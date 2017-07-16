package com.android.huminskiy1325.appformegakit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cubru on 15.07.2017.
 */

public class MainFragment extends Fragment {
    public DriversAPI driversAPI;
    public static DriversModel modelDriver = null;

    Button getAllBtn;
    Button getOneBtn;
    Button deleteOneBtn;
    TextView informationTV;
    EditText getOneET;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        driversAPI = ConnectionData.getInstance(getActivity().getApplicationContext());


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getAllBtn = (Button) view.findViewById(R.id.get_all_btn);
        getOneBtn = (Button) view.findViewById(R.id.get_one_btn);
        deleteOneBtn = (Button) view.findViewById(R.id.delete_one_btn);
        informationTV = (TextView) view.findViewById(R.id.informationTV);
        getOneET = (EditText) view.findViewById(R.id.get_id_ET);


        getAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driversAPI.getDriversList().enqueue(new Callback<List<DriversModel>>() {
                    @Override
                    public void onResponse(Call<List<DriversModel>> call, Response<List<DriversModel>> response) {
                        if (response.isSuccessful()) {
                            List<DriversModel> modelList = response.body();
                            informationTV.setText(modelList.toString());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<DriversModel>> call, Throwable t) {
                        Log.d("NEW_TAG", t.getMessage());
                        Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        getOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driversAPI.getDriver(getOneET.getText().toString()).enqueue(new Callback<DriversModel>() {
                    @Override
                    public void onResponse(Call<DriversModel> call, Response<DriversModel> response) {
                        if (response.isSuccessful()) {
                            modelDriver = response.body();
                            informationTV.setText(modelDriver.toString());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<DriversModel> call, Throwable t) {
                        Log.d("NEW_TAG", t.getMessage());
                        Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carsDeleted();
                driversAPI.deleteDriver(getOneET.getText().toString().trim()).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            informationTV.setText("Deleted");
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("NEW_TAG", t.getMessage());
                        Toast.makeText(getActivity(), "An error occurred during networking",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    private void carsDeleted() {
        if (modelDriver == null) {
            Toast.makeText(getActivity(), "At first you should to know Drivers List",
                    Toast.LENGTH_SHORT).show();
        } else {
            List<Car> carList = modelDriver.getCars();
            for (Car car : carList) {
                driversAPI.deleteCar(car.getId()).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Toast.makeText(getActivity(), "Car's deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        }
    }
}
