package com.example.reclaimportugal;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {

    private static final String SERVER_ADDRESS = "https://ratoongames.com:5407/";
    private static final String LOGIN_PATH = "login";

    public static void loginRequest(String user, String password, Login instance){
        String url = SERVER_ADDRESS + LOGIN_PATH;
        JSONObject request = new JSONObject();

        try{
            request.put("User", user);
            request.put("Password", password);
        }
        catch (JSONException e){
            e.printStackTrace();
            instance.loginErrorResult();
        }

        NukeSSLCerts.nuke();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        instance.loginResult(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        instance.loginErrorResult();
                    }
                });
        RequestManager.getInstance(instance).addToRequestQueue(jsonObjectRequest);
    }
}
