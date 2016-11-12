package com.ily.pakertymer.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ily.pakertymer.R;
import com.ily.pakertymer.fragment.SavedFragment;
import com.ily.pakertymer.fragment.SettingsFragment;
import com.ily.pakertymer.fragment.TimerFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.contentContainer)
    FrameLayout container;

    private Fragment fragment;
    private int currentTabId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(currentTabId != tabId) {
            currentTabId = tabId;
            switch (tabId) {
                case R.id.tab_saved:
                    fragment = SavedFragment.newInstance();
                    break;
                case R.id.tab_timer:
                    fragment = TimerFragment.newInstance();
                    break;
                case R.id.tab_settings:
                    fragment = SettingsFragment.newInstance();
                    break;
            }
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contentContainer, fragment, fragment.getClass().getName())
                    .commit();
        }
    }

}