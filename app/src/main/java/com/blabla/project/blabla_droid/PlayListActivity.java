package com.blabla.project.blabla_droid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        // Request api playlist
        requestPlayList();

    }

    protected void requestPlayList() {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> params = new HashMap<>();

        SharedPreferences mSharedPref = this.getPreferences(Context.MODE_PRIVATE);
        headers.put("Authorization", "Bearer " + mSharedPref.getString(getString(R.string.blabla_user_token), ""));

        RequestClass.getInstance(this).postJson(
            "/playlist",
            headers,
            params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    drawPlayList(response);
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

    protected void drawPlayList(JSONObject playlists) {
        /*
        String[] playlist = {"List 1", "List 2", "List 3", "List 4"};
        ListView listView = (ListView) findViewById(R.id.listview_playlist);
        listView.setAdapter(adapter);
        */
        try {
            Iterator<?> keys = playlists.keys();
            String[] playlist = new String[playlists.length()];
            JSONObject item;
            int i = 0;
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (playlists.get(key) instanceof JSONObject) {
                    item = (JSONObject) playlists.get(key);
                    playlist[i] = item.getString("title");
                    i++;
                }
            }

            // Add item to listview
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlist);
            ListView listView = (ListView) findViewById(R.id.listview_playlist);
            listView.setAdapter(adapter);

            // TODO : http://tutos-android-france.com/listview-afficher-une-liste-delements/

        }catch(JSONException e) {

        }

    }
}
