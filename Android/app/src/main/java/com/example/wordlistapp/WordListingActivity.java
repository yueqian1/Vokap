package com.example.wordlistapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.include.Word;
import com.example.wordlistapp.include.WordResources;
import com.example.wordlistapp.ui.main.WordListingAdapter;

import java.io.IOException;
import java.util.List;

public class WordListingActivity extends Activity {

    private RecyclerView recyclerView;
    private List<Word> wordList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlisting);

        initWordList();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rvWordlisting);

        LinearLayoutManager manager = new LinearLayoutManager(WordListingActivity.this);
        recyclerView.setLayoutManager(manager);

        WordListingAdapter adapter = new WordListingAdapter(WordListingActivity.this, wordList);
        recyclerView.setAdapter(adapter);
    }

    private void initWordList() {
        int wordListIndex = getIntent().getIntExtra("wordListIndex", 0);
        wordList = WordResources.getWordList(wordListIndex).getWordList();
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public List<Word> getWordList() {
        return wordList;
    }
}
