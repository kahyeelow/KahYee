package com.tarcc.proin.proin.ui.login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityLoginBinding;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.MainActivity;
import com.tarcc.proin.proin.ui.product.ProductPurchaseFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    RequestQueue queue;
    SharedPreferences data;
    SharedPreferences.Editor editor;


    public static void restart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.resgiter.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);


        data = PreferenceManager.getDefaultSharedPreferences(this);
        editor = data.edit();


        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });

    }


    public void loginUser(){
        try{
            makeServiceCall(LoginActivity.this, getString(R.string.login_url), binding.username.getText().toString(), binding.password.getText().toString());

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url,final String username, final String password){
        RequestQueue queue = Volley.newRequestQueue(context);


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
                               Boolean success = jsonObject.getBoolean("success");

                                if (success) {

                                    String name = jsonObject.getString("name");
                                    String ic = jsonObject.getString("nric");
                                    String dob = jsonObject.getString("dob");
                                    String address = jsonObject.getString("address");
                                    String gender = jsonObject.getString("gender");
                                    String phoneNum = jsonObject.getString("phoneNo");
                                    String email = jsonObject.getString("email");
                                    String occupation = jsonObject.getString("occupation");
                                    String salary = jsonObject.getString("salary");
                                    String username = jsonObject.getString("username");
                                    String password = jsonObject.getString("password");

                                    editor.putString("name", name);
                                    editor.commit();
                                    editor.putString("nric", ic); //i pass it here, get it there
                                    editor.commit();
                                    editor.putString("dob", dob);
                                    editor.commit();
                                    editor.putString("address", address);
                                    editor.commit();
                                    editor.putString("gender", gender);
                                    editor.commit();
                                    editor.putString("phoneNum", phoneNum);
                                    editor.commit();
                                    editor.putString("email", email);
                                    editor.commit();
                                    editor.putString("occupation", occupation);
                                    editor.commit();
                                    editor.putString("salary", salary);
                                    editor.commit();
                                    editor.putString("username", username);
                                    editor.commit();
                                    editor.putString("password", password);
                                    editor.commit();

                                    User user = User.deserialize(response);
                                    editor.putString("user_details", user.toJson());
                                    editor.commit();

                                    MainActivity.start(LoginActivity.this);
                                    //Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    //LoginActivity.this.startActivity(MainIntent);

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (!isConnected()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Connection Error");
                                builder.setMessage("No network.\nPlease try connect your network").setNegativeButton("Retry", null).create().show();

                            } else
                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);

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

    @Override
    public void onClick(View view) {
        if (view == binding.resgiter) {
            RegisterActivity.start(this);
        } else if (view == binding.forgotPassword) {
            ForgotPasswordActivity.start(this);
        }
    }
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }




}
