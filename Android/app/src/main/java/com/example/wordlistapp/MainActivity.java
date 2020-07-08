package com.example.wordlistapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;

import com.example.wordlistapp.include.BarColorManager;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean firstStart = true;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        preferences = getSharedPreferences("AppData", MODE_PRIVATE);

        if (firstStart) {
            try {
                AssetManager manager = getResources().getAssets();
                WordResources.init(manager);
                FontManager.init(manager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                WordResources.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setBarColor();

        WordResources.wordListsToString();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WordListingActivity.class);
                intent.putExtra("wordListIndex", (int) (Math.random() * WordResources.getWordListCount()));
                startActivity(intent);
            }
        });
    }

    private void setBarColor() {
        //设置导航栏为透明色
        BarColorManager.setNavigationBarColor(getWindow(), Color.TRANSPARENT);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}