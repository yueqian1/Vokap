package com.example.wordlistapp.include;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordTestCase {

    private List<Word> words = new ArrayList<>();
    private Word testedWord;
    private int testedWordIndexInTheCase;
    private int testCaseIndex;

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
            if (words.get(i).equals(testedWord)) {
                testedWordIndexInTheCase = i;
                break;
            }
        }
    }

}
