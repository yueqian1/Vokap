package com.example.wordlistapp.notebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.NoteResources;

import java.util.List;

public class NotebookFragment extends Fragment {

    private ListView noteListView;

    private List<NoteInfo> noteList;
    private NotebookListAdapter mListAdapter;

    private Context context;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notebook, container, false);

        initView();
        setListener();

        return rootView;
    }


    private void initView() {
        noteListView = rootView.findViewById(R.id.note_list);
        //获取noteList
        noteList = NoteResources.getNoteList();
        mListAdapter = new NotebookListAdapter(context, noteList);
        noteListView.setAdapter(mListAdapter);
    }

    //从数据库中读取所有笔记 封装成List<NoteInfo>
    private void getNoteList() {
        noteList.clear();
    }

    //设置监听器
    private void setListener() {

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteInfo noteInfo = noteList.get(position);
                Intent intent = new Intent(context, NotebookEditActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("notePosition", position);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final NoteInfo noteInfo = noteList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setIcon(R.drawable.ic_baseline_delete_forever_24);
                builder.setTitle("要删除么？");

                builder.setPositiveButton(R.string.notebook_btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note.deleteNote(NoteResources.getDbHelper(), Integer.parseInt(noteInfo.getIdStr()));
                        noteList.remove(position);
                        mListAdapter.refreshDataSet();
                        Toast.makeText(context, "删除成功！", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton(R.string.notebook_btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        updateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    public void updateView() {
        //mListAdapter = new NotebookListAdapter(context, noteList);
        NoteResources.refreshNoteList();
        mListAdapter.refreshDataSet();
        noteListView.setAdapter(mListAdapter);

        /*for (NoteInfo i : noteList) {
            Log.i(">>>>>>>>>>>>", i.getIdStr() + " " + i.getTitle() + " " + i.getContent());
        }*/
    }

}