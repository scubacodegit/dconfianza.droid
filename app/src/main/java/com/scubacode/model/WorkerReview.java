package com.scubacode.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by htorres on 29/07/2016.
 */
public class WorkerReview {
    @SerializedName("ID")
    private int id;
    @SerializedName("WorkerID")
    private int workerID;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Review")
    private String review;
    @SerializedName("Rating")
    private double rating;
    @SerializedName("CreatedDate")
    private Date createdDate;
    @SerializedName("CreatedByID")
    private int createdByID;
    @SerializedName("CreatedByFirstName")
    private String createdByFirstName;
    @SerializedName("CreatedByLastName")
    private String createdByLastName;

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public int getWorkerID() {
        return workerID;
    }
    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReview() {return review;}
    public void setRerview(String review) {
        this.review = review;
    }

    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}

    public Date getCreatedDate() {return createdDate;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}

    public int getCreatedByID() {return createdByID;}
    public void setCreatedByID(int createdByID) {this.createdByID = createdByID;}

    public String getCreatedByFirstName() {return createdByFirstName;}
    public void setCreatedByFirstName(String createdByFirstName) {this.createdByFirstName = createdByFirstName;}

    public String getCreatedByLastName() {return createdByLastName;}
    public void setCreatedByLastName(String createdByLastName) {this.createdByLastName = createdByLastName;}

}
