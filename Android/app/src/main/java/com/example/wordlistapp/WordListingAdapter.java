package com.example.wordlistapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.Word;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;

import java.util.List;

public class WordListingAdapter extends RecyclerView.Adapter<WordListingAdapter.WordListingViewHolder> {

    private WordList wordList;

    public WordListingAdapter(int wordListIndex) {
        this.wordList = WordResources.getWordList(wordListIndex);
    }

    @NonNull
    @Override
    public WordListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_word, parent, false);

        return new WordListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WordListingViewHolder holder, int position) {
        final Word word = wordList.getWord(position);
        holder.textView.setText(word.getString());
        holder.textView.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));

        if (!wordList.getWordLearnedStatus(position)) {
            holder.textView.setTextColor(Color.parseColor("#B0B0B0"));
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = holder.showTranslation
                        ? word.getString()
                        : word.getAffilix().getType() + " " + word.getAffilix().getTranslation();
                holder.textView.setText(text);

                holder.showTranslation = !holder.showTranslation;
            }
        });
    }

    public static class WordListingViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        boolean showTranslation = false;

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
