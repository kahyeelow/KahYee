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
import android.util.Patterns;
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
    private String gender;
    RequestQueue queue;
    List<String> allUsername;
    public static final String TAG = "com.tarcc.proin.proin.ui.login"; //ask tutor

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initToolbar();

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


    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    private ActivityRegisterBinding binding;

    public String selectGender()
    {
        int checked = binding.radgrpGender.getCheckedRadioButtonId();

        if(checked == R.id.radMale)
        {
            gender = "Male";
        }
        else if(checked == R.id.radFemale)
        {
            gender = "Female";
        }

        return gender;
    }


    public void saveRecord(){
        User user = new User();

        String username = binding.username.getText().toString();

        if(!validation()){
            //TODO error message
            Toast.makeText(this,"Please enter required fields", Toast.LENGTH_LONG).show();
        }
        else{
            if(foundUsername(username))
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Username already exist. Please try another.").setNegativeButton("Retry",null).create().show();

            }
            else{
                user.setName(binding.fullName.getText().toString());
                user.setNric(binding.nric.getText().toString());
                user.setDob(binding.dob.getText().toString());
                user.setAddress(binding.address.getText().toString());
                user.setGender(selectGender().toString());
                user.setPhoneNo(binding.phoneNo.getText().toString());
                user.setEmail(binding.email.getText().toString());
                user.setOccupation(binding.occupation.getText().toString());
                user.setSalary(Double.parseDouble(binding.salary.getText().toString()));
                user.setUsername(binding.username.getText().toString());
                user.setPassword(binding.password.getText().toString());

                try{
                    makeServiceCall(this, getString(R.string.insert_user_url), user);


                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
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
                                if (success==1) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    LoginActivity.start(RegisterActivity.this);
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

    public boolean validation(){
        boolean valid = true;
        if(binding.fullName.getText().toString().isEmpty()){
            binding.fullName.setError("Please enter full name");
            valid = false;
        }
        if(binding.nric.getText().toString().isEmpty() || binding.nric.getText().toString().length() != 12){
            binding.nric.setError("Please enter valid IC number");
            valid = false;
        }
        if(binding.dob.getText().toString().isEmpty() || binding.dob.getText().toString().length() !=10){
            binding.dob.setError("Please enter valid dob");
            valid = false;
        }
        if(binding.phoneNo.getText().toString().isEmpty()){
            binding.phoneNo.setError("Please enter phone number");
            valid = false;
        }
        if(binding.email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()){
            binding.email.setError("Please enter valid email");
            valid = false;
        }
        if(binding.address.getText().toString().isEmpty()){
            binding.address.setError("Please enter address");
            valid = false;
        }
        if(binding.occupation.getText().toString().isEmpty()){
            binding.occupation.setError("Please enter occupation");
            valid = false;
        }
        if(binding.salary.getText().toString().isEmpty()){
            binding.salary.setError("Please enter salary");
            valid = false;
        }
        if(binding.username.getText().toString().isEmpty()){
            binding.username.setError("Please enter username");
            valid = false;
        }
        if(binding.password.getText().toString().isEmpty()){
            binding.password.setError("Please enter password");
            valid = false;
        }

        return valid;
    }

}
