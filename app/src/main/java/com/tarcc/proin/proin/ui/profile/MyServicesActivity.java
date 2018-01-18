package com.tarcc.proin.proin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.tarcc.proin.proin.databinding.ActivityMyServicesBinding;
import com.tarcc.proin.proin.model.ProductPackage;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.BaseActivity;
import com.tarcc.proin.proin.ui.MainActivity;
import com.tarcc.proin.proin.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyServicesActivity extends BaseActivity {
    private SharedPreferences data;
    private SharedPreferences.Editor editor;
    private List<ProductPackage> productPackageArry;
   private ProductPackage productPackage;

    private User user;
    private String myic;
    public static void start(Context context) {
        Intent starter = new Intent(context, MyServicesActivity.class);
        context.startActivity(starter);
    }

    private ActivityMyServicesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_services);
        initToolbar();

        data = PreferenceManager.getDefaultSharedPreferences(this);
        editor = data.edit();
        productPackageArry = new ArrayList<>();

        String userJson = data.getString("user_details", "");
        //if user details empty then will stop user continue in this page
        if(TextUtils.isEmpty(userJson)){
            this.finish();
        }
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        user = User.deserialize(userJson);

        try{
        myic = user.getNric();
        makeServiceCall(this, (getString(R.string.get_my_services_url)+myic));
    }catch (Exception e){
        e.printStackTrace();
        Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
        binding.productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("coverage", productPackageArry.get(i).getCoverage());
                editor.commit();
                editor.putString("premium", productPackageArry.get(i).getPremium());
                editor.commit();
                editor.putString("status", productPackageArry.get(i).getStatus());
                editor.commit();
                editor.putString("expireDate", productPackageArry.get(i).getExpireDate());
                editor.commit();
                editor.putString("totPaymentYear", productPackageArry.get(i).getTotPaymentYear());
                editor.commit();

                ShowProductActivity.start(MyServicesActivity.this);
            }
        });


    }

    private void initToolbar() {
        setSupportActionBar(binding.actionToolbar);
        binding.actionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    public void makeServiceCall(Context context, String url){
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            JSONObject jsonObject;
                            try {


                                for(int i = 0; i<response.length(); i++){
                                    jsonObject = (JSONObject) response.get(i);

                                        String nric = jsonObject.getString("nric");
                                        String productID = jsonObject.getString("productID");
                                        String coverage = jsonObject.getString("coverage");
                                        String premium = jsonObject.getString("premium");
                                        String status = jsonObject.getString("status");
                                        String expireDate = jsonObject.getString("expireDate");
                                        String totPaymentYear = jsonObject.getString("totPaymentYear");

                                        productPackage = new ProductPackage(nric, productID,coverage,premium,status,expireDate,totPaymentYear);
                                        productPackageArry.add(productPackage);

                                        //Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        //LoginActivity.this.startActivity(MainIntent);

                            }
                                loadMyServices();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) ;
            queue.add(jsonArrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    private void loadMyServices(){
        final MyServiceAdaptor myServiceAdaptor = new MyServiceAdaptor(MyServicesActivity.this, R.layout.activity_my_services, productPackageArry);
        binding.productList.setAdapter(myServiceAdaptor);
    }



}
