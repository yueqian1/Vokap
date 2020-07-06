package com.example.wordlistapp.include;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontManager {

    private static Map<String, Typeface> fontMap = new HashMap<>();
    private static AssetManager fileManager;

    private static final String FONTPATH = "fonts/";
    public static final String PRODUCT_SANS_R = "ProductSans-Regular";
    public static final String PRODUCT_SANS_M = "ProductSans-Medium";
    public static final String PRODUCT_SANS_L = "ProductSans-Light";

    public static void init(AssetManager manager) {
        fileManager = manager;

        fontMap.put(PRODUCT_SANS_R, Typeface.createFromAsset(manager, FONTPATH + PRODUCT_SANS_R + ".ttf"));
        fontMap.put(PRODUCT_SANS_M, Typeface.createFromAsset(manager, FONTPATH + PRODUCT_SANS_M + ".ttf"));
        fontMap.put(PRODUCT_SANS_L, Typeface.createFromAsset(manager, FONTPATH + PRODUCT_SANS_L + ".ttf"));
    }

    public static Typeface getTypeface(String font) {
        return fontMap.get(font);
    }

}
