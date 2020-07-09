package com.example.wordlistapp.include;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WordList {

    private int index;
    private List<Boolean> isWordLearned;
    private List<Boolean> isWordFailed;
    private List<Word> wordList;
    private String listName;

    public WordList() {
        wordList = new LinkedList<>();
        isWordLearned = new LinkedList<>();
        isWordFailed = new LinkedList<>();
    }

    public void add(String word, boolean learned) {
        wordList.add(WordResources.getMapped(word));
        isWordLearned.add(learned);
        isWordFailed.add(false);
    }

    public WordList(List<Word> words, String name) {
        // 初始设置所有单词为未掌握
        isWordLearned = new LinkedList<>();
        isWordFailed = new LinkedList<>();

        for (int i = 0; i < words.size(); i++) {
            isWordLearned.add(false);
            isWordFailed.add(false);
        }

        this.wordList = words;
        this.listName = name;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String name) {
        this.listName = name;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int size() {
        return wordList.size();
    }

    public int getLearnedProgress() {
        int count = 0;

        for (boolean i : isWordLearned) {
            if (i) {
                count++;
            }
        }

        return count;
    }

    public boolean isFinished() {
        return getLearnedProgress() == isWordLearned.size();
    }

    public void clearLearningHistory() {
        for (int i = 0; i < isWordLearned.size(); i++) {
            isWordLearned.set(i, false);
        }
    }

    public Word getWord(int index) {
        return wordList.get(index);
    }

    public boolean getWordLearnedStatus(int wordIndex) {
        return isWordLearned.get(wordIndex);
    }

    public void setWordLearnedStatus(int wordIndex, boolean isLearned) {
        isWordLearned.set(wordIndex, isLearned);
    }

    public boolean getWordFailedStatus(int wordIndex) {
        return isWordFailed.get(wordIndex);
    }

    public void setWordFailedStatus(int wordIndex, boolean isFailed) {
        isWordFailed.set(wordIndex, isFailed);
    }

}
