package com.blabla.project.blabla_droid;

/**
 * Created by popolos on 21/05/2017.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class RequestClass extends Application {

    private static RequestClass mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private static String ApiUrl = "http://10.0.2.2/api-project/web/app_dev.php/api";
    private String mToken = "";

    private RequestClass(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
            new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap>
                        cache = new LruCache<String, Bitmap>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });
    }

    public static synchronized RequestClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestClass(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    public void setToken(String token) {
        mToken = token;
    }
    private String getToken() {
        return "Bearer " + mToken;
    }

    public void postString(String url, final Map<String,String> headers, final Map<String,String> params, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        url = ApiUrl + url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, successListener, errorListener){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Authorization", getToken());
                return headers;
            }
        };
        addToRequestQueue(stringRequest);
    }

    public void postJson(String url, final Map<String,String> headers, final Map<String,String> params, Response.Listener<JSONObject> successListener, final Response.ErrorListener errorListener) {
        url = ApiUrl + url;
        JSONObject jsonParameters = new JSONObject(params);

        Response.ErrorListener fErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Authentication error
                if(error.toString().endsWith("AuthFailureError")) {
                    // Remove key
                    SharedPreferences sharedPref = ((Activity) mCtx).getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(mCtx.getString(R.string.blabla_user_token));
                    editor.apply();
                    RequestClass.this.setToken("");

                    // Reload Main page
                    Intent intent = new Intent(mCtx, MainActivity.class);
                    mCtx.startActivity(intent);
                }
                // Else call user func
                else {
                    errorListener.onErrorResponse(error);
                }
            }
        };

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParameters, successListener, fErrorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Authorization", getToken());
                return headers;
            }
        };
        addToRequestQueue(stringRequest);
    }

}
