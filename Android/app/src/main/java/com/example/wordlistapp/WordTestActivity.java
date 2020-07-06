package com.example.wordlistapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wordlistapp.include.BarColorManager;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.WordResources;
import com.example.wordlistapp.include.WordTestCase;

import java.util.LinkedList;
import java.util.List;

public class WordTestActivity extends Activity {

    private List<WordTestCase> testCases = new LinkedList<>();
    private ViewPager2 viewPager;
    private TextView textView;
    private ProgressBar progressBar;

    // 此处的wordList应该是一个引用
    private int wordListIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordtest);

        BarColorManager.setNavigationBarColor(getWindow(), Color.TRANSPARENT);

        wordListIndex = getIntent().getIntExtra("wordListIndex", 0);

        // 加载每个单词测试的case
        initTestCases();
        initViews();
        initEvents();
    }

    private void initEvents() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                WordTestCase testCase = testCases.get(position);

                textView.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_L));
                textView.setText(testCase.getTestedWord().getString());

                progressBar.setMax(testCases.size());
                progressBar.setProgress(position + 1);
            }
        });
    }

    private void initViews() {
        textView = findViewById(R.id.tvWordTestTitle);
        progressBar = findViewById(R.id.pbWordTest);

        viewPager = findViewById(R.id.vpWordTest);
        WordTestAdapter adapter = new WordTestAdapter(testCases);
        viewPager.setAdapter(adapter);
    }

    private void initTestCases() {
        int wordListSize = WordResources.getWordList(wordListIndex).size();

        for (int i = 0; i < wordListSize; i++) {
            testCases.add(new WordTestCase(wordListIndex, i));
        }
    }

}
