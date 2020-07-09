package com.example.wordlistapp.notebook;

import java.io.Serializable;

public class NoteInfo implements Serializable {

    private String id;
    private String title;
    private String content;
    private String sentence;
    private String date;

    public int getId() {
        return Integer.parseInt(id);
    }

    public String getIdStr() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
