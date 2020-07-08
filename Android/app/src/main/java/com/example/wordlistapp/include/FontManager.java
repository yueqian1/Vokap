package com.example.wordlistapp.include;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;

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

    public static SpannableString getTitleSpannable(String title, Typeface tf) {
        SpannableString string = new SpannableString(title);
        string.setSpan(new TestTypefaceSpan(tf), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

}
