package com.example.wordlistapp.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public NoteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //表创建接口 有多张表时 方便统一调用
    public static interface TableCreateInterface {
        //创建表
        public void onCreate(SQLiteDatabase db);

        //更新表
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Note.getInstance().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Note.getInstance().onUpgrade(db, oldVersion, newVersion);
    }
}
