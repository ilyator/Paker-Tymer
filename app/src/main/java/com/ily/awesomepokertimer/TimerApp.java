package com.ily.awesomepokertimer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ily.awesomepokertimer.util.TournamentsUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ily on 21.10.2016.
 */

public class TimerApp extends Application {

    private static final String IS_FIRST_LAUNCH = "isFirstLaunch";

    @Override
    public void onCreate() {
        super.onCreate();
        //Realm init
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        initDefaultTournaments();
    }

    private void initDefaultTournaments() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)) {
            TournamentsUtil.createDefaultTournaments();
            sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply();
        }
    }

}
