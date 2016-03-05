package com.example.eric.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CongressionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        getSupportActionBar().hide();
    }

    public void goToDetailed1(View view) {
        Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
        intent.putExtra("REP NUM", 1);
        startActivity(intent);
    }

    public void goToDetailed2(View view) {
        Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
        intent.putExtra("REP NUM", 2);
        startActivity(intent);
    }

    public void goToDetailed3(View view) {
        Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
        intent.putExtra("REP NUM", 3);
        startActivity(intent);
    }

    public void goToDetailed4(View view) {
        Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
        intent.putExtra("REP NUM", 4);
        startActivity(intent);
    }
}
