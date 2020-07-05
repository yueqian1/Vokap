package com.example.wordlistapp.include;

import java.util.Arrays;
import java.util.List;

public class WordList {

    private int index;
    private int[] score;
    private List<Word> wordList;
    private String name;

    public WordList(List<Word> words, String name) {
        score = new int[words.size()];

        Arrays.fill(score, 5);

        this.wordList = words;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSize() {
        return wordList.size();
    }

    public int getProgress() {
        int count = 0;

        for (int value : score) {
            if (value <= 0) {
                count++;
            }
        }

        return  count / wordList.size();
    }

    public boolean learned(int index) {
        return score[index] <= 0;
    }

    public void successOn(int index) {
        score[index] -= 2;
    }

    public void failOn(int index) {
        score[index] += 1;
    }

    public Word getWord(int index) {
        return wordList.get(index);
    }

}
