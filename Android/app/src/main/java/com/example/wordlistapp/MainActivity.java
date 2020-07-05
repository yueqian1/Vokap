package com.example.wordlistapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.wordlistapp.ui.main.SectionsPagerAdapter;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<WordList> wordLists;
    private boolean firstStart = true;

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

        if (firstStart) {
            try {
                WordResources.init(getResources().getAssets());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            WordResources.prepare();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this, WordListingActivity.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}