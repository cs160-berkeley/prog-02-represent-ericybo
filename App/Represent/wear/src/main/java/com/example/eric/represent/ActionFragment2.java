package com.example.eric.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Edited by Eric on 3/9/2016.
 * Originally from: http://stackoverflow.com/questions/28419057/how-to-change-to-another-activity-when-pressing-on-a-gridviewpager
 */
public class ActionFragment2 extends CardFragment {

    private View fragmentView;
    private View.OnClickListener listener;
    private TextView vLabel1;
    private TextView vLabel2;
    private String num;

    public static ActionFragment2 create(String s1, String s2, String num) {
        ActionFragment2 fragment = new ActionFragment2();;
        Bundle args = new Bundle();
        args.putString("LABEL1", s1);
        args.putString("LABEL2", s2);
        args.putString("INDEX", num);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        fragmentView = super.onCreateContentView(inflater, container, savedInstanceState);
        fragmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                if (listener != null) {
//                    listener.onClick(view);
//                }
                Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("repnum", num);
                getActivity().startService(sendIntent);
            }
        });

//        vLabel1 = (TextView) view.findViewById(R.id.label1);
//        vLabel2 = (TextView) view.findViewById(R.id.label2);

        return fragmentView;
    }

    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;
    }
}
