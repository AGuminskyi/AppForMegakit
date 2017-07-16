package com.android.huminskiy1325.appformegakit.Model;

/**
 * Created by cubru on 15.07.2017.
 */

import java.util.List;

import com.android.huminskiy1325.appformegakit.Model.Car;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriversModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("cars")
    @Expose
    private List<Car> cars = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "\nDriversModel:" +
                "id= " + id +
                ", firstName= '" + firstName + '\'' +
                ", lastName= '" + lastName + '\'' +
                ", cars= " + cars;
    }
}