package com.middleand.mobile.rccar.framents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.middleand.mobile.rccar.R;

/**
 * A placeholder fragment containing BT Console Application view.
 */
public class BTConsoleFragment extends Fragment {

    public BTConsoleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_btconsole, container,
                false);
        return rootView;
    }
}
