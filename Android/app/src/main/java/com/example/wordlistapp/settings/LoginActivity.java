package com.example.wordlistapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.UserInformation;
import com.example.wordlistapp.web.HttpConfig;
import com.example.wordlistapp.web.OkhttpUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText edtName, edtPasswd;
    private Button btnLogin, btnRegister;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HttpConfig.LOGIN_SUCCESS:
                    //HttpConfig.setID(msg.obj.toString());

                    UserInformation.setUserID(msg.obj.toString());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacksAndMessages(null);
                    //startActivity(new Intent(LoginActivity.this,FunctionActivity.class));
                    finish();
                    break;

                case HttpConfig.LOGIN_FAILURE:
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacksAndMessages(null);
                    break;

                case HttpConfig.HTTP_ERROR:
                    Toast.makeText(LoginActivity.this, "连接失败，请联系管理员", Toast.LENGTH_SHORT).show();
                    break;

                case HttpConfig.REFISTER_FAILURE:
                    Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;

                case HttpConfig.HTTP_SUCCESS:
                    UserInformation.setUserID(msg.obj.toString());
                    Toast.makeText(LoginActivity.this, "新用户注册成功！", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacksAndMessages(null);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtName = findViewById(R.id.edt_name);
        edtPasswd = findViewById(R.id.edt_passwd);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        edtName.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_L));
        edtPasswd.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_L));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String passwd = edtPasswd.getText().toString();
                if (name.length() == 0) {
                    edtName.setError("用户名不能为空");
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd.length() == 0) {
                    edtPasswd.setError("密码不能为空");
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserInformation.setUserName(name);
                UserInformation.setPassword(name);
                OkhttpUtil.Login(HttpConfig.SELECT_URL, name, passwd, handler);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String passwd = edtPasswd.getText().toString();
                if (name.length() == 0) {
                    edtName.setError("用户名不能为空");
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd.length() == 0) {
                    edtPasswd.setError("密码不能为空");
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkhttpUtil.Register(HttpConfig.UPDATE_URL, name, passwd, handler);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
