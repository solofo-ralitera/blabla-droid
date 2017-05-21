package com.blabla.project.blabla_droid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.blabla_user_token), null);
        // If no token, load login layout
        if(token == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
        // Else load main_menu layout
        else {
            // Store token to request class
            RequestClass.getInstance(this).setToken(token);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_menu);
            // List of menu
            String[] menuList = {
                getString(R.string.Playlist),
                getString(R.string.Logout),
            };
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuList);
            ListView listView = (ListView) findViewById(R.id.listview_mainmenu);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(menuListClickedHandler);
        }
    }

    @Override
    public void onRestart() {  // After a pause OR at startup
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    /**
     * Login click action
     * @param view
     */
    public void login(View view) {
        Map<String,String> headers = new HashMap<>();

        Map<String,String> params = new HashMap<>();
        params.put("_username", ((EditText) findViewById(R.id.editText_login)).getText().toString());
        params.put("_password", ((EditText) findViewById(R.id.editText_password)).getText().toString());

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        RequestClass.getInstance(this).postJson(
            "/login_check",
            headers,
            params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Login success, store token
                    try {
                        // Read & store token
                        String token = response.getString("token");
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.blabla_user_token), token);
                        editor.apply();
                        // Reload login page
                        finish();
                        startActivity(getIntent());
                    }catch (JSONException e) {
                        // TODO login error
                    }
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

    /**
     * Called when logout
     * @param view
     */
    public void logout(View view) {
        // Remove token
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.blabla_user_token));
        editor.apply();

        // Open login activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Called when to open playlist
     * @param view
     */
    public void openPlayList(View view) {
        // Open playlist activity
        Intent intent = new Intent(this, PlayListActivity.class);
        startActivity(intent);
    }

    /**
     * Main menu item click
     */
    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener menuListClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View listView, int position, long id) {
            Object o = parent.getItemAtPosition(position);
            if(o.toString().equals(getString(R.string.Playlist))) {
                openPlayList(listView);
            }
            else if(o.toString().equals(getString(R.string.Logout))) {
                logout(listView);
            }
        }
    };
}
