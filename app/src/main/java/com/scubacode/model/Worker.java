package com.scubacode.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by htorres on 26/07/2016.
 */
public class Worker
{
    @SerializedName("ID")
    private int ID;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("MobilPhone")
    private String mobilPhone;
    @SerializedName("WorkPhone")
    private String workPhone;
    @SerializedName("Email")
    private String email;
    @SerializedName("LocationID")
    private int locationID;
    @SerializedName("LocationName")
    private String locationName;
    @SerializedName("Rating")
    private double rating;
    @SerializedName("Resume")
    private String resume;
    @SerializedName("Active")
    private Boolean active;
    @SerializedName("CreatedDate")
    private Date createdDate;
    @SerializedName("CreatedByID")
    private int createdByID;
    @SerializedName("CreatedByFirstName")
    private String createdByFirstName;
    @SerializedName("CreatedByLastName")
    private String createdByLastName;
    @SerializedName("ServiceID")
    private int serviceID;
    @SerializedName("ServiceName")
    private String serviceName;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilPhone() {
        return mobilPhone;
    }
    public void setMobilePhone(String mobilPhone) {
        this.mobilPhone = mobilPhone;
    }

    public String getWorkPhone() {return workPhone;}
    public void setWorkPhone(String workPhone) {this.workPhone = workPhone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public int getLocationID() {return locationID;}
    public void setLocationID(int locationID) {this.locationID = locationID;}

    public String getLocationName() {return locationName;}
    public void setLocationName(String locationName) {this.locationName = locationName;}

    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}

    public String getResume() {return resume;}
    public void setResume(String resume) {this.resume = resume;}

    public Boolean getActive() {return active;}
    public void setActive(Boolean active) {this.active = active;}

    public Date getCreatedDate() {return createdDate;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}

    public int getCreatedByID() {return createdByID;}
    public void setCreatedByID(int createdByID) {this.createdByID = createdByID;}

    public String getCreatedByFirstName() {return createdByFirstName;}
    public void setCreatedByFirstName(String createdByFirstName) {this.createdByFirstName = createdByFirstName;}

    public String getCreatedByLastName() {return createdByLastName;}
    public void setCreatedByLastName(String createdByLastName) {this.createdByLastName = createdByLastName;}

    public int getServiceID() {return serviceID;}
    public void setServiceID(int serviceID) {this.serviceID = serviceID;}

    public String getServiceName() {return serviceName;}
    public void setServiceName(String serviceName) {this.serviceName = serviceName;}

}
