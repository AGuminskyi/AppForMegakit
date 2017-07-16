package com.android.huminskiy1325.appformegakit;

import android.content.Context;

import com.android.huminskiy1325.appformegakit.Model.DriversAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cubru on 15.07.2017.
 */

public class ConnectionData {

    private static DriversAPI driversAPI;
    private static String BASE_URL = "http://192.168.1.105:8099/";

    public static DriversAPI getInstance(Context context){
        if(driversAPI == null){
            Retrofit  retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            driversAPI = retrofit.create(DriversAPI.class);
        }
        return driversAPI;
    }
}
