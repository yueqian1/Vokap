package com.example.wordlistapp.include;

import java.util.Arrays;
import java.util.List;

public class WordList {

    private int index;
    private boolean[] isWordLearned;
    private List<Word> wordList;
    private String listName;

    public WordList(List<Word> words, String name) {
        isWordLearned = new boolean[words.size()];

        // 初始设置所有单词为未掌握
        Arrays.fill(isWordLearned, false);

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
        return getLearnedProgress() == isWordLearned.length;
    }

    public void clearLearningHistory() {
        Arrays.fill(isWordLearned, false);
    }

    public Word getWord(int index) {
        return wordList.get(index);
    }

    public boolean getWordLearnedStatus(int wordIndex) {
        return isWordLearned[wordIndex];
    }

    public void setWordLearnedStatus(int wordIndex, boolean isLearned) {
        isWordLearned[wordIndex] = isLearned;
    }

}
