package com.example.gerdjikov.mathgame;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gerdjikov on 17.2.2018 г..
 */

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://doperich.000webhostapp.com/Login.php";
    Map<String,String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
