package com.example.eric.represent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

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
            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
