package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentProductPurchaseBinding;
import com.tarcc.proin.proin.model.Product;
import com.tarcc.proin.proin.model.ProductPackage;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.MainActivity;
import com.tarcc.proin.proin.ui.login.RegisterActivity;
import com.tarcc.proin.proin.ui.profile.MyServicesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductPurchaseFragment extends Fragment{
    private static final String TAG = "com.tarcc.proin.proin.ui.product";
    private SharedPreferences data;
    private SharedPreferences.Editor editor;
    private ProductPackage productPackage;
    private User user;
    private List<String> allProdID;

    public static ProductPurchaseFragment newInstance(Product product) {

        Bundle args = new Bundle();
        //make the product to become string
        //store the user as a string in the sharedpreferences
        args.putString("product", product.toJson());

        ProductPurchaseFragment fragment = new ProductPurchaseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private FragmentProductPurchaseBinding binding;
    private Product product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = data.edit();
        allProdID = new ArrayList<>();

        //Inside this project object has the productId
        //get the string out and deserialize to become a object
        String projectJson = getArguments().getString("product");
        product = Product.deserialize(projectJson);
        productPackage = new ProductPackage();
        String userJson = data.getString("user_details", "");
        //if user details empty then will stop user continue in this page
        if(TextUtils.isEmpty(userJson)){
            getActivity().finish();
        }
        user = User.deserialize(userJson);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_purchase, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //final Spinner spinner = (Spinner)getActivity().findViewById(R.id.spnCoverage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.coverage, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spnCoverage.setAdapter(adapter);

        binding.spnCoverage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(position == 0)
                {
                    updatePackage("Monthly",
                            "280.00",
                            "30",
                            "2047-12-03");
                }
                else if(position == 1) {
                    updatePackage("Monthly",
                            "380.00",
                            "50",
                            "2057-12-03");
                }
                else if(position == 2)
                {
                    updatePackage("Monthly",
                            "480.00",
                            "70",
                            "2067-12-03");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                updatePackage("",
                        "",
                        "",
                        "");
            }
        });


        getProdID(getActivity(), (getString(R.string.get_my_services_url)+user.getNric()));

        binding.btnPay.setOnClickListener(onClickListener);
    }

    private void updatePackage(String paymentMode, String monthlyPay, String totalYear, String expireDate){
        binding.paymentMode.setText(paymentMode);
        binding.monthlyPay.setText(monthlyPay);
        binding.totalYear.setText(totalYear);
        binding.expireDate.setText(expireDate);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == binding.btnPay){

                String nric = user.getNric();
                String prodID = product.getProductId();
                String coverage = binding.spnCoverage.getSelectedItem().toString();

                String premium = binding.monthlyPay.getText().toString();
                String status = binding.paymentMode.getText().toString();
                String expireDate = binding.expireDate.getText().toString();
                String totPaymentYear = binding.totalYear.getText().toString();

                if(foundRepeatedProdID(prodID)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("You have already purchased the same product. Please choose another product.")
                            .setNegativeButton("OK",null).create().show();

                }
                else
                {
                    try{
                        productPackage.setNric(nric);
                        productPackage.setProductID(prodID);
                        productPackage.setCoverage(coverage);
                        productPackage.setPremium(premium);
                        productPackage.setStatus(status);
                        productPackage.setExpireDate(expireDate);
                        productPackage.setTotPaymentYear(totPaymentYear);
                        Log.d("kahyee", productPackage.toJson());

                        makeServiceCall(getActivity(), getString(R.string.insert_package_url));
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    };

    public void makeServiceCall(Context context, String url) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==1) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                                    MainActivity.start(getActivity());
                                    //Intent newIntent = new Intent(getActivity(), MyServicesActivity.class);
                                    //startActivity(newIntent);

                                }else{
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nric", productPackage.getNric());
                    params.put("productID", productPackage.getProductID());
                    params.put("coverage", productPackage.getCoverage());
                    params.put("premium", productPackage.getPremium());
                    params.put("status", productPackage.getStatus());
                    params.put("expireDate", productPackage.getExpireDate());
                    params.put("totPaymentYear", productPackage.getTotPaymentYear());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProdID(Context context, String url){
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            JSONObject jsonObject;
                            try {
                                allProdID.clear();
                                for(int i = 0; i<response.length(); i++){
                                    jsonObject = (JSONObject) response.get(i);
                                    String productID = jsonObject.getString("productID");
                                    allProdID.add(productID);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getActivity(), "Error : " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) ;
            jsonArrayRequest.setTag(TAG);
            queue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean foundRepeatedProdID(String prodID){
        boolean found=false;
        for (int i = 0; i < allProdID.size(); ++i) {
            if (allProdID.get(i).equals(prodID)) {
                found=true;
            }
        }
        return found;
    }


}




