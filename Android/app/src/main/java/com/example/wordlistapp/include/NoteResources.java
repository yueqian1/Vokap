package com.example.wordlistapp.include;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.example.wordlistapp.notebook.InterfaceActivity;
import com.example.wordlistapp.notebook.Note;
import com.example.wordlistapp.notebook.NoteDataBaseHelper;
import com.example.wordlistapp.notebook.NoteInfo;
import com.example.wordlistapp.web.HttpConfig;
import com.example.wordlistapp.web.HttpWord;
import com.example.wordlistapp.web.OkhttpUtil;

import java.util.LinkedList;
import java.util.List;

public class NoteResources {

    private static List<NoteInfo> noteList = new LinkedList<>();
    private static NoteDataBaseHelper dbHelper;
    private static Context context;

    public static NoteDataBaseHelper getDbHelper() {
        return dbHelper;
    }

    public static List<NoteInfo> getNoteList() {
        return noteList;
    }

    public static void refreshNoteList() {
        noteList.clear();
        loadNoteList();
    }

    private static void loadNoteList() {
        Cursor allNotes = Note.getAllNotes(dbHelper);
        for (allNotes.moveToFirst(); !allNotes.isAfterLast(); allNotes.moveToNext()) {
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setId(allNotes.getString(allNotes.getColumnIndex(Note._id)));
            noteInfo.setTitle(allNotes.getString(allNotes.getColumnIndex(Note.title)));
            noteInfo.setContent(allNotes.getString(allNotes.getColumnIndex(Note.content)));
            noteInfo.setSentence(allNotes.getString(allNotes.getColumnIndex(Note.sentence)));
            noteInfo.setDate(allNotes.getString(allNotes.getColumnIndex(Note.time)));
            noteList.add(noteInfo);
        }
    }

    public static void init(Context cont) {
        context = cont;
        dbHelper = new NoteDataBaseHelper(context, "MyNote.db", null, 1);

        loadNoteList();
    }

    private static List<HttpWord> noteListToHttpWordList() {
        List<HttpWord> httpWordList = new LinkedList<>();

        for (int i = 0; i < noteList.size(); i++) {
            NoteInfo note = noteList.get(i);
            httpWordList.add(new HttpWord(
                    note.getTitle(),
                    note.getContent(),
                    note.getSentence(),
                    1
            ));
        }

        return httpWordList;
    }

    public static void uploadAllNotes(Handler handler) {
        String jsonString = "[";

        List<HttpWord> httpWordList = noteListToHttpWordList();

        for (HttpWord word : httpWordList) {
            jsonString = jsonString + JSON.toJSONString(word);
        }

        jsonString += "]";

        OkhttpUtil.enqueueSyn(HttpConfig.UPDATE_URL, UserInformation.getUserID(), jsonString, handler, "5");
    }

    public static void downloadAllNotes(HttpWord[] words) {
        Note.deleteAllNote(dbHelper);
        noteList.clear();

        InterfaceActivity inf = new InterfaceActivity();

        for (HttpWord word : words) {
            inf.addOne(
                    word.getEnglishWord(),
                    word.getChineseWord(),
                    word.getInstance()
            );
        }
    }

}
