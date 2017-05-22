package com.blabla.project.blabla_droid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blabla.project.blabla_droid.PLaylist.ListViewItem;
import com.blabla.project.blabla_droid.PLaylist.ListViewItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        try {
            List<ListViewItem> items = new ArrayList<>();
            JSONObject item;
            Iterator<?> keys = playlists.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (playlists.get(key) instanceof JSONObject) {
                    item = (JSONObject) playlists.get(key);
                    items.add(new ListViewItem(Color.BLUE, item.getString("title"), item.getString("title")));
                }
            }

            // Add item to listview
            ListView listView = (ListView) findViewById(R.id.listview_playlist);
            ListViewItemAdapter adapter = new ListViewItemAdapter(this, items);
            listView.setAdapter(adapter);

            // TODO : http://tutos-android-france.com/listview-afficher-une-liste-delements/

        }catch(JSONException e) {

        }

    }
}
