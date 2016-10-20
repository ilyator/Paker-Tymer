package com.ily.awesomepokertimer.model;

import io.realm.RealmObject;

/**
 * Created by ily on 20.10.2016.
 */

public class Level extends RealmObject {

    private int id;
    private long duration;
    private int sBlind;
    private int bBlind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getsBlind() {
        return sBlind;
    }

    public void setsBlind(int sBlind) {
        this.sBlind = sBlind;
    }

    public int getbBlind() {
        return bBlind;
    }

    public void setbBlind(int bBlind) {
        this.bBlind = bBlind;
    }
}
