package com.scubacode.library.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by htorres on 12/07/2016.
 */
public class ApiAdapter
{
    private static ApiService API_SERVICE;

    public static ApiService getApiService () {

        if(API_SERVICE == null){

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(ApiConstants.BASE_URL)
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);

        }

        return API_SERVICE;

    }
}
