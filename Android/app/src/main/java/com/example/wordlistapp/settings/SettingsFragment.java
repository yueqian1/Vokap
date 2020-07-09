package com.example.wordlistapp.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.example.wordlistapp.R;
import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.NoteResources;
import com.example.wordlistapp.include.UserInformation;
import com.example.wordlistapp.web.HttpConfig;
import com.example.wordlistapp.web.HttpWord;
import com.example.wordlistapp.web.OkhttpUtil;

public class SettingsFragment extends Fragment {

    private View rootView;
    private Context context;
    private Button btnSignInEntrance;
    private Button btnUpload;
    private Button btnDownload;
    private Button btnLogout;
    private TextView tvUserName;

    private Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case HttpConfig.HTTP_SUCCESS:
                    HttpWord[] notes = JSONArray.parseObject(msg.obj.toString(), HttpWord[].class);

                    NoteResources.downloadAllNotes(notes);
                    handler.removeCallbacksAndMessages(null);

                    Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    break;

                case HttpConfig.HTTP_ERROR:
                    Toast.makeText(context, "连接失败，请联系管理员", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    public void updateView() {
        if (UserInformation.getUserID().equals("")) {
            btnUpload.setVisibility(View.GONE);
            btnDownload.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            btnSignInEntrance.setVisibility(View.VISIBLE);

            tvUserName.setText("未登录");
        } else {
            btnUpload.setVisibility(View.VISIBLE);
            btnDownload.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            btnSignInEntrance.setVisibility(View.GONE);

            tvUserName.setText(UserInformation.getUserName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        initViews();
        initEvents();

        return rootView;
    }

    private void initViews() {
        btnSignInEntrance = rootView.findViewById(R.id.btnSignInEntrance);
        btnUpload = rootView.findViewById(R.id.btnUpload);
        btnDownload = rootView.findViewById(R.id.btnDownload);
        btnLogout = rootView.findViewById(R.id.btnLogout);

        tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserName.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));

        updateView();
    }

    private void initEvents() {
        btnSignInEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteResources.uploadAllNotes(handler);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkhttpUtil.SelectMore(HttpConfig.SELECT_URL, UserInformation.getUserID(), handler);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation.setUserID("");
                updateView();
                Toast.makeText(context, "已退出", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

}
