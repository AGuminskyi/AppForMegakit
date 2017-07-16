package com.android.huminskiy1325.appformegakit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by cubru on 15.07.2017.
 */

public interface DriversAPI {

    @GET("drivers/")
    Call<List<DriversModel>> getDriversList();

    @GET("drivers/{id}")
    Call<DriversModel> getDriver(@Path("id") String id);

    @DELETE("drivers/{id}")
    Call<Object> deleteDriver(@Path("id") String id);

    @DELETE("cars/{id}")
    Call<Object> deleteCar(@Path("id") int id);


    @POST("drivers/")
    Call<Object> createDriver(@Body DriversModel driversModel);
}
