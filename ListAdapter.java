package com.android.huminskiy1325.appformegakit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.huminskiy1325.appformegakit.Model.Car;
import com.android.huminskiy1325.appformegakit.Model.DriversModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cubru on 20.07.2017.
 */

class ListAdapter extends ArrayAdapter<DriversModel> {

    Activity activity = null;

    public ListAdapter(Activity activity, List<DriversModel> driversModelList) {
        super(activity, 0, driversModelList);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item, null);
        }
        DriversModel driversModel = getItem(position);

        TextView driverTextView = (TextView) convertView.findViewById(R.id.driver_list_name_tv);
        setNameInTV(driversModel, driverTextView);
        TextView carsTextView = (TextView) convertView.findViewById(R.id.cars_list_tv);
        setCars(driversModel, carsTextView);
        return convertView;
//        return super.getView(position, convertView, parent);
    }

    private void setNameInTV(DriversModel driversModel, TextView textView) {
        String name = "(ID =  " + driversModel.getId() + ")" + " " + driversModel.getFirstName() +
                " " + driversModel.getLastName();
        textView.setText(name);
    }

    private void setCars(DriversModel driversModel, TextView textView) {
        String cars = "";
        if (driversModel.getCars() != null) {
            for (Car car : driversModel.getCars()) {
                cars = cars + car.getMark() + " - " + car.getModel()
                        + "(" + car.getManufactureYear() + ")" + '\n';
            }
        }
        else{
            cars = "No cars";
        }
        textView.setText(cars);
    }
}
