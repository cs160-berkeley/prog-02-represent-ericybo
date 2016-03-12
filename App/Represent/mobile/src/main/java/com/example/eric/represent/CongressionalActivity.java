package com.example.eric.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class CongressionalActivity extends AppCompatActivity {

    private String lat, lon, zipcode;
    private String state, county, obama, romney;
    static String name1, name2, name3, name4;
    static String party1, party2, party3, party4;
    static String senOrRep1, senOrRep2, senOrRep3, senOrRep4;
    static String email1, email2, email3, email4;
    static String website1, website2, website3, website4;
    static String term1, term2, term3, term4;
    static String bioguide1, bioguide2, bioguide3, bioguide4;
    static ArrayList<String> comms1, comms2, comms3, comms4;
    static ArrayList<String> bills1, bills2, bills3, bills4;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "5oLRV34qtu4WvRIqbs7Xq6jTp";
    private static final String TWITTER_SECRET = "dmskpiX2zeoR3LDh7d7gA4rDFWJ672d6iQzYi8B6LwZz1x79k5";
    private static final String SUNLIGHT_KEY = "4111cd329a064f25bb8c4848abf49841";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_congressional);

        getSupportActionBar().hide();

        Intent intent = getIntent();

        comms1 = new ArrayList<String>();
        comms2 = new ArrayList<String>();
        comms3 = new ArrayList<String>();
        comms4 = new ArrayList<String>();
        bills1 = new ArrayList<String>();
        bills2 = new ArrayList<String>();
        bills3 = new ArrayList<String>();
        bills4 = new ArrayList<String>();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (intent.getStringExtra("RANDOM") != null) {
            zipcode = getIntent().getStringExtra("zipcode");
            Toast.makeText(getBaseContext(), "New zipcode: " + zipcode, Toast.LENGTH_SHORT).show();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadWebpageTask2().execute("zipcode");
                new DownloadWebpageTask().execute("zipcode");
            } else {
                Toast.makeText(getBaseContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (intent.getStringExtra("lat") != null) {
                lat = intent.getStringExtra("lat");
                lon = intent.getStringExtra("lon");
                Log.d("TAG", "lat: " + lat);
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageTask2().execute("");
                    new DownloadWebpageTask().execute("");
                } else {
                    Toast.makeText(getBaseContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
            else if (intent.getStringExtra("zipcode") != null) {
                zipcode = intent.getStringExtra("zipcode");
                Log.d("TAG", "zipcode: " + zipcode);
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageTask2().execute("zipcode");
                    new DownloadWebpageTask().execute("zipcode");
                } else {
                    Toast.makeText(getBaseContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    public class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                if (urls[0].equals("zipcode")) {
                    String myurl = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipcode + "&apikey=" + SUNLIGHT_KEY;
                    return lookup(myurl);
                } else {
                    String myurl = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + lat + "&longitude=" + lon + "&apikey=" + SUNLIGHT_KEY;
                    return lookup(myurl);
                }
            } catch (IOException e) {
                return "1 Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
            try{
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.optJSONArray("results");
                if(jArray.length() == 4) {
                    senOrRep1 = (jArray.getJSONObject(0)).optString("title");
                    name1 = (jArray.getJSONObject(0)).optString("first_name") + " " + (jArray.getJSONObject(0)).optString("last_name");
                    party1 = (jArray.getJSONObject(0)).optString("party");
                    email1 = (jArray.getJSONObject(0)).optString("oc_email");
                    website1 = (jArray.getJSONObject(0)).optString("website");
                    term1 = (jArray.getJSONObject(0)).optString("term_end");
                    bioguide1 = (jArray.getJSONObject(0)).optString("bioguide_id");
                    String pic1 = "https://theunitedstates.io/images/congress/225x275/" + bioguide1 + ".jpg";
                    ImageView rep1Image = (ImageView) findViewById(R.id.rep1Image);
                    Picasso.with(getBaseContext()).load(pic1).into(rep1Image);
                    if (party1.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg1);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep1Name = (TextView) findViewById(R.id.rep1Name);
                    rep1Name.setText(senOrRep1 + " " + name1);
                    TextView rep1Party = (TextView) findViewById(R.id.rep1Party);
                    rep1Party.setText(party1);
                    TextView rep1Email = (TextView) findViewById(R.id.rep1Email);
                    rep1Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email1 + "\">Email</a> "));
                    rep1Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep1Website = (TextView) findViewById(R.id.rep1Website);
                    rep1Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website1 + "\">Website</a> "));
                    rep1Website.setMovementMethod(LinkMovementMethod.getInstance());
                    senOrRep2 = (jArray.getJSONObject(1)).optString("title");
                    name2 = (jArray.getJSONObject(1)).optString("first_name") + " " + (jArray.getJSONObject(1)).optString("last_name");
                    party2 = (jArray.getJSONObject(1)).optString("party");
                    email2 = (jArray.getJSONObject(1)).optString("oc_email");
                    website2 = (jArray.getJSONObject(1)).optString("website");
                    term2 = (jArray.getJSONObject(1)).optString("term_end");
                    bioguide2 = (jArray.getJSONObject(1)).optString("bioguide_id");
                    String pic2 = "https://theunitedstates.io/images/congress/225x275/" + bioguide2 + ".jpg";
                    ImageView rep2Image = (ImageView) findViewById(R.id.rep2Image);
                    Picasso.with(getBaseContext()).load(pic2).into(rep2Image);
                    if (party2.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg2);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep2Name = (TextView) findViewById(R.id.rep2Name);
                    rep2Name.setText(senOrRep2 + " " + name2);
                    TextView rep2Party = (TextView) findViewById(R.id.rep2Party);
                    rep2Party.setText(party2);
                    TextView rep2Email = (TextView) findViewById(R.id.rep2Email);
                    rep2Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email2 + "\">Email</a> "));
                    rep2Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep2Website = (TextView) findViewById(R.id.rep2Website);
                    rep2Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website2 + "\">Website</a> "));
                    rep2Website.setMovementMethod(LinkMovementMethod.getInstance());
                    senOrRep3 = (jArray.getJSONObject(2)).optString("title");
                    name3 = (jArray.getJSONObject(2)).optString("first_name") + " " + (jArray.getJSONObject(2)).optString("last_name");
                    party3 = (jArray.getJSONObject(2)).optString("party");
                    email3 = (jArray.getJSONObject(2)).optString("oc_email");
                    website3 = (jArray.getJSONObject(2)).optString("website");
                    term3 = (jArray.getJSONObject(2)).optString("term_end");
                    bioguide3 = (jArray.getJSONObject(2)).optString("bioguide_id");
                    String pic3 = "https://theunitedstates.io/images/congress/225x275/" + bioguide3 + ".jpg";
                    ImageView rep3Image = (ImageView) findViewById(R.id.rep3Image);
                    Picasso.with(getBaseContext()).load(pic3).into(rep3Image);
                    if (party3.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg3);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep3Name = (TextView) findViewById(R.id.rep3Name);
                    rep3Name.setText(senOrRep3 + " " + name3);
                    TextView rep3Party = (TextView) findViewById(R.id.rep3Party);
                    rep3Party.setText(party3);
                    TextView rep3Email = (TextView) findViewById(R.id.rep3Email);
                    rep3Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email3 + "\">Email</a> "));
                    rep3Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep3Website = (TextView) findViewById(R.id.rep3Website);
                    rep3Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website3 + "\">Website</a> "));
                    rep3Website.setMovementMethod(LinkMovementMethod.getInstance());
                    senOrRep4 = (jArray.getJSONObject(3)).optString("title");
                    name4 = (jArray.getJSONObject(3)).optString("first_name") + " " + (jArray.getJSONObject(3)).optString("last_name");
                    party4 = (jArray.getJSONObject(3)).optString("party");
                    email4 = (jArray.getJSONObject(3)).optString("oc_email");
                    website4 = (jArray.getJSONObject(3)).optString("website");
                    term4 = (jArray.getJSONObject(3)).optString("term_end");
                    bioguide4 = (jArray.getJSONObject(3)).optString("bioguide_id");
                    String pic4 = "https://theunitedstates.io/images/congress/225x275/" + bioguide4 + ".jpg";
                    ImageView rep4Image = (ImageView) findViewById(R.id.rep4Image);
                    Picasso.with(getBaseContext()).load(pic4).into(rep4Image);
                    if (party4.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg4);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep4Name = (TextView) findViewById(R.id.rep4Name);
                    rep4Name.setText(senOrRep4 + " " + name4);
                    TextView rep4Party = (TextView) findViewById(R.id.rep4Party);
                    rep4Party.setText(party4);
                    TextView rep4Email = (TextView) findViewById(R.id.rep4Email);
                    rep4Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email4 + "\">Email</a> "));
                    rep4Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep4Website = (TextView) findViewById(R.id.rep4Website);
                    rep4Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website4 + "\">Website</a> "));
                    rep4Website.setMovementMethod(LinkMovementMethod.getInstance());
                    String data = senOrRep1 + ";" + name1 + ";" + party1 + ";" + senOrRep2 + ";" + name2 + ";" + party2 + ";" + senOrRep3 + ";" + name3 + ";" + party3 + ";" + senOrRep4 + ";" + name4 + ";" + party4;
                    sendIntent.putExtra("data", data);
                    if (zipcode != null) {
                        sendIntent.putExtra("zipOrLatLon", zipcode);
                    } else {
                        sendIntent.putExtra("zipOrLatLon", lat + "_" + lon);
                    }
                    find2012VoteData();
                    sendIntent.putExtra("state", state);
                    sendIntent.putExtra("county", county);
                    sendIntent.putExtra("obama", obama);
                    sendIntent.putExtra("romney", romney);
                    startService(sendIntent);
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide1 + "&apikey=" + SUNLIGHT_KEY, "1");
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide2 + "&apikey=" + SUNLIGHT_KEY, "2");
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide3 + "&apikey=" + SUNLIGHT_KEY, "3");
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide4 + "&apikey=" + SUNLIGHT_KEY, "4");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide1 + "&apikey=" + SUNLIGHT_KEY, "1");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide2 + "&apikey=" + SUNLIGHT_KEY, "2");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide3 + "&apikey=" + SUNLIGHT_KEY, "3");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide4 + "&apikey=" + SUNLIGHT_KEY, "4");
                } else {
                    RelativeLayout rep4 = (RelativeLayout)findViewById(R.id.rep4);
                    rep4.setVisibility(View.GONE);
                    senOrRep1 = (jArray.getJSONObject(0)).optString("title");
                    name1 = (jArray.getJSONObject(0)).optString("first_name") + " " + (jArray.getJSONObject(0)).optString("last_name");
                    party1 = (jArray.getJSONObject(0)).optString("party");
                    email1 = (jArray.getJSONObject(0)).optString("oc_email");
                    website1 = (jArray.getJSONObject(0)).optString("website");
                    term1 = (jArray.getJSONObject(0)).optString("term_end");
                    bioguide1 = (jArray.getJSONObject(0)).optString("bioguide_id");
                    String pic1 = "https://theunitedstates.io/images/congress/225x275/" + bioguide1 + ".jpg";
                    ImageView rep1Image = (ImageView) findViewById(R.id.rep1Image);
                    Picasso.with(getBaseContext()).load(pic1).into(rep1Image);
                    if (party1.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg1);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep1Name = (TextView) findViewById(R.id.rep1Name);
                    rep1Name.setText(senOrRep1 + " " + name1);
                    TextView rep1Party = (TextView) findViewById(R.id.rep1Party);
                    rep1Party.setText(party1);
                    TextView rep1Email = (TextView) findViewById(R.id.rep1Email);
                    rep1Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email1 + "\">Email</a> "));
                    rep1Email.setMovementMethod(LinkMovementMethod.getInstance());

