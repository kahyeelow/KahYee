package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.FragmentProductPurchaseBinding;
import com.tarcc.proin.proin.model.Product;
import com.tarcc.proin.proin.model.ProductPackage;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.MainActivity;
import com.tarcc.proin.proin.ui.profile.MyServicesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ProductPurchaseFragment extends Fragment{
    //Why this nric, prodId will be public? got other class access?
    //nope
    //private String nric, prodID, coverage, premium, status, expireDate,totPaymentYear;

    private static final String TAG = "com.tarcc.proin.proin.ui.product";
    //in page has databinding, so no need findViewById anymore
    //private TextView tvYear,tvPay,tvExpDate, tvStatus;
    //private Button buttonPur;
    private SharedPreferences data;
    private SharedPreferences.Editor editor;
    private ProductPackage productPackage;
    private User user;
    //the sharedpreferences data & editor, other page also use this, need to private?
    //other page is using sharepreferences but not using the sharedpreferences in this page

    public static ProductPurchaseFragment newInstance(Product product) {

        Bundle args = new Bundle();
        //Here i make the product to become string
        //which you will store the user as a string in the sharedpreferences
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

        //Inside this project object has the productId
        //then u will get the string out and deserialize to become a object
        //Something like this, if you have time only try to change it, otherwise continue like this first
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

        //This part is not needed actually
        //tvYear = (TextView)getActivity().findViewById(R.id.total_year);
        //tvExpDate = (TextView)getActivity().findViewById(R.id.expire_date);
        //tvPay = (TextView)getActivity().findViewById(R.id.monthly_pay);
        //tvStatus = (TextView)getActivity().findViewById(R.id.payment_mode);
        //buttonPur = (Button)getActivity().findViewById(R.id.btn_pay);


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

                //why dont use position but use selectedItemPosition?
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

        //String inCoverage = spinner.getSelectedItem().toString();
        //String monthlyPay = binding.monthlyPay.getText().toString();
        //String paymentYear = binding.totalYear.getText().toString();
        //String expiredDate = binding.expireDate.getText().toString();
        //String payStatus = binding.paymentMode.getText().toString();

        //editor.putString("coverage", inCoverage);
        //editor.commit();
        //editor.putString("premium", monthlyPay);
        //editor.commit();
        //editor.putString("totPayYear", paymentYear);
        //editor.commit();
        //editor.putString("expiredDate", expiredDate);
        //editor.commit();
        //editor.putString("status", payStatus);
        //editor.commit();

        binding.btnPay.setOnClickListener(onClickListener);
    }

    private void updatePackage(String paymentMode, String monthlyPay, String totalYear, String expireDate){
        binding.paymentMode.setText(paymentMode);
        //this one should be the editText's id?
        //ya binding.{view_id}
        //shouldn't it be payment_mode?
        //android will help you to remove "_" can capital the next character
        //so here should not be the problem?
        //ya should not be problem, if you want to check what thing pass into server, we can check like this
        binding.monthlyPay.setText(monthlyPay);
        binding.totalYear.setText(totalYear);
        binding.expireDate.setText(expireDate);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == binding.btnPay){

                //Where you get this nric? i left the original data here
                //String nric = data.getString("nric", "");
                //String prodID = data.getString("prodID", "");
                //String coverage = binding.spnCoverage.getSelectedItem().toString();
                //String premium = data.getString("premium","");
                //String status = data.getString("status", "");
                //String expireDate = data.getString("expiredDate", "");
                //String totPaymentYear = data.getString("totPayYear", "");

                //this is the example normally we will try to min the total data save in sharedpreferences
                //instance to save all the data one by one
                //we will save all the data into a user object and save it one time
                //i think should be okay ald, u try try and debug, i may have do wrong also
                //haha any question wan ask me?
                //so far okay, i understand
                String nric = user.getNric();
                String prodID = product.getProductId();
                String coverage = binding.spnCoverage.getSelectedItem().toString();
                //Normally we will declare the name either is premium or paymentMode, will not be paymentMode
                //at first then become permium after that
                String premium = binding.monthlyPay.getText().toString();
                //what is status?cool if you see me use wrong please correct me thanks
                String status = binding.paymentMode.getText().toString();
                String expireDate = binding.expireDate.getText().toString();
                String totPaymentYear = binding.totalYear.getText().toString();
                //Should be okay already, please debug and have a look
                try{
                    productPackage.setNric(nric);
                    productPackage.setProductID(prodID);
                    productPackage.setCoverage(coverage);
                    productPackage.setPremium(premium);
                    productPackage.setStatus(status);
                    productPackage.setExpireDate(expireDate);
                    productPackage.setTotPaymentYear(totPaymentYear);
                    Log.d("kahyee", productPackage.toJson());
                    //check ur server side did we pass any less info?
                    //my bad
                    //i miss the debug, should press debug?
                    //we can turn to debug by pressing this


                    makeServiceCall(getActivity(), getString(R.string.insert_package_url));
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    //What is this make servcie call use?
    //this is communication with the server db wor, is insert into sqlite or server db ya?
    //server db, through the insert_package_url
    //so it success right?
    //ya, i can successfully connect ady
    //okay cool~
    //give me some time i go through ur code
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
                                    //getActivity is same as getApplicationContext()
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

}




