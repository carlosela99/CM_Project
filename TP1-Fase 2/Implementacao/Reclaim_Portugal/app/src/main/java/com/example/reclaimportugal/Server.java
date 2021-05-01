package com.example.reclaimportugal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Server {

    private static final String SERVER_ADDRESS = "https://ratoongames.com:5407/";
    private static final String LOGIN_PATH = "login";
    private static final String REGISTER_PATH = "register";
    private static final String CONFIRM_REGISTER_PATH = "confirm-register";

    public static void loginRequest(String user, String password, Login instance){
        String url = SERVER_ADDRESS + LOGIN_PATH;
        JSONObject request = new JSONObject();

        try{
            request.put("User", user);
            request.put("Password", hashMD5(password));
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

    public static void registerRequest(String username, String email, String password, Register instance){
        String url = SERVER_ADDRESS + REGISTER_PATH;
        JSONObject request = new JSONObject();

        try{
            request.put("Username", username);
            request.put("Email", email);
            request.put("Password", hashMD5(password));
        }
        catch (JSONException e){
            e.printStackTrace();
            instance.registerErrorResult();
        }

        NukeSSLCerts.nuke();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        instance.registerResult(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        instance.registerErrorResult();
                    }
                });
        RequestManager.getInstance(instance).addToRequestQueue(jsonObjectRequest);
    }

    public static void confirmRegisterRequest(String email, String code, RegisterConfirmCode instance){
        String url = SERVER_ADDRESS + CONFIRM_REGISTER_PATH;
        JSONObject request = new JSONObject();

        try{
            request.put("Email", email);
            request.put("Code", code);
        }
        catch (JSONException e){
            e.printStackTrace();
            instance.registerConfirmationErrorResult();
        }

        NukeSSLCerts.nuke();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        instance.registerConfirmationResult(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        instance.registerConfirmationErrorResult();
                    }
                });
        RequestManager.getInstance(instance).addToRequestQueue(jsonObjectRequest);
    }

    private static String hashMD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
