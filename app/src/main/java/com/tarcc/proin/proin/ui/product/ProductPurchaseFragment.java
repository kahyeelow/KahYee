package com.tarcc.proin.proin.ui.product;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.tarcc.proin.proin.ui.login.LoginActivity;
import com.tarcc.proin.proin.ui.profile.MyServicesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ProductPurchaseFragment extends Fragment{
    private String userIC;
    RequestQueue queue;
    List<String> dob;
    public static final String TAG = "com.tarcc.proin.proin.ui.product";
    private TextView tvYear,tvPay,tvExpDate;
    private Button buttonPur;

    public static ProductPurchaseFragment newInstance() {

        Bundle args = new Bundle();

        ProductPurchaseFragment fragment = new ProductPurchaseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private FragmentProductPurchaseBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        //tvAge = (TextView)getActivity().findViewById(R.id.age);


        //Bundle bundle = this.getArguments();
        //String myic = bundle.getString(LoginActivity.NRIC);

       // tvAge.setText(myic.toString());

        tvYear = (TextView)getActivity().findViewById(R.id.total_year);
        tvExpDate = (TextView)getActivity().findViewById(R.id.expire_date);
        tvPay = (TextView)getActivity().findViewById(R.id.monthly_pay);
        buttonPur = (Button)getActivity().findViewById(R.id.btn_pay);

        final Spinner spinner = (Spinner)getActivity().findViewById(R.id.spnCoverage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.coverage, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(spinner.getSelectedItemPosition() == 0)
                {
                    tvPay.setText("280.00");
                    tvYear.setText("30");
                    tvExpDate.setText("2047-12-03");
                }
                else if(spinner.getSelectedItemPosition() == 1) {
                    tvPay.setText("380.00");
                    tvYear.setText("50");
                    tvExpDate.setText("2057-12-03");
                }
                else if(spinner.getSelectedItemPosition() == 2)
                {
                    tvPay.setText("480.00");
                    tvYear.setText("70");
                    tvExpDate.setText("2067-12-03");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tvPay.setText("");
                tvYear.setText("");
                tvExpDate.setText("");

            }
        });

        buttonPur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getContext(), MyServicesActivity.class);
                startActivity(newIntent);
            }
        });





    }


/**
    public void getAge(Context context, String url){
        queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //everytime i listen to the server, i clear the list
                            JSONObject userResponse = new JSONObject();
                                String dob = userResponse.getString("dob");
                           // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //Date date = dateFormat.parse(dob);

                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

        }
**/


}




