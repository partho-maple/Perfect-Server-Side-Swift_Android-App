package com.example.parthobiswas.familytree.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by parthobiswas on 3/30/17.
 */

public class ConnectivityUtils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
