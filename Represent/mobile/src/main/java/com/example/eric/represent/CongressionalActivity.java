package com.example.eric.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CongressionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        getSupportActionBar().hide();

        int lat = 0;
        int lon = 0;
        if(getIntent().getIntExtra("LAT", -1) != -1) {
            lat = getIntent().getIntExtra("LAT", -1);
            lon = getIntent().getIntExtra("LON", -1);
            Toast.makeText(getBaseContext(), "New lat: " + lat + "; New lon: " + lon, Toast.LENGTH_SHORT).show();
        }
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
