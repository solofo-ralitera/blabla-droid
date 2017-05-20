package com.blabla.project.blabla_droid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        String[] playlist = {"List 1", "List 2", "List 3", "List 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlist);
        ListView listView = (ListView) findViewById(R.id.listview_playlist);
        listView.setAdapter(adapter);

    }
}
