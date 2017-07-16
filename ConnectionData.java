package com.android.huminskiy1325.appformegakit;

import android.content.Context;

import java.net.URL;

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



//    private Context mAppContext;
//    private static ConnectionData connectionData;
//    private String URL = "localhost:8099/";
//
//    private static DriversAPI driversAPI;
//    private Retrofit retrofit;
//
//    private ConnectionData(Context appContext) {
//        mAppContext = appContext;
//    }
//
//    public static ConnectionData get(Context context) {
//        if (connectionData != null) {
//            connectionData = new ConnectionData(context.getApplicationContext());
//            connectionData.createConntection();
//        }
//        return connectionData;
//    }
//
//    private void createConntection() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        driversAPI = retrofit.create(DriversAPI.class);
//    }
//
//    public DriversAPI getApi() {
//        if (connectionData == null) {
//            get(mAppContext);
//        }
//        return driversAPI;
//    }
}
