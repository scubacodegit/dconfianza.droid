package com.scubacode.library.io;

import com.scubacode.model.ContactMessage;
import com.scubacode.model.User;
import com.scubacode.model.Location;
import com.scubacode.model.Service;
import com.scubacode.model.Credentials;
import com.scubacode.model.Worker;
import com.scubacode.model.WorkerReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by htorres on 12/07/2016.
 */
public interface  ApiService
{
    //registration
    @GET("/Recomendado/api/registration/")
    Call<User> getUserByEmail(@Query("email") String email);

    @GET("/Recomendado/api/registration/{id}")
    Call<User> getUserByID(@Path("id") int id);

    @GET("/Recomendado/api/main/getlocations")
    Call<List<Location>> getLocations();

    @GET("/Recomendado/api/main/getactiveworkers")
    Call<List<Worker>> getActiveWorkers(@Query("locationID") int locationID, @Query("serviceID") int serviceID);

    @GET("/Recomendado/api/main/getservices")
    Call<List<Service>> getServices();

    @GET("/Recomendado/api/main/getservicesbylocation")
    Call<List<Service>> getServicesByLocation(@Query("locationID") int locationID);

    @GET("/Recomendado/api/main/getworker")
    Call<User> getWorkerByID(@Query("workerID") String workerID);

    @GET("/Recomendado/api/main/getworkerreviews")
    Call<List<WorkerReview>> getWorkerReviews(@Query("workerID") int workerID);

    @POST("/Recomendado/api/registration/new")
    Call<User> createUser(@Body User user);

    @POST("/Recomendado/api/registration/login")
    Call<User>login(@Body Credentials crdentials);

    @POST("/Recomendado/api/main/addreview")
    Call<Integer> addReview(@Body WorkerReview review);

    @POST("/Recomendado/api/main/addworker")
    Call<Integer> addWorker(@Query("userID") int userID,@Body Worker worker);

    @POST("/Recomendado/api/main/addmessage")
    Call<Integer> addMessage(@Body ContactMessage contactMessage);


}
