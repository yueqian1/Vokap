package com.example.wordlistapp.notebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

public class Note implements NoteDataBaseHelper.TableCreateInterface {
    // 定义表名
    public static String tableName = "Note";
    // 定义各字段名
    public static String _id = "_id"; // _id是SQLite中自动生成的主键，用语标识唯一的记录，为了方便使用，此处定义对应字段名
    public static String title = "title"; // 标题
    public static String content = "content"; // 内容
    public static String sentence = "sentence";
    public static String time = "date"; // 时间

    //私有化构造方法
    private Note() {
    }

    //初始化实例
    private static Note note = new Note();

    //只提供一个实例
    public static Note getInstance() {
        return note;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + Note.tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + Note.title + " TEXT, "
                + Note.content + " TEXT, "
                + Note.sentence + " TEXT, "
                + Note.time + " TEXT "
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            String sql = "DROP TABLE IF EXISTS " + Note.tableName;
            db.execSQL(sql);
            this.onCreate(db);
        }
    }

    // 插入
    public static void insertNote(NoteDataBaseHelper dbHelper, ContentValues userValues) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(Note.tableName, null, userValues);
        db.close();
    }

    // 删除一条话题
    public static void deleteNote(NoteDataBaseHelper dbHelper, int _id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Note.tableName, Note._id + "=?", new String[]{_id + ""});
        db.close();

    }

    // 删除所有
    public static void deleteAllNote(NoteDataBaseHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Note.tableName, null, null);
        db.close();
    }

    // 修改
    public static void updateNote(NoteDataBaseHelper dbHelper, int _id, ContentValues infoValues) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.update(Note.tableName, infoValues, Note._id + " =? ", new String[]{_id + ""});
        db.close();
    }

    // 以HashMap<String, Object>键值对的形式获取一条信息
    public static HashMap<String, Object> getNote(NoteDataBaseHelper dbHelper, int _id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap<String, Object> NoteMap = new HashMap<String, Object>();
        // 此处要求查询Note._id为传入参数_id的对应记录，使游标指向此记录
        Cursor cursor = db.query(Note.tableName, null, Note._id + " =? ", new String[]{_id + ""}, null, null, null);
        cursor.moveToFirst();
        NoteMap.put(Note.title, cursor.getLong(cursor.getColumnIndex(Note.title)));
        NoteMap.put(Note.content, cursor.getString(cursor.getColumnIndex(Note.content)));
        NoteMap.put(Note.sentence, cursor.getString(cursor.getColumnIndex(Note.sentence)));
        NoteMap.put(Note.time, cursor.getString(cursor.getColumnIndex(Note.time)));

        return NoteMap;

    }

    // 获得查询指向话题表的游标
    public static Cursor getAllNotes(NoteDataBaseHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Note.tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;

    }

}
