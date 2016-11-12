package com.ily.pakertymer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ily.pakertymer.R;
import com.ily.pakertymer.model.Tournament;
import com.ily.pakertymer.view.CircleTimerView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by ily on 20.10.2016.
 */

public class TimerFragment extends Fragment {

    @BindView(R.id.iv_timer)
    CircleTimerView ivTimer;

    private Realm realm;

    public static TimerFragment newInstance() {
        Bundle args = new Bundle();
        TimerFragment fragment = new TimerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        Tournament tournament = realm.where(Tournament.class).equalTo("isActive", true).findFirst();

        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        ButterKnife.bind(this, root);
        if(tournament!=null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, (int)(tournament.getCurrentLevelTime()/(1000*60)));
            ivTimer.setLevelFullTime(tournament.getCurrentLevel().getDuration());
            ivTimer.setLevelCurrentTime(tournament.getCurrentLevelTime());
            ivTimer.startAnimation();
        }

        return root;
    }
}
