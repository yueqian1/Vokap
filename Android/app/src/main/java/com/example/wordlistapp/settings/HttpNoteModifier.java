package com.example.wordlistapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.wordlistapp.R;
import com.example.wordlistapp.web.HttpConfig;
import com.example.wordlistapp.web.HttpWord;

public class HttpNoteModifier extends AppCompatActivity {

    private EditText edtEnglish;
    private EditText edtChinese;
    private EditText edtInstance;

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case HttpConfig.SELECT_ONE:
                    HttpWord word = JSON.parseObject(msg.obj.toString(), HttpWord.class);
                    edtChinese.setText(word.getChineseWord());
                    edtInstance.setText(word.getInstance());
                    handler.removeCallbacksAndMessages(null);
                    break;
                case HttpConfig.SELECT_FAILURE:
                    Toast.makeText(HttpNoteModifier.this, edtEnglish.getText().toString() + "不在您的生词库中", Toast.LENGTH_SHORT).show();
                    break;
                case HttpConfig.ADD_SUCCESS:
                    Toast.makeText(HttpNoteModifier.this, edtEnglish.getText().toString() + "成功加入到您的生词库中", Toast.LENGTH_SHORT).show();
                    edtEnglish.setText("");
                    edtChinese.setText("");
                    edtInstance.setText("");
                    break;
                case HttpConfig.ADD_FAILURE:
                    Toast.makeText(HttpNoteModifier.this, "添加失败!\n您的生词库中已存在" + edtEnglish.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case HttpConfig.DELETE_SUCCESS:
                    Toast.makeText(HttpNoteModifier.this, edtEnglish.getText().toString() + "已经从您的生词库中删除", Toast.LENGTH_SHORT).show();
                    break;
                case HttpConfig.DELETE_FAILURE:
                    Toast.makeText(HttpNoteModifier.this, "删除失败！您的生词库中并没有" + edtEnglish.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case HttpConfig.UPDATE_SUCCESS:
                    Toast.makeText(HttpNoteModifier.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case HttpConfig.UPDATE_FAILURE:
                    Toast.makeText(HttpNoteModifier.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_wordtest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        edtEnglish = findViewById(R.id.edt_englishword);
        edtChinese = findViewById(R.id.edt_chineseword);
        edtInstance = findViewById(R.id.edt_instance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpUtil.Add(HttpConfig.UPDATE_URL,HttpConfig.USER_ID,edtEnglish.getText().toString(),edtChinese.getText().toString(),edtInstance.getText().toString(),handler);
            }
        });

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpUtil.SelectOne(HttpConfig.SELECT_URL,HttpConfig.USER_ID,edtEnglish.getText().toString(),handler);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpUtil.Delete(HttpConfig.UPDATE_URL,HttpConfig.USER_ID,edtEnglish.getText().toString(),handler);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpUtil.Update(HttpConfig.UPDATE_URL,HttpConfig.USER_ID,edtEnglish.getText().toString(),edtChinese.getText().toString(),edtInstance.getText().toString(),handler);
            }
        });

         */
    }
}
