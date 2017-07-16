package com.android.huminskiy1325.appformegakit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.huminskiy1325.appformegakit.Model.Car;
import com.android.huminskiy1325.appformegakit.Model.DriversAPI;
import com.android.huminskiy1325.appformegakit.Model.DriversModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cubru on 15.07.2017.
 */

public class MainFragment extends Fragment {
    public DriversAPI driversAPI;
    public static DriversModel modelDriver = null;

    View view;
    Button getAllBtn;
    Button getOneBtn;
    Button deleteOneBtn;
    Button postBtn;
    TextView informationTV;
    EditText getOneET;
    EditText firstNameET;
    EditText lastNameET;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        driversAPI = ConnectionData.getInstance(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        getAllBtn = (Button) view.findViewById(R.id.get_all_btn);
        getOneBtn = (Button) view.findViewById(R.id.get_one_btn);
        deleteOneBtn = (Button) view.findViewById(R.id.delete_one_btn);
        postBtn = (Button) view.findViewById(R.id.post_btn);

        informationTV = (TextView) view.findViewById(R.id.informationTV);

        getOneET = (EditText) view.findViewById(R.id.get_id_ET);
        firstNameET = (EditText) view.findViewById(R.id.first_name_ET);
        lastNameET = (EditText) view.findViewById(R.id.last_name_ET);

        getAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDriversList();
            }
        });

        getOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getOneET.getText().toString().trim();
                if (!TextUtils.isEmpty(id)) {
                    getOneById(id);
                } else {
                    Toast.makeText(getActivity(), "Input drivers id",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getOneET.getText().toString().trim();
                if (!TextUtils.isEmpty(id)) {
                    carsDeleted();
                    deleteById(id);
                } else {
                    Toast.makeText(getActivity(), "Input drivers id",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString().trim();
                String lastName = lastNameET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    DriversModel driversModel = new DriversModel();
                    driversModel.setFirstName(firstName);
                    driversModel.setLastName(lastName);
                    driversModel.setCars(null);
                    sendPost(driversModel);
                } else {
                    Toast.makeText(getActivity(), "Input your first and last name",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void getDriversList() {
        driversAPI.getDriversList().enqueue(new Callback<List<DriversModel>>() {
            @Override
            public void onResponse(Call<List<DriversModel>> call, Response<List<DriversModel>> response) {
                if (response.isSuccessful()) {
                    List<DriversModel> modelList = response.body();
                    informationTV.setText(modelList.toString());
                } else {
                    informationTV.setText("Error happend");
                }
            }

            @Override
            public void onFailure(Call<List<DriversModel>> call, Throwable t) {
                Log.d("NEW_TAG", t.getMessage());
                Toast.makeText(getActivity(), "An error occurred during networking",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getOneById(String id) {
        driversAPI.getDriver(id).enqueue(new Callback<DriversModel>() {
            @Override
            public void onResponse(Call<DriversModel> call, Response<DriversModel> response) {
                if (response.isSuccessful()) {
                    modelDriver = response.body();
                    informationTV.setText(modelDriver.toString());
                } else {
                    informationTV.setText("Error happend");
                }
            }

            @Override
            public void onFailure(Call<DriversModel> call, Throwable t) {
                Log.d("NEW_TAG", t.getMessage());
                Toast.makeText(getActivity(), "An error occurred during networking",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteById(String id) {
        driversAPI.deleteDriver(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    informationTV.setText("Deleted");
                } else {
                    informationTV.setText("Error happend");
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

    public void sendPost(DriversModel driversModel) {
        driversAPI.createDriver(driversModel).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    informationTV.setText("Created");
                } else {
                    informationTV.setText("Error happend");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void carsDeleted() {
        if (modelDriver == null) {
            Toast.makeText(getActivity(), "At first you should to know Drivers List of one person",
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
