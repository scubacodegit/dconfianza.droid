package com.scubacode.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htorres on 25/07/2016.
 */
public class Location {

    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;

    public int getID() {
        return id;
    }
    public void setID(int userID) {
        this.id = userID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
