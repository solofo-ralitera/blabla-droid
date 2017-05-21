package com.blabla.project.blabla_droid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Map<String,String> headers = new HashMap<>();

        Map<String,String> params = new HashMap<>();
        params.put("_username", ((EditText) findViewById(R.id.editText_login)).getText().toString());
        params.put("_password", ((EditText) findViewById(R.id.editText_password)).getText().toString());

        RequestClass.getInstance(this).postJson(
                "http://10.0.2.2/api-project/web/app_dev.php/api/login_check",
                headers,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Login Ok, store token



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO login error
                    }
                }
        );
    }

        /** Called when to open playlist */
    public void openPlayList(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PlayListActivity.class);
        startActivity(intent);
    }
}
