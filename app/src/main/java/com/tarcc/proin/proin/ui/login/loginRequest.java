package com.tarcc.proin.proin.ui.login;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class loginRequest extends StringRequest {
    private Map<String,String> params;
    private static final String LOGIN_REQUEST_URL = "http://yeelow76.000webhostapp.com/login.php";

    public loginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL , listener,null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}
