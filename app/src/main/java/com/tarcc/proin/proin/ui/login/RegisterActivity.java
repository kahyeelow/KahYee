package com.tarcc.proin.proin.ui.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.tarcc.proin.proin.databinding.ActivityRegisterBinding;
import com.tarcc.proin.proin.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText editTextName, editTextNric, editTextDob, editTextAddress, editTextGender, editTextPhoneNo, editTextEmail,
    editTextOccupation, editTextSalary, editTextUsername, editTextPassword;
    private TextView register;
    RequestQueue queue;
    List<String> allUsername;
    public static final String TAG = "com.tarcc.proin.proin.ui.login"; //ask tutor


    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initToolbar();

        editTextName   = (TextInputEditText)findViewById(R.id.fullName);
        editTextNric = (TextInputEditText)findViewById(R.id.nric);
        editTextDob = (TextInputEditText)findViewById(R.id.dob);
        editTextAddress = (TextInputEditText)findViewById(R.id.address);
        editTextGender = (TextInputEditText)findViewById(R.id.gender);
        editTextPhoneNo = (TextInputEditText)findViewById(R.id.phoneNo);
        editTextEmail = (TextInputEditText)findViewById(R.id.email);
        editTextOccupation = (TextInputEditText)findViewById(R.id.occupation);
        editTextSalary = (TextInputEditText)findViewById(R.id.salary);
        editTextUsername= (TextInputEditText)findViewById(R.id.username);
        editTextPassword = (TextInputEditText)findViewById(R.id.password);
        register = (TextView)findViewById(R.id.register);
        allUsername = new ArrayList<>();

        getUser(getApplicationContext(), getString(R.string.get_user_url));

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
            }
        });

    }

    public void saveRecord(){
        User user = new User();

        String username = editTextUsername.getText().toString();

        if(foundUsername(username))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Username already exist. Please try another.").setNegativeButton("Retry",null).create().show();

        }
        else{
            user.setName(editTextName.getText().toString());
            user.setNric(editTextNric.getText().toString());
            user.setDob(editTextDob.getText().toString());
            user.setAddress(editTextAddress.getText().toString());
            user.setGender(editTextGender.getText().toString());
            user.setPhoneNo(editTextPhoneNo.getText().toString());
            user.setEmail(editTextEmail.getText().toString());
            user.setOccupation(editTextOccupation.getText().toString());
            user.setSalary(Double.parseDouble(editTextSalary.getText().toString()));
            user.setUsername(editTextUsername.getText().toString());
            user.setPassword(editTextPassword.getText().toString());

            try{
                makeServiceCall(this, getString(R.string.insert_user_url), user);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void makeServiceCall(Context context, String url, final User user) {
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
                                if (success==0) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", user.getName());
                    params.put("nric", user.getNric());
                    params.put("dob", user.getDob());
                    params.put("address", user.getAddress());
                    params.put("gender", user.getGender());
                    params.put("phoneNo", user.getPhoneNo());
                    params.put("email", user.getEmail());
                    params.put("occupation", user.getOccupation());
                    params.put("salary", Double.toString(user.getSalary()));
                    params.put("username", user.getUsername());
                    params.put("password", user.getPassword());
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
    private void getUser(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //everytime i listen to the server, i clear the list
                            allUsername.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userResponse = (JSONObject) response.get(i);
                                //json object that contains all of the username in the user table
                                String username = userResponse.getString("username");
                                allUsername.add(username);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
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

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    public boolean foundUsername(String username) {
        //check whether the username exist or not
        boolean found=false;
        for (int i = 0; i < allUsername.size(); ++i) {
            if (allUsername.get(i).equals(username)) {
                found=true;
            }
        }
        return found;
    }

}
