package com.example.eric.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        String stateCounty = intent.getStringExtra("stateCounty");
        int obama = intent.getIntExtra("obama", -1);
        int romney = intent.getIntExtra("romney", -1);

        ((TextView) findViewById(R.id.stateCounty)).setText(stateCounty);
        ((TextView) findViewById(R.id.obama)).setText("Obama - " + obama + "%");
        ((TextView) findViewById(R.id.romney)).setText("Romney - " + romney + "%");
    }
}
