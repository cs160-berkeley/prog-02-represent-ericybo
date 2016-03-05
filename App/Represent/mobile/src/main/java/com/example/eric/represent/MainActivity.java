package com.example.eric.represent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button locationButton;
    private Button zipCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        locationButton = (Button) findViewById(R.id.locationButton);
        zipCodeButton = (Button) findViewById(R.id.zipCodeButton);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CongressionalActivity.class);
                startActivity(intent);
                //start the service here to make this click do stuff on the watch too
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                String location = "how do i get current location??";
                sendIntent.putExtra("data", lookup(location));
                startService(sendIntent);
            }
        });

        zipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CongressionalActivity.class);
                EditText editText = (EditText) findViewById(R.id.zipCodeEditText);
                String zipcode = editText.getText().toString();
                if (zipcode.length() != 5) {
                    Toast.makeText(getBaseContext(), "Please enter a Zip Code!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                    Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("data", lookup(zipcode));
                    startService(sendIntent);
                }
            }
        });
    }

    public String lookup(String zipcode) {
        //find data using API and zipcode
        String senOrRep1 = "Senator";
        String name1 = "Mark Warner";
        String party1 = "D";
        String senOrRep2 = "Senator";
        String name2 = "Tim Kaine";
        String party2 = "D";
        String senOrRep3 = "Rep.";
        String name3 = "Donald S. Beyer";
        String party3 = "D";
        //String senOrRep4 = "Sen.";
        //String name4 = "Mark Warner";
        //String party4 = "D";
        return senOrRep1 + ";" + name1 + ";" + party1 + ";" + senOrRep2 + ";" + name2 + ";" + party2 + ";" + senOrRep3 + ";" + name3 + ";" + party3;
    }
}
