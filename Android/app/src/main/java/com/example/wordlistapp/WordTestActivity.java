package com.example.wordlistapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wordlistapp.include.BarColorManager;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.Word;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;
import com.example.wordlistapp.include.WordTestCase;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WordTestActivity extends Activity {

    private List<WordTestCase> testCases = new LinkedList<>();
    private WordList wordList;
    private ViewPager2 viewPager;
    private TextView tvTitle;
    private ProgressBar progressBar;
    private TextView tvTestResult;
    private Button btnNextTest;
    private Queue<String> titleQueue = new LinkedList<>();
    private boolean firstTitle = true;

    // 此处的wordList应该是一个引用
    private int wordListIndex;

    public void updateViews() {
        updateProgress();

        if (wordList.isFinished()) {
            setFinished();
        }
    }

    public void setOverlearned() {
        tvTitle.setText("Why not have a rest?");
    }

    public void setFinished() {
        tvTitle.setText("You have finished all words!");
        btnNextTest.setVisibility(View.INVISIBLE);
    }

    public void displayResult(String result) {
        tvTestResult.setText(result);
    }

    public void addTitle(Word word) {
        if (firstTitle) {
            tvTitle.setText(word.getString());
            firstTitle = false;
            return;
        }

        titleQueue.add(word.getString());
    }

    public void updateProgress() {
        progressBar.setProgress(wordList.getLearnedProgress());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordtest);

        BarColorManager.setNavigationBarColor(getWindow(), Color.TRANSPARENT);

        wordListIndex = getIntent().getIntExtra("wordListIndex", 0);
        wordList = WordResources.getWordList(wordListIndex);

        initTestCases();
        initViews();
        initEvents();

        updateProgress();
    }

    private void initTestCases() {
        int count = wordList.size();

        for (int i = 0; i < count; i++) {
            if (!wordList.getWordLearnedStatus(i)) {
                testCases.add(new WordTestCase(wordListIndex, i));
            }
        }
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvTestTitle);
        tvTitle.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));

        progressBar = findViewById(R.id.pbWordTest);
        progressBar.setMax(wordList.size());

        viewPager = findViewById(R.id.vpWordTest);
        WordTestAdapter adapter = new WordTestAdapter(this, testCases, wordListIndex);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(1);

        tvTestResult = findViewById(R.id.tvTestResult);
        tvTestResult.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_L));

        btnNextTest = findViewById(R.id.btnNextTest);
        btnNextTest.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_M));
    }

    private void initEvents() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        btnNextTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();

                if (currentPage == WordTestAdapter.MAX_TESTPAGES - 1) {
                    setOverlearned();
                    return;
                }

                viewPager.setCurrentItem(currentPage + 1);

                String nextTitle = titleQueue.poll();
                tvTitle.setText(nextTitle);
                tvTestResult.setText("");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
