package com.ily.awesomepokertimer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ily on 20.10.2016.
 */

public class TimerFragment extends Fragment {

    public static TimerFragment newInstance() {
        Bundle args = new Bundle();
        TimerFragment fragment = new TimerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
