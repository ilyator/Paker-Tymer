package com.ily.pakertymer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ily.pakertymer.model.Level;
import com.ily.pakertymer.model.Tournament;
import com.ily.pakertymer.util.TournamentsUtil;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ily on 21.10.2016.
 */

public class TimerApp extends Application {

    public static AtomicInteger tournamentNextId, levelNextId;

    private static final String IS_FIRST_LAUNCH = "isFirstLaunch";

    @Override
    public void onCreate() {
        super.onCreate();
        //Realm init
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        initDefaultTournaments();
        tournamentNextId = new AtomicInteger(Realm.getDefaultInstance().where(Tournament.class).max("index").intValue());
        levelNextId = new AtomicInteger(Realm.getDefaultInstance().where(Level.class).max("id").intValue());
    }

    private void initDefaultTournaments() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)) {
            TournamentsUtil.createDefaultTournaments();
            sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply();
        }
    }

}
