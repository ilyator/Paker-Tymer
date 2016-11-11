package com.ily.awesomepokertimer.util;

import android.content.Context;

import com.ily.awesomepokertimer.R;
import com.ily.awesomepokertimer.model.Level;
import com.ily.awesomepokertimer.model.Tournament;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ily on 21.10.2016.
 */

public class TournamentsUtil {

    public static void createDefaultTournaments() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Tournament tournament = new Tournament();
            Level level = new Level();
            level.setSmallBlind(10);
            level.setBigBlind(20);
            level.setDuration(1000 * 60 * 60);
            tournament.setIndex(i);
            tournament.setLevels(new RealmList<>(level));
            tournament.setName("Default" + String.valueOf(i));
            realm.copyToRealm(tournament);
        }
        realm.commitTransaction();
    }

    public static String getBlindsInfo(Context context, Tournament tournament) {
        RealmList<Level> levels = tournament.getLevels();
        int startBlind = levels.get(0).getBigBlind();
        int endBlind = levels.get(levels.size()-1).getBigBlind();
        return String.format(context.getString(R.string.blinds_d_d), startBlind, endBlind);
    }

    public static String getLevelsInfo(Context context, Tournament tournament) {
        RealmList<Level> levels = tournament.getLevels();
        return String.format(context.getString(R.string.d_levels_d_minutes), levels.size(), tournament.getLevels().get(0).getDuration()/1000/60);
    }

}
