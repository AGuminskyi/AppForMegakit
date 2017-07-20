package com.android.huminskiy1325.appformegakit;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by cubru on 20.07.2017.
 */

public final class DialogFactory {
    private static String ALERT_MESSAGE = "An error occurred during networking";

//    public DialogFactory(Activity activity) {
//        DialogFactory.activity = activity;
//    }

    public static void createMessage(Activity activity, String message){
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
    public static void showAlertMessage(Activity activity){
        Snackbar.make(activity.findViewById(android.R.id.content), ALERT_MESSAGE, Snackbar.LENGTH_LONG).show();
    }
}
