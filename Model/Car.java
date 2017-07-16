package com.android.huminskiy1325.appformegakit.Model;

/**
 * Created by cubru on 15.07.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// Model of Car. Mapped by JSON- file
public class Car {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("mark")
    @Expose
    private String mark;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("manufactureYear")
    @Expose
    private int manufactureYear;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    @Override
    public String toString() {
        return "\nid= " + id +
                ", mark= '" + mark + '\'' +
                ", model= '" + model + '\'' +
                ", manufactureYear=" + manufactureYear;
    }
}
