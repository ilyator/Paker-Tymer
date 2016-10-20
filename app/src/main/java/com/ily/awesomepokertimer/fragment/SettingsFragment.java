package com.ily.awesomepokertimer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ily on 20.10.2016.
 */

public class SettingsFragment extends Fragment {
    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
