package com.android.huminskiy1325.appformegakit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cubru on 15.07.2017.
 */

public class MainFragment extends Fragment {
    public DriversAPI driversAPI;
    public List<DriversModel> modelList;

    Button getAllBtn;
    TextView informationTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        driversAPI = ConnectionData.getInstance(getActivity().getApplicationContext());
        modelList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getAllBtn = (Button) view.findViewById(R.id.get_all_btn);
        informationTV = (TextView)view.findViewById(R.id.informationTV);



        getAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driversAPI.getDriversList().enqueue(new Callback<List<DriversModel>>() {
                    @Override
                    public void onResponse(Call<List<DriversModel>> call, Response<List<DriversModel>> response) {
                        modelList.addAll(response.body());
                        informationTV.setText(modelList.toString());
                    }

                    @Override
                    public void onFailure(Call<List<DriversModel>> call, Throwable t) {
                        Log.d("NEW_TAG", t.getMessage());
                        Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}
