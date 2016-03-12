package com.example.eric.represent;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        int repnum = intent.getIntExtra("REP NUM", 0);
        //LayoutInflater layin = from(this);
        RelativeLayout item = (RelativeLayout)findViewById(R.id.parent);
        LinearLayout comms = (LinearLayout)findViewById(R.id.comms);
        LinearLayout bills = (LinearLayout)findViewById(R.id.bills);
        if (repnum == 1) {
            View child = getLayoutInflater().inflate(R.layout.rep1, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep1Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + CongressionalActivity.term1);
            for(int x = 0; x < CongressionalActivity.comms1.size(); x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText(CongressionalActivity.comms1.get(x));
                comms.addView(comm);
            }
            for(int x = 0; x < CongressionalActivity.bills1.size(); x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText(CongressionalActivity.bills1.get(x));
                bills.addView(bill);
            }
            if (CongressionalActivity.party1.equals("R")) {
                RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg1);
                bg.setBackground(new ColorDrawable(0x80ff0000));
            }
            String pic1 = "https://theunitedstates.io/images/congress/225x275/" + CongressionalActivity.bioguide1 + ".jpg";
            ImageView rep1Image = (ImageView) findViewById(R.id.rep1Image);
            Picasso.with(getBaseContext()).load(pic1).into(rep1Image);
            TextView rep1Name = (TextView) findViewById(R.id.rep1Name);
            rep1Name.setText(CongressionalActivity.senOrRep1 + " " + CongressionalActivity.name1);
            TextView rep1Party = (TextView) findViewById(R.id.rep1Party);
            rep1Party.setText(CongressionalActivity.party1);
            TextView rep1Email = (TextView) findViewById(R.id.rep1Email);
            rep1Email.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.email1 + "\">Email</a> "));
            rep1Email.setMovementMethod(LinkMovementMethod.getInstance());
            TextView rep1Website = (TextView) findViewById(R.id.rep1Website);
            rep1Website.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.website1 + "\">Website</a> "));
            rep1Website.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (repnum == 2) {
            View child = getLayoutInflater().inflate(R.layout.rep2, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep2Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + CongressionalActivity.term2);
            for(int x = 0; x < CongressionalActivity.comms2.size(); x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText(CongressionalActivity.comms2.get(x));
                comms.addView(comm);
            }
            for(int x = 0; x < CongressionalActivity.bills2.size(); x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText(CongressionalActivity.bills2.get(x));
                bills.addView(bill);
            }
            if (CongressionalActivity.party2.equals("R")) {
                RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg2);
                bg.setBackground(new ColorDrawable(0x80ff0000));
            }
            String pic2 = "https://theunitedstates.io/images/congress/225x275/" + CongressionalActivity.bioguide2 + ".jpg";
            ImageView rep2Image = (ImageView) findViewById(R.id.rep2Image);
            Picasso.with(getBaseContext()).load(pic2).into(rep2Image);
            TextView rep2Name = (TextView) findViewById(R.id.rep2Name);
            rep2Name.setText(CongressionalActivity.senOrRep2 + " " + CongressionalActivity.name2);
            TextView rep2Party = (TextView) findViewById(R.id.rep2Party);
            rep2Party.setText(CongressionalActivity.party2);
            TextView rep2Email = (TextView) findViewById(R.id.rep2Email);
            rep2Email.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.email2 + "\">Email</a> "));
            rep2Email.setMovementMethod(LinkMovementMethod.getInstance());
            TextView rep2Website = (TextView) findViewById(R.id.rep2Website);
            rep2Website.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.website2 + "\">Website</a> "));
            rep2Website.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (repnum == 3) {
            View child = getLayoutInflater().inflate(R.layout.rep3, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep3Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + CongressionalActivity.term3);
            for(int x = 0; x < CongressionalActivity.comms3.size(); x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText(CongressionalActivity.comms3.get(x));
                comms.addView(comm);
            }
            for(int x = 0; x < CongressionalActivity.bills3.size(); x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText(CongressionalActivity.bills3.get(x));
                bills.addView(bill);
            }
            if (CongressionalActivity.party3.equals("R")) {
                RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg3);
                bg.setBackground(new ColorDrawable(0x80ff0000));
            }
            String pic3 = "https://theunitedstates.io/images/congress/225x275/" + CongressionalActivity.bioguide3 + ".jpg";
            ImageView rep3Image = (ImageView) findViewById(R.id.rep3Image);
            Picasso.with(getBaseContext()).load(pic3).into(rep3Image);
            TextView rep3Name = (TextView) findViewById(R.id.rep3Name);
            rep3Name.setText(CongressionalActivity.senOrRep3 + " " + CongressionalActivity.name3);
            TextView rep3Party = (TextView) findViewById(R.id.rep3Party);
            rep3Party.setText(CongressionalActivity.party3);
            TextView rep3Email = (TextView) findViewById(R.id.rep3Email);
            rep3Email.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.email3 + "\">Email</a> "));
            rep3Email.setMovementMethod(LinkMovementMethod.getInstance());
            TextView rep3Website = (TextView) findViewById(R.id.rep3Website);
            rep3Website.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.website3 + "\">Website</a> "));
            rep3Website.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (repnum == 4) {
            View child = getLayoutInflater().inflate(R.layout.rep4, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep4Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + CongressionalActivity.term4);
            for(int x = 0; x < CongressionalActivity.comms4.size(); x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText(CongressionalActivity.comms4.get(x));
                comms.addView(comm);
            }
            for(int x = 0; x < CongressionalActivity.bills4.size(); x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText(CongressionalActivity.bills4.get(x));
                bills.addView(bill);
            }
            if (CongressionalActivity.party4.equals("R")) {
                RelativeLayout bg = (RelativeLayout) findViewById(R.id.bg4);
                bg.setBackground(new ColorDrawable(0x80ff0000));
            }
            String pic4 = "https://theunitedstates.io/images/congress/225x275/" + CongressionalActivity.bioguide4 + ".jpg";
            ImageView rep4Image = (ImageView) findViewById(R.id.rep4Image);
            Picasso.with(getBaseContext()).load(pic4).into(rep4Image);
            TextView rep4Name = (TextView) findViewById(R.id.rep4Name);
            rep4Name.setText(CongressionalActivity.senOrRep4 + " " + CongressionalActivity.name4);
            TextView rep4Party = (TextView) findViewById(R.id.rep4Party);
            rep4Party.setText(CongressionalActivity.party4);
            TextView rep4Email = (TextView) findViewById(R.id.rep4Email);
            rep4Email.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.email4 + "\">Email</a> "));
            rep4Email.setMovementMethod(LinkMovementMethod.getInstance());
            TextView rep4Website = (TextView) findViewById(R.id.rep4Website);
            rep4Website.setText(
                    Html.fromHtml(
                            "<a href=\"" + CongressionalActivity.website4 + "\">Website</a> "));
            rep4Website.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else {
            Toast.makeText(getBaseContext(), "Invalid Rep Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Obtains the LayoutInflater from the given context.
     */
    public static LayoutInflater from(Context context) {
        LayoutInflater LayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }
}
