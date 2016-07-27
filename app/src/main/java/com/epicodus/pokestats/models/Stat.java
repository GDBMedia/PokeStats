package com.epicodus.pokestats.models;

/**
 * Created by Guest on 7/26/16.
 */
public class Stat {


    public String statname;



    public String stat;

    public Stat(String statname, String stat) {
        this.statname = statname;
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }

    public String getStatname() {
        return statname;
    }
}
