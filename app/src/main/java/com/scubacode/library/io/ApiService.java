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
    @GET("/dconfianza/api/registration/")
    Call<User> getUserByEmail(@Query("email") String email);

    @GET("/dconfianza/api/registration/{id}")
    Call<User> getUserByID(@Path("id") int id);

    @GET("/dconfianza/api/main/getlocations")
    Call<List<Location>> getLocations();

    @GET("/dconfianza/api/main/getactiveworkers")
    Call<List<Worker>> getActiveWorkers(@Query("locationID") int locationID, @Query("serviceID") int serviceID);

    @GET("/dconfianza/api/main/getservices")
    Call<List<Service>> getServices();

    @GET("/dconfianza/api/main/getservicesbylocation")
    Call<List<Service>> getServicesByLocation(@Query("locationID") int locationID);

    @GET("/dconfianza/api/main/getworker")
    Call<User> getWorkerByID(@Query("workerID") String workerID);

    @GET("/dconfianza/api/main/getworkerreviews")
    Call<List<WorkerReview>> getWorkerReviews(@Query("workerID") int workerID);

    @POST("/dconfianza/api/registration/new")
    Call<User> createUser(@Body User user);

    @POST("/dconfianza/api/registration/login")
    Call<User>login(@Body Credentials crdentials);

    @POST("/dconfianza/api/main/addreview")
    Call<Integer> addReview(@Body WorkerReview review);

    @POST("/dconfianza/api/main/addworker")
    Call<Integer> addWorker(@Query("userID") int userID,@Body Worker worker);

    @POST("/dconfianza/api/main/addmessage")
    Call<Integer> addMessage(@Body ContactMessage contactMessage);


}
