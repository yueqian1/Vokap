package com.example.wordlistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.include.FontManager;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;

public class WordCardAdapter extends RecyclerView.Adapter<WordCardAdapter.WordCardViewHolder> {

    @NonNull
    @Override
    public WordCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordcard, parent, false);

        return new WordCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordCardViewHolder holder, int position) {
        WordList wordList = WordResources.getWordList(position);

        holder.progressBar.setMax(wordList.size());
        holder.progressBar.setProgress(wordList.getLearnedProgress());
        holder.textView.setText(wordList.getListName());
        holder.setWordListIndex(position);
    }

    public static class WordCardViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private TextView textView;
        private CardView cardBackground;
        private CardView cardForeground;
        private int wordListIndex;

        public void setWordListIndex(int index) {
            wordListIndex = index;
        }

        public WordCardViewHolder(@NonNull final View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.pbWordCard);
            textView = itemView.findViewById(R.id.tvWordCard);
            cardBackground = itemView.findViewById(R.id.cvWordCard);
            cardForeground = itemView.findViewById(R.id.cvWordCardFore);

            cardForeground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context upContent = itemView.getContext();

                    Intent intent = new Intent(upContent, WordListingActivity.class);
                    intent.putExtra("wordListIndex", wordListIndex);

                    upContent.startActivity(intent);
                }
            });

            textView.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
            cardBackground.setCardElevation(5.0f);
        }

    }

    @Override
    public int getItemCount() {
        return WordResources.getWordListCount();
    }

}
