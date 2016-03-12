package com.example.eric.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {
    //declared in the manifest with the special stuff because it extends WearableListenerService
//not the same as service so doesn't have an ibinder
//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    //private static final String TOAST = "/send_toast";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("TAG", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase("/path") ) {
            //open the detailed view on the phone for rep #
            Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            intent.putExtra("REP NUM", Integer.parseInt(value));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if ( messageEvent.getPath().equalsIgnoreCase("/shake") ) {
            Intent intent = new Intent(getBaseContext(), CongressionalActivity.class);
            //generate RANDOM location here
            String randomZip = "";
            try {
                InputStream stream = getAssets().open("valid-zips.json");
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                String jsonString = new String(buffer, "UTF-8");
                JSONArray jArray = new JSONArray(jsonString);
                int random = (int)(Math.random()*(jArray.length()-1));
                randomZip = jArray.getString(random);
            } catch (Exception e) {
                Log.d("TAG", "Random, The exception is " + e);
            }
            intent.putExtra("RANDOM", "RANDOM");
            intent.putExtra("zipcode", randomZip);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
