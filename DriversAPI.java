package com.android.huminskiy1325.appformegakit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by cubru on 15.07.2017.
 */

public interface DriversAPI {

    @GET("drivers/")
    Call<List<DriversModel>> getDriversList();

}
