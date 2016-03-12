package com.example.eric.represent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CircledImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Edited by Eric on 3/3/2016.
 * Originally from: http://stackoverflow.com/questions/24969086/is-there-an-easy-way-to-create-an-action-button-fragment
 */
public class ActionFragment extends Fragment implements View.OnClickListener {
//    public class ActionFragment extends Fragment {

    private static Listener mListener;
    private TextView vLabel1;
    private TextView vLabel2;

      public static ActionFragment create(String s1, String s2, Listener listener) {
          mListener = listener;
          ActionFragment fragment = new ActionFragment();;
          Bundle args = new Bundle();
          args.putString("LABEL1", s1);
          args.putString("LABEL2", s2);
          fragment.setArguments(args);
          return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vLabel1 = (TextView) view.findViewById(R.id.label1);
        vLabel2 = (TextView) view.findViewById(R.id.label2);
        vLabel1.setText(getArguments().getString("LABEL1"));
        vLabel2.setText(getArguments().getString("LABEL2"));
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { mListener.onActionPerformed();
    }

    public interface Listener {
        public void onActionPerformed();
    }
}