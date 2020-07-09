package com.example.wordlistapp.notebook;

import android.content.ContentValues;

import com.example.wordlistapp.include.NoteResources;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InterfaceActivity implements notebook {

    public void addOne(String word, String meaning, String sentence) {
        NoteDataBaseHelper dbHelper = NoteResources.getDbHelper();
        ContentValues values = new ContentValues();
        values.put(Note.title, word);
        values.put(Note.content, meaning);
        values.put(Note.sentence, sentence);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        values.put(Note.time, sdf.format(date));
        Note.insertNote(dbHelper, values);
    }

    public void saveToWeb() {

    }

}
