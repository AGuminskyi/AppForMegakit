package com.android.huminskiy1325.appformegakit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.huminskiy1325.appformegakit.Model.Car;
import com.android.huminskiy1325.appformegakit.Model.DriversAPI;
import com.android.huminskiy1325.appformegakit.Model.DriversModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by cubru on 15.07.2017.
 */

public class MainFragment extends ListFragment {
    public DriversAPI driversAPI;
    public static DriversModel modelDriver = null;
    public static ListView listView;
    private List<DriversModel> modelList;


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

        modelList = new ArrayList<>();

        ListAdapter listAdapter = new ListAdapter(getActivity(), modelList);
        setListAdapter(listAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        //initialize View's
        getAllBtn = (Button) view.findViewById(R.id.get_all_btn);
        getOneBtn = (Button) view.findViewById(R.id.get_one_btn);
        deleteOneBtn = (Button) view.findViewById(R.id.delete_one_btn);
        postBtn = (Button) view.findViewById(R.id.post_btn);

        informationTV = (TextView) view.findViewById(R.id.informationTV);

        getOneET = (EditText) view.findViewById(R.id.get_id_ET);
        firstNameET = (EditText) view.findViewById(R.id.first_name_ET);
        lastNameET = (EditText) view.findViewById(R.id.last_name_ET);

        listView = (ListView) view.findViewById(android.R.id.list);
//        listView.setAdapter(listAdapter);


        //button that will represent all drivers(include their cars) on TextView
        getAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDriversList();
            }
        });

        //button that represent all drivers(include their cars) on TextView
        getOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getOneET.getText().toString().trim();
                if (!TextUtils.isEmpty(id)) {
                    //method that processes data
                    getOneById(id);
                } else {
                    DialogFactory.createMessage(getActivity(),"Input drivers id");
                }
            }
        });
        //button that delete driver(include cars) by ID
        deleteOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getOneET.getText().toString().trim();
                if (!TextUtils.isEmpty(id)) {
                    //methods that processes data
                    carsDeleted();
                    deleteById(id);
                } else {
                    DialogFactory.createMessage(getActivity(),"Input drivers id");
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
                    //method that processes data
                    sendPost(driversModel);
                } else {
                    DialogFactory.createMessage(getActivity(),"Input your first and last name");
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
                    modelList = new ArrayList<DriversModel>(response.body());
//                    modelList.addAll(response.body());
                    setListAdapter(new ListAdapter(getActivity(), modelList));
//                    listAdapter.notifyDataSetChanged();
                    DialogFactory.createMessage(getActivity(), "Data loaded");
                } else {
                    DialogFactory.createMessage(getActivity(),"Error happend");
                }
            }

            @Override
            public void onFailure(Call<List<DriversModel>> call, Throwable t) {
                Log.d("NEW_TAG", t.getMessage());
                DialogFactory.showAlertMessage(getActivity());
            }
        });
    }

    public void getOneById(String id) {
        driversAPI.getDriver(id).enqueue(new Callback<DriversModel>() {
            @Override
            public void onResponse(Call<DriversModel> call, Response<DriversModel> response) {
                if (response.isSuccessful()) {
                    modelList = new ArrayList<DriversModel>();
                    modelDriver = response.body();
                    modelList.add(modelDriver);
                    setListAdapter(new ListAdapter(getActivity(), modelList));
                    DialogFactory.createMessage(getActivity(), "Data loaded");
                } else {
                    DialogFactory.createMessage(getActivity(),"Error happend");
                }
            }

            @Override
            public void onFailure(Call<DriversModel> call, Throwable t) {
                DialogFactory.showAlertMessage(getActivity());
            }
        });
    }

    public void deleteById(final String id) {
        driversAPI.deleteDriver(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    DialogFactory.createMessage(getActivity(),"Deleted");
                    Integer integer = Integer.valueOf(id);
                    for (DriversModel driversModel : modelList) {
                        if (driversModel.getId() == integer) {
                            modelList.remove(driversModel);
                        }
                    }
                    setListAdapter(new ListAdapter(getActivity(), modelList));
                } else {
                    DialogFactory.createMessage(getActivity(),"Error happend");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("NEW_TAG", t.getMessage());
                DialogFactory.showAlertMessage(getActivity());
            }
        });
    }

    public void sendPost(final DriversModel driversModel) {
        driversAPI.createDriver(driversModel).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    modelList.add(driversModel);
                    DialogFactory.createMessage(getActivity(),"Created");
                } else {
                    DialogFactory.createMessage(getActivity(),"Error happend");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                DialogFactory.showAlertMessage(getActivity());
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
                        DialogFactory.createMessage(getActivity(),"Car's deleted");
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        DialogFactory.showAlertMessage(getActivity());
                    }
                });
            }
        }
    }
}
