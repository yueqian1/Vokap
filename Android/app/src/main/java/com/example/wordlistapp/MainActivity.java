package com.example.wordlistapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

import com.example.wordlistapp.include.BarColorManager;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.NoteResources;
import com.example.wordlistapp.include.SharedPreferencesKit;
import com.example.wordlistapp.include.UserInformation;
import com.example.wordlistapp.include.WordResources;
import com.example.wordlistapp.notebook.NotebookEditActivity;
import com.example.wordlistapp.web.WebActivity;
import com.example.wordlistapp.wordtest.WordListingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteResources.init(this);

        initViews();
        initEvents();

        SharedPreferencesKit.init(this);

        int firstStart = SharedPreferencesKit.loadInt("firstStart", -1);
        Log.i("First Start", "" + firstStart);

        if (firstStart == -1) {
            try {
                AssetManager manager = getResources().getAssets();
                WordResources.init(manager);
                FontManager.init(manager);
                SharedPreferencesKit.writeInt("firstStart", 1);
                WordResources.saveData(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                WordResources.prepare(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UserInformation.init();
        setBarColor();
    }

    void initViews() {
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        fab = findViewById(R.id.fab);
    }

    void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, WordListingActivity.class);
                        intent.putExtra("wordListIndex", (int) (Math.random() * WordResources.getWordListCount()));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, NotebookEditActivity.class);
                        intent1.putExtra("notePosition", -1);
                        startActivity(intent1);
                        break;
                }
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));

                return true;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fab.setVisibility(View.VISIBLE);

                switch (position) {
                    case 0:
                        fab.setImageResource(R.drawable.ic_baseline_dehaze_24);
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.ic_baseline_edit_24);
                        break;
                    case 2:
                        fab.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
        try {
            WordResources.saveData(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}