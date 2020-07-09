package com.example.wordlistapp.web;

public class HttpWord {

    private String englishWord;
    private String chineseWord;
    private String instance;
    private int id;

    public HttpWord() {

    }

    public HttpWord(String englishWord, String chineseWord, String instance, int id) {
        this.englishWord = englishWord;
        this.chineseWord = chineseWord;
        this.instance = instance;
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getChineseWord() {
        return chineseWord;
    }

    public String getInstance() {
        return instance;
    }

    public int getCollection() {
        return id;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setChineseWord(String chineseWord) {
        this.chineseWord = chineseWord;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setid(int collection) {
        this.id = collection;
    }

    @Override
    public String toString() {
        return "{" +
                "englishWord=" + englishWord +
                ", chineseWord='" + chineseWord + '\'' +
                ",instance='" + instance + '\'' +
                '}';
    }
}
