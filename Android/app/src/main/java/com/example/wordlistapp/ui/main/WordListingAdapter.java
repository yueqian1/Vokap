package com.example.wordlistapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.Word;

import java.util.List;

public class WordListingAdapter extends RecyclerView.Adapter<WordListingAdapter.WordListingViewHolder> {

    private Context context;
    private List<Word> wordList;

    public WordListingAdapter(Context context, List<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_word, parent, false);

        return new WordListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListingViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.textView.setText(word.getWord());
    }

    public static class WordListingViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public WordListingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvSingleWord);
        }

    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

}
