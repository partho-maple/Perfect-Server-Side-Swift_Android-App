package com.example.parthobiswas.familytree.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by parthobiswas on 3/30/17.
 */

public class ViewUtils {

    public static float convertPixelsToDp(float px, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

}
