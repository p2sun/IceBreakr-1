package com.lloydtorres.icebreakr;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class User {

    private String twitterID;
    private String estimoteHash;
    private String name;
    private String interests;
    private String description;
    private Beacon beacon;

    public User (String twID, String estH, String nom, String inter, String desc, Beacon b) {
        twitterID = twID;
        estimoteHash = estH;
        name = nom;
        interests = inter;
        description = desc;
        beacon = b;
    }

    public String getTwitterID() {
        return twitterID;
    }

    public String getEstimoteHash() {
        return estimoteHash;
    }

    public String getName() {
        return name;
    }

    public String getInterests() {
        return interests;
    }

    public String getDescription() {
        return description;
    }

    public double getDistance() { return Utils.computeAccuracy(beacon); }

    public Beacon getBeacon() { return beacon; }

}
