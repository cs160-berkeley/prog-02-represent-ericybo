package com.example.eric.represent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.TextView;

public class WearActivity extends Activity implements SensorEventListener {

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
//            mAccelLast = mAccelCurrent;
//            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
//            float delta = mAccelCurrent - mAccelLast;
//            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // Use values from event.values array
        // do stuff for shake here!!!
    }

    // A simple container for static data in each page
    private static class Page {
        String party;
        String senOrRep;
        String name;

        public Page(String p, String s, String n) {
            party = p;
            senOrRep = s;
            name = n;
        }
    }

    // Create a static set of pages in a 1D array
    private Page[] pages = new Page[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            CardFragment cardFragment = CardFragment.create("Represent!", "");
//            fragmentTransaction.add(R.id.background, cardFragment);
//            fragmentTransaction.commit();
            return;
        } else {
            if (extras.getString("3or4").equals("4")) {
                pages = new Page[4];
                String party = extras.getString("party4");
                String senOrRep = extras.getString("senOrRep4");
                String name = extras.getString("name4");
                pages[3] = new Page(party, senOrRep, name);
            }
            for (int x = 1; x < 4; x++) {
                String party = extras.getString("party" + x);
                String senOrRep = extras.getString("senOrRep" + x);
                String name = extras.getString("name" + x);
                pages[x-1] = new Page(party, senOrRep, name);
                //Log.d("TAG", name);
            }
        }
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
//        final String party = extras.getString("party3");
//        final String senOrRep = extras.getString("senOrRep3");
//        final String name = extras.getString("name3");
        //final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        //stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            //@Override
            //public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);
                //BoxInsetLayout bg = (BoxInsetLayout) findViewById(R.id.background);
                //bg.setBackgroundResource(R.drawable.warner);
                //TextView line1 = (TextView) findViewById(R.id.line1);
                //line1.setText("(" + party + ") " + senOrRep);
                //TextView line2 = (TextView) findViewById(R.id.line2);
                //line2.setText(name);
            //}
        //});

    }

    public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

        private final Context mContext;

        public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;
        }

        // Obtain the number of pages (vertical)
        @Override
        public int getRowCount() {
            return 1;
        }

        // Obtain the number of pages (horizontal)
        @Override
        public int getColumnCount(int rowNum) {
            return pages.length;
        }

        // Override methods in FragmentGridPagerAdapter
        // Obtain the UI fragment at the specified position
        @Override
        public Fragment getFragment(int useless, int index) {
            Page page = pages[index];
            final String repnum = index + 1 + "";
            String line1 = "(" + page.party + ") " + page.senOrRep;
                    //page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
            String line2 = page.name;
                    //page.textRes != 0 ? mContext.getString(page.textRes) : null;
            //CardFragment conFragment = CardFragment.create(line1, line2);

            final Fragment conFragment = ActionFragment.create(line1, line2, new ActionFragment.Listener() {
                @Override
                public void onActionPerformed() {
                    Log.d("TAG", "IM CLICKABLE");
                    Log.d("TAG", repnum);
                    //Toast.makeText(getBaseContext(), "I am clickable!!!", Toast.LENGTH_SHORT).show();
                    //switch to this congressperon's detailed view
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repnum", repnum);
                    startService(sendIntent);
                }
            });

            // Advanced settings (card gravity, card expansion/scrolling)
//            fragment.setCardGravity(page.cardGravity);
//            fragment.setExpansionEnabled(page.expansionEnabled);
//            fragment.setExpansionDirection(page.expansionDirection);
//            fragment.setExpansionFactor(page.expansionFactor);
            return conFragment;
        }
    };
}
