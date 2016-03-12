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
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.TextView;

public class WearActivity extends Activity {

    private SensorManager mSensorManager;
    private ShakeListener mSensorListener;
    private String zipOrLatLon, state, county, obama, romney;

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

    // A simple container for static data in each page
    private static class Page {
        String party;
        String senOrRep;
        String name;
        String num;

        public Page(String p, String s, String n, String u) {
            party = p;
            senOrRep = s;
            name = n;
            num = u;
        }
    }

    // Create a static set of pages in a 1D array
    private Page[] pages = new Page[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        mSensorListener = new ShakeListener();
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
            zipOrLatLon = extras.getString("zipOrLatLon");
            state = extras.getString("state");
            county = extras.getString("county");
            obama = extras.getString("obama");
            romney = extras.getString("romney");
            if (extras.getString("3or4").equals("4")) {
                pages = new Page[4];
                String party = extras.getString("party4");
                String senOrRep = extras.getString("senOrRep4");
                String name = extras.getString("name4");
                pages[3] = new Page(party, senOrRep, name, "4");
                Log.d("TAG", "4 reps");
            }
            for (int x = 1; x < 4; x++) {
                String party = extras.getString("party" + x);
                String senOrRep = extras.getString("senOrRep" + x);
                String name = extras.getString("name" + x);
                pages[x-1] = new Page(party, senOrRep, name, x + "");
                //Log.d("TAG", name);
            }
        }
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
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

    class ShakeListener implements SensorEventListener {
        private boolean init = true;
        private float x, y, z;

        public void onSensorChanged(SensorEvent se) {
            float x2 = se.values[0];
            float y2 = se.values[1];
            float z2 = se.values[2];
            float dx = x2-x;
            float dy = y2-y;
            float dz = z2-z;
            if (!init) {
                if (dx*dx + dy*dy + dz*dz > 100) {
                    //go to random zip
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("SHAKE", "yes");
                    startService(sendIntent);
                }
            }
            x = x2;
            y = y2;
            z = z2;
            init = false;
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    public class GridPagerAdapter extends FragmentGridPagerAdapter {

        private final Context mContext;

        public GridPagerAdapter(Context ctx, FragmentManager fm) {
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
            return pages.length + 1; //+1 for vote view
        }

        // Override methods in FragmentGridPagerAdapter
        // Obtain the UI fragment at the specified position
        @Override
        public Fragment getFragment(int useless, int index) {
            if (index == pages.length) {
                if (obama.equals("null")) {
                    final CardFragment voteFragment = CardFragment.create("2012 Pres. Vote"
                            , state + "\n" + "No voting data available");
                    return voteFragment;
                } else {
                    final CardFragment voteFragment = CardFragment.create("2012 Pres. Vote"
                            , county + ", " + state + "\n" + "Obama - " + obama + "%" + "\n" + "Romney - " + romney + "%");
                    return voteFragment;
                }
            }
            Log.d("TAG", "index: " + index);
            Page page = pages[index];
            String line1 = "(" + page.party + ") " + page.senOrRep;
            String line2 = page.name;
            ActionFragment.Listener temp1 = new ActionFragment.Listener() {
                @Override
                public void onActionPerformed() {
                    //switch to this congressperon's detailed view
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repnum", "1");
                    if (zipOrLatLon.length() == 5) {
                        sendIntent.putExtra("zipcode", zipOrLatLon);
                    } else {
                        String[] temp = zipOrLatLon.split("_");
                        String lat = temp[0];
                        String lon = temp[1];
                        sendIntent.putExtra("lat", lat);
                        sendIntent.putExtra("lon", lon);
                    }
                    startService(sendIntent);
                }
            };
            ActionFragment.Listener temp2 = new ActionFragment.Listener() {
                @Override
                public void onActionPerformed() {
                    //switch to this congressperon's detailed view
                    //switch to this congressperon's detailed view
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repnum", "2");
                    if (zipOrLatLon.length() == 5) {
                        sendIntent.putExtra("zipcode", zipOrLatLon);
                    } else {
                        String[] temp = zipOrLatLon.split("_");
                        String lat = temp[0];
                        String lon = temp[1];
                        sendIntent.putExtra("lat", lat);
                        sendIntent.putExtra("lon", lon);
                    }
                    startService(sendIntent);
                }
            };
            ActionFragment.Listener temp3 = new ActionFragment.Listener() {
                @Override
                public void onActionPerformed() {
                    //switch to this congressperon's detailed view
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repnum", "3");
                    if (zipOrLatLon.length() == 5) {
                        sendIntent.putExtra("zipcode", zipOrLatLon);
                    } else {
                        String[] temp = zipOrLatLon.split("_");
                        String lat = temp[0];
                        String lon = temp[1];
                        sendIntent.putExtra("lat", lat);
                        sendIntent.putExtra("lon", lon);
                    }
                    startService(sendIntent);
                }
            };
            ActionFragment.Listener temp4 = new ActionFragment.Listener() {
                @Override
                public void onActionPerformed() {
                    //switch to this congressperon's detailed view
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("repnum", "4");
                    if (zipOrLatLon.length() == 5) {
                        sendIntent.putExtra("zipcode", zipOrLatLon);
                    } else {
                        String[] temp = zipOrLatLon.split("_");
                        String lat = temp[0];
                        String lon = temp[1];
                        sendIntent.putExtra("lat", lat);
                        sendIntent.putExtra("lon", lon);
                    }
                    startService(sendIntent);
                }
            };
            if (page.num.equals("1")) {
                Log.d("TAG", "num " + page.num);
                final Fragment conFragment = ActionFragment.create(line1, line2, temp1);
                return conFragment;
            } else if (page.num.equals("2")) {
                Log.d("TAG", "num " + page.num);
                final Fragment conFragment = ActionFragment.create(line1, line2, temp2);
                return conFragment;
            } else if (page.num.equals("3")) {
                Log.d("TAG", "num " + page.num);
                final Fragment conFragment = ActionFragment.create(line1, line2, temp3);
                return conFragment;
            } else {
                Log.d("TAG", "num " + page.num);
                final Fragment conFragment = ActionFragment.create(line1, line2, temp4);
                return conFragment;
            }
        }
    };
}