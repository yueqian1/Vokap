package com.example.wordlistapp.notebook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.NoteResources;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotebookEditActivity extends AppCompatActivity {

    private FloatingActionButton btn_save;

    private EditText et_title;
    private EditText et_content;
    private EditText et_sentence;
    //记录当前编辑的笔记对象（用于比对是否改变）
    //记录是否是插入状态 （因为也可能是更新（编辑）状态）
    private boolean contentChanged = false;
    private NoteInfo note = null;
    private int notePosition;
    private List<NoteInfo> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        initView();

        noteList = NoteResources.getNoteList();

        Intent intent = getIntent();
        notePosition = intent.getIntExtra("notePosition", -1);
        //主界面点击ListView中的一个Item跳转时
        if (notePosition != -1) {
            note = noteList.get(notePosition);

            et_title.setText(note.getTitle());
            et_content.setText(note.getContent());
            et_sentence.setText(note.getSentence());
        }

        setListener();
    }

    //初始化视图
    private void initView() {
        btn_save = findViewById(R.id.notebook_btn_save);
        et_content = findViewById(R.id.notebook_edit_meaning);
        et_title = findViewById(R.id.notebool_edit_word);
        et_sentence = findViewById(R.id.notebook_edit_example);

        et_content.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
        et_title.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
        et_sentence.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
    }

    //设置监听器
    private void setListener() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkContentEmpty()) {
                    Toast.makeText(NotebookEditActivity.this, R.string.notebook_save_fail, Toast.LENGTH_LONG).show();
                } else {
                    saveNote();
                    Toast.makeText(NotebookEditActivity.this, R.string.notebook_save_success, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contentChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contentChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //保存笔记到数据库 判断是新建还是更新
    private void saveNote() {
        if (!contentChanged) return;

        NoteDataBaseHelper dbHelper = NoteResources.getDbHelper();

        InterfaceActivity inf = new InterfaceActivity();

        ContentValues values = new ContentValues();
        values.put(Note.title, et_title.getText().toString());
        values.put(Note.content, et_content.getText().toString());
        values.put(Note.sentence, et_sentence.getText().toString());

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        values.put(Note.time, sdf.format(date));

        if (note != null) {
            Log.i("-----------", note.getTitle() + " " + note.getContent());
            Log.i(">>>>>>>>>>>", et_title.getText().toString() + " " + et_content.getText().toString());
        }

        if (notePosition == -1) {
            Note.insertNote(dbHelper, values);
        } else {
            Note.updateNote(dbHelper, note.getId(), values);
        }

        contentChanged = false;
    }

    private boolean checkContentEmpty() {
        return et_title.getText().toString().equals("") | et_content.getText().toString().equals("");
    }

    //重写手机上返回按键处理函数，如果更改了提示保存 否则直接返回主界面
    @Override
    public void onBackPressed() {
        boolean display = false;

        if (notePosition == -1) {
            if (!checkContentEmpty()) {
                display = true;
            }
        } else {
            if (contentChanged) {
                display = true;
            }
        }

        if (display) {
            String title = "警告";
            new AlertDialog.Builder(NotebookEditActivity.this)
                    .setIcon(R.drawable.note)
                    .setTitle(title)
                    .setMessage("是否保存当前内容?")
                    .setPositiveButton(R.string.notebook_btn_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveNote();
                            Toast.makeText(NotebookEditActivity.this, R.string.notebook_save_success, Toast.LENGTH_LONG).show();
                            contentChanged = false;
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.notebook_btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
        } else {
            finish();
        }
    }

}
