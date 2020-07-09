package com.example.wordlistapp.wordtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.wordtest.WordCardAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class WordCardFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordCardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wordcard, container, false);

        recyclerView = root.findViewById(R.id.rvWordListCard);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new WordCardAdapter();
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        recyclerView.setAdapter(adapter);
    }
}