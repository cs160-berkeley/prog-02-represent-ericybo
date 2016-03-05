package com.example.eric.represent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            tv1.setText("Term ends on: " + "3/2/2018");
            for(int x=1; x<4; x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText("Committee " + x);
                comms.addView(comm);
            }
            for(int x=1; x<4; x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText("Bill " + x);
                bills.addView(bill);
            }
        }
        else if (repnum == 2) {
            View child = getLayoutInflater().inflate(R.layout.rep2, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep2Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + "7/12/2018");
            for(int x=1; x<4; x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText("Committee " + x);
                comms.addView(comm);
            }
            for(int x=1; x<4; x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText("Bill " + x);
                bills.addView(bill);
            }
        }
        else if (repnum == 3) {
            View child = getLayoutInflater().inflate(R.layout.rep3, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep3Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + "10/11/2018");
            for(int x=1; x<4; x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText("Committee " + x);
                comms.addView(comm);
            }
            for(int x=1; x<4; x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText("Bill " + x);
                bills.addView(bill);
            }
        }
        else if (repnum == 4) {
            View child = getLayoutInflater().inflate(R.layout.rep4, null);
            child.setClickable(false);
            ImageButton ib = (ImageButton)child.findViewById(R.id.rep4Go);
            ib.setVisibility(View.INVISIBLE);
            item.addView(child);
            TextView tv1 = (TextView)findViewById(R.id.termEndText);
            tv1.setText("Term ends on: " + "3/2/2018");
            for(int x=1; x<4; x++) {
                TextView comm = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                comm.setText("Committee " + x);
                comms.addView(comm);
            }
            for(int x=1; x<4; x++) {
                TextView bill = (TextView)getLayoutInflater().inflate(R.layout.comorbill, null);
                bill.setText("Bill " + x);
                bills.addView(bill);
            }
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
