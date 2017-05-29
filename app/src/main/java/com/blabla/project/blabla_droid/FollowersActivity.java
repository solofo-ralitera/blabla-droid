package com.blabla.project.blabla_droid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blabla.project.blabla_droid.Follower.ListViewItem;
import com.blabla.project.blabla_droid.Follower.ListViewItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        // Request api playlist
        requestFollowers();

    }

    protected void requestFollowers() {
        Map<String,String> headers = new HashMap<>();
        Map<String,String> params = new HashMap<>();

        headers.put("Authorization", "Bearer " + RequestClass.getInstance(this).getToken());

        RequestClass.getInstance(this).getJson(
            "/users/2/followers",
            headers,
            params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    drawFollowers(response);
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

    protected void drawFollowers(JSONObject data) {
        try {
            List<ListViewItem> items = new ArrayList<>();
            JSONObject item;
            JSONArray followers = data.getJSONArray("data");
            for (int i = 0 ; i < followers.length(); i++) {
                item = followers.getJSONObject(i);
                items.add(new ListViewItem(item.getString("avatar"), item.getString("username"), item.getString("name")));
            }
            // Add item to listview
            ListView listView = (ListView) findViewById(R.id.listview_followers);
            ListViewItemAdapter adapter = new ListViewItemAdapter(this, items);
            listView.setAdapter(adapter);
        }catch(JSONException e) {
            // TODO : back to previous activity
        }

    }
}
