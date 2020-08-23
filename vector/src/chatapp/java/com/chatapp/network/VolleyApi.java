package com.chatapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyApi {

    static RequestQueue mRequestQueue;
    private Context context;

    public VolleyApi(Context context) {
        this.context = context;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;

    }

    public void post(VolleyListener listener, String url, JSONObject object,String tag) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, object, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response, tag);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error, tag);
                    }
                });

        getRequestQueue().add(jsonObjectRequest);
    }
}
