package com.blabla.project.blabla_droid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when to open playlist */
    public void openPlayList(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PlayListActivity.class);
        startActivity(intent);
    }
}
