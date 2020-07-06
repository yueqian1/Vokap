package com.example.wordlistapp.include;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;

import androidx.annotation.RequiresApi;

public class BarColorManager {

    public static void setStatusBarColor(Window window, int color) {
        window.setStatusBarColor(color);
    }

    public static void setNavigationBarColor(Window window, int color) {
        window.setNavigationBarColor(color);
    }

}
