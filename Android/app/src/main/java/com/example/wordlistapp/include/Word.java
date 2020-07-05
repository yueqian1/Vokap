package com.example.wordlistapp.include;

public class Word {
    private String word;
    private WordAffilix affilix;

    public Word(String word, WordAffilix affilix) {
        this.word = word;
        this.affilix = affilix;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public WordAffilix getAffilix() {
        return affilix;
    }

    public void setAffilix(WordAffilix affilix) {
        this.affilix = affilix;
    }
}
