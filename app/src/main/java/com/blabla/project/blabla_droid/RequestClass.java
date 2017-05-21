package com.blabla.project.blabla_droid;

/**
 * Created by popolos on 21/05/2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
    private SharedPreferences mSharedPref;

    private static String ApiUrl = "http://10.0.2.2/api-project/web/app_dev.php/api";

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

    private String getToken(FragmentActivity context) {
        //if(mSharedPref == null) {
            mSharedPref = context.getPreferences(Context.MODE_PRIVATE);
        //}
        //String s = getString(R.string.blabla_user_token);
        return "Bearer " + mSharedPref.getString("token", "");
    }

    public void postString(final FragmentActivity context, String url, final Map<String,String> headers, final Map<String,String> params, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        url = ApiUrl + url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, successListener, errorListener){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Authorization", getToken(context));
                return headers;
            }
        };
        addToRequestQueue(stringRequest);
    }

    public void postJson(final FragmentActivity context, String url, final Map<String,String> headers, final Map<String,String> params, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        url = ApiUrl + url;
        JSONObject jsonParameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParameters, successListener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //headers.put("Authorization", getToken(context));
                return headers;
            }
        };
        addToRequestQueue(stringRequest);
    }

}
