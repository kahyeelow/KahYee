package com.tarcc.proin.proin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tarcc.proin.proin.R;
import com.tarcc.proin.proin.databinding.ActivityChangePasswordBinding;
import com.tarcc.proin.proin.model.User;
import com.tarcc.proin.proin.ui.BaseActivity;
import com.tarcc.proin.proin.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChangePasswordActivity extends BaseActivity {
    private ActivityChangePasswordBinding binding;
    private SharedPreferences data;
    private SharedPreferences.Editor editor;
    private User user;
    private String currPass;
    private String newPass;
    private String confirmPass;
    RequestQueue queue;

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        initToolbar();

        data = PreferenceManager.getDefaultSharedPreferences(ChangePasswordActivity.this);
        editor = data.edit();
        String userJson = data.getString("user_details", "");


        //this user object will have all the user info
        user = User.deserialize(userJson);

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
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


    public void changePass(){
        currPass = binding.currentPassword.getText().toString();
        newPass = binding.newPassword.getText().toString();
        confirmPass = binding.confirmPassword.getText().toString();

        if(!validation()){
            Toast.makeText(ChangePasswordActivity.this,"Please enter required fields", Toast.LENGTH_LONG).show();
        }
        else{
            if(!(currPass.equals(user.getPassword()))){
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setMessage("Current Password Incorrect.").setNegativeButton("Retry", null).create().show();
            }
            else if(!(newPass.equals(confirmPass))){
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setMessage("New Password and Confirm Password Mismatch.").setNegativeButton("Retry", null).create().show();
            }
            else{
                try {
                    makeServiceCall(ChangePasswordActivity.this, getString(R.string.update_password_url));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

        }
    }
    public boolean validation(){
        currPass = binding.currentPassword.getText().toString();
        newPass = binding.newPassword.getText().toString();
        confirmPass = binding.confirmPassword.getText().toString();

        boolean valid = true;

        if(currPass.isEmpty()){
            binding.currentPassword.setError("Please enter current password");
            valid = false;
        }
        if(newPass.isEmpty()){
            binding.newPassword.setError("Please enter new password");
            valid = false;
        }
        if(confirmPass.isEmpty()){
            binding.confirmPassword.setError("Please enter confirm password");
            valid = false;
        }

        return valid;
    }



    public void makeServiceCall(final Context context, String url) {
        queue = Volley.newRequestQueue(context);
        //Send data
        try {
            currPass = binding.currentPassword.getText().toString();
            newPass = binding.newPassword.getText().toString();
            confirmPass = binding.confirmPassword.getText().toString();

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
                                String message=jsonObject.getString("message");

                                if (success==1) {//UPDATED success
                                    Toast.makeText(ChangePasswordActivity.this,message,Toast.LENGTH_LONG).show();

                                    editor.putString("password", confirmPass);
                                    editor.commit();

                                    user.setPassword(confirmPass);
                                    editor.putString("user_details", user.toJson());
                                    editor.commit();

                                    LoginActivity.start(ChangePasswordActivity.this);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                                    builder.setTitle("Failed to update");
                                    builder.setMessage(message).setNegativeButton("Retry", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ChangePasswordActivity.this, "Error : " + error.toString(), Toast.LENGTH_LONG).show();


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("password", confirmPass);
                    params.put("nric", user.getNric());

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
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }
}
