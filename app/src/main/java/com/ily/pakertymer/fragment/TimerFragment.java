package com.ily.pakertymer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ily.pakertymer.R;

import butterknife.ButterKnife;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}
