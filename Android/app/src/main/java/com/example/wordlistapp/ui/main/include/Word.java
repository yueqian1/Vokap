package com.example.wordlistapp.ui.main.include;

public class Word {
    private String word;
    private String type;
    private String translation;

    public Word(String word, String type, String translation) {
        this.word = word;
        this.type = type;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
