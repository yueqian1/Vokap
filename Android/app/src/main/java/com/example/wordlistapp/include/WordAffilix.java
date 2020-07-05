package com.example.wordlistapp.include;

public class WordAffilix {
    private String type;
    private String translation;

    public WordAffilix(String type, String translation) {
        this.type = type;
        this.translation = translation;
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