//                    rep1Email.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                            emailIntent.setType("plain/text");
//                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email1});
//                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
//                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
//                            startActivity(emailIntent.createChooser(emailIntent, "Send mail..."));
//                        }
//                    });

                    TextView rep1Website = (TextView) findViewById(R.id.rep1Website);
                    rep1Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website1 + "\">Website</a> "));
                    rep1Website.setMovementMethod(LinkMovementMethod.getInstance());
                    senOrRep2 = (jArray.getJSONObject(1)).optString("title");
                    name2 = (jArray.getJSONObject(1)).optString("first_name") + " " + (jArray.getJSONObject(1)).optString("last_name");
                    party2 = (jArray.getJSONObject(1)).optString("party");
                    email2 = (jArray.getJSONObject(1)).optString("oc_email");
                    website2 = (jArray.getJSONObject(1)).optString("website");
                    term2 = (jArray.getJSONObject(1)).optString("term_end");
                    bioguide2 = (jArray.getJSONObject(1)).optString("bioguide_id");
                    String pic2 = "https://theunitedstates.io/images/congress/225x275/" + bioguide2 + ".jpg";
                    ImageView rep2Image = (ImageView) findViewById(R.id.rep2Image);
                    Picasso.with(getBaseContext()).load(pic2).into(rep2Image);
                    if (party2.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg2);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep2Name = (TextView) findViewById(R.id.rep2Name);
                    rep2Name.setText(senOrRep2 + " " + name2);
                    TextView rep2Party = (TextView) findViewById(R.id.rep2Party);
                    rep2Party.setText(party2);
                    TextView rep2Email = (TextView) findViewById(R.id.rep2Email);
                    rep2Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email2 + "\">Email</a> "));
                    rep2Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep2Website = (TextView) findViewById(R.id.rep2Website);
                    rep2Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website2 + "\">Website</a> "));
                    rep2Website.setMovementMethod(LinkMovementMethod.getInstance());
                    senOrRep3 = (jArray.getJSONObject(2)).optString("title");
                    name3 = (jArray.getJSONObject(2)).optString("first_name") + " " + (jArray.getJSONObject(2)).optString("last_name");
                    party3 = (jArray.getJSONObject(2)).optString("party");
                    email3 = (jArray.getJSONObject(2)).optString("oc_email");
                    website3 = (jArray.getJSONObject(2)).optString("website");
                    term3 = (jArray.getJSONObject(2)).optString("term_end");
                    bioguide3 = (jArray.getJSONObject(2)).optString("bioguide_id");
                    String pic3 = "https://theunitedstates.io/images/congress/225x275/" + bioguide3 + ".jpg";
                    ImageView rep3Image = (ImageView) findViewById(R.id.rep3Image);
                    Picasso.with(getBaseContext()).load(pic3).into(rep3Image);
                    if (party3.equals("R")) {
                        RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg3);
                        bg.setBackground(new ColorDrawable(0x80ff0000));
                    }
                    TextView rep3Name = (TextView) findViewById(R.id.rep3Name);
                    rep3Name.setText(senOrRep3 + " " + name3);
                    TextView rep3Party = (TextView) findViewById(R.id.rep3Party);
                    rep3Party.setText(party3);
                    TextView rep3Email = (TextView) findViewById(R.id.rep3Email);
                    rep3Email.setText(
                            Html.fromHtml(
                                    "<a href=\"" + email3 + "\">Email</a> "));
                    rep3Email.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView rep3Website = (TextView) findViewById(R.id.rep3Website);
                    rep3Website.setText(
                            Html.fromHtml(
                                    "<a href=\"" + website3 + "\">Website</a> "));
                    rep3Website.setMovementMethod(LinkMovementMethod.getInstance());
                    String data = senOrRep1 + ";" + name1 + ";" + party1 + ";" + senOrRep2 + ";" + name2 + ";" + party2 + ";" + senOrRep3 + ";" + name3 + ";" + party3;
                    sendIntent.putExtra("data", data);
                    if (zipcode != null) {
                        sendIntent.putExtra("zipOrLatLon", zipcode);
                    } else {
                        sendIntent.putExtra("zipOrLatLon", lat + "_" + lon);
                    }
                    find2012VoteData();
                    sendIntent.putExtra("state", state);
                    sendIntent.putExtra("county", county);
                    sendIntent.putExtra("obama", obama);
                    sendIntent.putExtra("romney", romney);
                    startService(sendIntent);
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide1 + "&apikey=" + SUNLIGHT_KEY, "1");
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide2 + "&apikey=" + SUNLIGHT_KEY, "2");
                    new DownloadWebpageTask3().execute("https://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide3 + "&apikey=" + SUNLIGHT_KEY, "3");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide1 + "&apikey=" + SUNLIGHT_KEY, "1");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide2 + "&apikey=" + SUNLIGHT_KEY, "2");
                    new DownloadWebpageTask4().execute("https://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide3 + "&apikey=" + SUNLIGHT_KEY, "3");
                }
            }
            catch (Exception e) {
                Log.d("TAG", "1 The exception is " + e);
            }
        }
    }

    public class DownloadWebpageTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                if (urls[0].equals("zipcode")) {
                    String myurl = "http://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode;
                    return lookup(myurl);
                } else {
//                    String myurl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&key=" + "AIzaSyCSYnfoCYM5mLh29NPtnBkYvV8Ei99F030";
                    String myurl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon;
                    return lookup(myurl);
                }
            } catch (IOException e) {
                return "2 Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.optJSONArray("results");
                JSONArray address_components = jArray.getJSONObject(0).optJSONArray("address_components");
                for (int x = 0; x < address_components.length(); x++) {
                    JSONArray temp = address_components.getJSONObject(x).optJSONArray("types");
                    if (temp.get(0).equals("administrative_area_level_1")) {
                        state = address_components.getJSONObject(x).optString("short_name");
                        Log.d("TAG", "found state! " + state);
                    } else if (temp.get(0).equals("administrative_area_level_2")) {
                        county = address_components.getJSONObject(x).optString("short_name");
                        Log.d("TAG", "found county! " + county);
                    }
                }
            }
            catch (Exception e) {
                Log.d("TAG", "2 The exception is " + e);
            }
        }
    }

    private void find2012VoteData() {
        try {
            InputStream stream = getAssets().open("new-election-county-2012.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONObject json = new JSONObject(jsonString);
            String key = county + ", " + state;
            obama = "" + (json.getJSONObject(key)).get("obama");
            romney = "" + (json.getJSONObject(key)).get("romney");
        } catch (Exception e) {
            Log.d("TAG", "Vote, The exception is " + e);
        }
    }

    public class DownloadWebpageTask3 extends AsyncTask<String, Void, String> {
        private String repnum;
        @Override
        protected String doInBackground(String... urls) {
            try {
                String myurl = urls[0];
                repnum = urls[1];
                return lookup(myurl);
            } catch (IOException e) {
                return "3 Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.optJSONArray("results");
                for (int x = 0; x < json.getInt("count"); x++) {
                    if (repnum.equals("1")) {
                        comms1.add(jArray.getJSONObject(x).optString("name"));
                    }
                    if (repnum.equals("2")) {
                        comms2.add(jArray.getJSONObject(x).optString("name"));
                    }
                    if (repnum.equals("3")) {
                        comms3.add(jArray.getJSONObject(x).optString("name"));
                    }
                    if (repnum.equals("4")) {
                        comms4.add(jArray.getJSONObject(x).optString("name"));
                    }
                }
            }
            catch (Exception e) {
                Log.d("TAG", "3 The exception is " + e);
            }
        }
    }

    public class DownloadWebpageTask4 extends AsyncTask<String, Void, String> {
        private String repnum;
        @Override
        protected String doInBackground(String... urls) {
            try {
                String myurl = urls[0];
                repnum = urls[1];
                return lookup(myurl);
            } catch (IOException e) {
                return "4 Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.optJSONArray("results");
                int x = 0;
                while (jArray.get(x) != null && x < 5) {
                    x++;
                    if (!jArray.getJSONObject(x).optString("short_title").equals("null")) {
                        if (repnum.equals("1")) {
                            bills1.add(jArray.getJSONObject(x).optString("short_title") + "\nIntroduced on: " + jArray.getJSONObject(x).optString("introduced_on"));
                        }
                        if (repnum.equals("2")) {
                            bills2.add(jArray.getJSONObject(x).optString("short_title") + "\nIntroduced on: " + jArray.getJSONObject(x).optString("introduced_on"));
                        }
                        if (repnum.equals("3")) {
                            bills3.add(jArray.getJSONObject(x).optString("short_title") + "\nIntroduced on: " + jArray.getJSONObject(x).optString("introduced_on"));
                        }
                        if (repnum.equals("4")) {
                            bills4.add(jArray.getJSONObject(x).optString("short_title") + "\nIntroduced on: " + jArray.getJSONObject(x).optString("introduced_on"));
                        }
                    }
                }
            }
            catch (Exception e) {
                Log.d("TAG", "4 The exception is " + e);
            }
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String lookup(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            if (response != 200) {
                Log.d("TAG", "The response is: " + response);
            }
            is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
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
