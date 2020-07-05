package com.example.wordlistapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.WordListingActivity;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;

public class WordCardAdapter extends RecyclerView.Adapter<WordCardAdapter.WordCardViewHolder> {

    private Context context;

    public WordCardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WordCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wordcard, parent, false);

        return new WordCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordCardViewHolder holder, int position) {
        WordList wordList = WordResources.getWordList(position);

        holder.progressBar.setMax(wordList.getSize());
        holder.progressBar.setProgress((int) (Math.random() * wordList.getSize()));
        holder.textView.setText(wordList.getName());
        holder.setWordListIndex(position);
    }

    public static class WordCardViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private TextView textView;
        private CardView cardView;
        private int wordListIndex;

        public void setWordListIndex(int index) {
            wordListIndex = index;
        }

        public WordCardViewHolder(@NonNull final View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.pbWordCard);
            textView = itemView.findViewById(R.id.tvWordCard);
            cardView = itemView.findViewById(R.id.cvWordCard);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context upContent = itemView.getContext();

                    Intent intent = new Intent(upContent, WordListingActivity.class);
                    intent.putExtra("wordListIndex", wordListIndex);

                    upContent.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return WordResources.getWordListCount();
    }

}
