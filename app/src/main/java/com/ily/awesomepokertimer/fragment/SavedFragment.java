package com.ily.awesomepokertimer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ily on 20.10.2016.
 */

public class SavedFragment extends Fragment {

    public static SavedFragment newInstance() {
        Bundle args = new Bundle();
        SavedFragment fragment = new SavedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    

}
