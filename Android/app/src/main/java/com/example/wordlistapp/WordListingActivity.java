package com.example.wordlistapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.include.BarColorManager;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.Word;
import com.example.wordlistapp.include.WordResources;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class WordListingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private int wordListIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlisting);

        initWordList();
        initViews();
        initEvents();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        String title = WordResources.getWordList(wordListIndex).getListName();
        toolbar.setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        fab = findViewById(R.id.fabWordListing);
        toolbar = findViewById(R.id.tbWordListing);

        recyclerView = findViewById(R.id.rvWordlisting);

        LinearLayoutManager manager = new LinearLayoutManager(WordListingActivity.this);
        recyclerView.setLayoutManager(manager);

        WordListingAdapter adapter = new WordListingAdapter(wordListIndex);
        recyclerView.setAdapter(adapter);

        setBarColor();
    }

    private void setBarColor() {
        BarColorManager.setNavigationBarColor(getWindow(), Color.TRANSPARENT);
    }

    private void initWordList() {
        wordListIndex = getIntent().getIntExtra("wordListIndex", 0);
    }

    private void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordListingActivity.this, WordTestActivity.class);

                intent.putExtra("wordListIndex", wordListIndex);
                startActivity(intent);
            }
        });

    }

}
