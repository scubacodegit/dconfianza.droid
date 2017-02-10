package com.scubacode.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htorres on 05/09/2016.
 */
public class ContactMessage
{
    @SerializedName("UserID")
    private int userID;

    @SerializedName("Email")
    private String email;

    @SerializedName("Message")
    private String message;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
