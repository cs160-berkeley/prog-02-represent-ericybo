package com.example.eric.represent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {
    //extends more than just service - wearablelistenerservices are always on
    //go see android manifest! notice the intent-filter
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    // private static final String FRED_FEED = "/Fred";
    // private static final String LEXY_FEED = "/Lexy";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //parse data here and put them into diff extra for the intent to be sent to WearActivity
        Intent intent = new Intent(this, WearActivity.class);
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        String[] path = messageEvent.getPath().split("\\|");
        String zipOrLatLon = path[0];
        intent.putExtra("zipOrLatLon", zipOrLatLon);
        String state = path[1];
        String county = path[2];
        String obama = path[3];
        String romney = path[4];
        intent.putExtra("state", state);
        intent.putExtra("county", county);
        intent.putExtra("obama", obama);
        intent.putExtra("romney", romney);
        String[] data = value.split("\\;");
        String[] keys = {"senOrRep1", "name1", "party1", "senOrRep2", "name2", "party2", "senOrRep3", "name3", "party3", "senOrRep4", "name4", "party4"};
        for (int i = 0; i < data.length; i++) {
            //Log.d("TAG", "key: " + keys[i] + "; data: " + data[i]);
            intent.putExtra(keys[i], data[i]);
            if (i == 0) {
                intent.putExtra("3or4", "3");
            }
            if (i == 9) {
                intent.putExtra("3or4", "4");
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        //Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use log statements for debugging!!
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

//        if( messageEvent.getPath().equalsIgnoreCase( FRED_FEED ) ) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, WearActivity.class );
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Fred");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
//            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, WearActivity.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
//        } else {
//            super.onMessageReceived( messageEvent );
//        }

    }
}
