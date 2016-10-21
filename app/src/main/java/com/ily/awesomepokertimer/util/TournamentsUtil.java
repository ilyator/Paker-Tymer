package com.ily.awesomepokertimer.util;

import android.content.Context;

import com.ily.awesomepokertimer.model.Level;
import com.ily.awesomepokertimer.model.Tournament;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ily on 21.10.2016.
 */

public class TournamentsUtil {

    public static void createDefaultTournaments(Context context){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Tournament tournament = new Tournament();
        Level level = new Level();
        level.setSmallBlind(10);
        level.setBigBlind(20);
        level.setDuration(1000 * 60 * 60); //60mins
        tournament.setId(0);
        tournament.setLevels(new RealmList<>(level));
        tournament.setName("Default");
        realm.copyToRealmOrUpdate(tournament);
        Tournament tournament1 = new Tournament();
        Level level1 = new Level();
        level1.setSmallBlind(10);
        level1.setBigBlind(20);
        level1.setDuration(1000 * 60 * 60); //60mins
        tournament1.setId(1);
        tournament1.setLevels(new RealmList<>(level1));
        tournament1.setName("Default");
        realm.copyToRealmOrUpdate(tournament1);
        realm.commitTransaction();
    }

}
