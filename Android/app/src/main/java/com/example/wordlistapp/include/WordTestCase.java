package com.example.wordlistapp.include;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordTestCase {

    private List<Word> words = new ArrayList<>();
    private Word testedWord;
    private int testedWordIndexInTheCase;
    private int testCaseIndex;
    private int testStatus = WordTestStatus.STATUS_START;

    public WordTestCase(int wordListIndex, int testedWordIndex) {
        testedWord = WordResources.getWordList(wordListIndex).getWord(testedWordIndex);
        words.add(testedWord);

        testedWordIndexInTheCase = this.testCaseIndex = testedWordIndex;

        for (int i = 0; i < 3; i++) {
            Word word = WordResources.getRandomWord(testedWord);
            words.add(word);
        }

        shuffle();
    }

    public int getTestCaseIndex() {
        return testCaseIndex;
    }

    public int getTestedWordIndexInTheCase() {
        return testedWordIndexInTheCase;
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public Word getWord(int index) {
        return words.get(index);
    }

    public Word getTestedWord() {
        return testedWord;
    }

    public boolean isCorrect(int index) {
        return index == testedWordIndexInTheCase;
    }

    public void shuffle() {
        Collections.shuffle(words);

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getString().equals(testedWord.getString())) {
                testedWordIndexInTheCase = i;
                break;
            }
        }
    }

    public void toNextStatus(boolean isTestPassed) {
        testStatus = WordTestStatus.getNextStatus(testStatus, isTestPassed);
    }

    public void toNextStatus(int choice) {
        toNextStatus(choice == testedWordIndexInTheCase);
    }

    public int getStatus() {
        return testStatus;
    }

}
