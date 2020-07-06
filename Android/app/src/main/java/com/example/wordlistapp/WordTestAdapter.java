package com.example.wordlistapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.include.WordTestCase;

import java.util.List;

public class WordTestAdapter extends RecyclerView.Adapter<WordTestAdapter.WordTestViewHolder> {

    private List<WordTestCase> testCases;

    public WordTestAdapter(List<WordTestCase> testCases) {
        this.testCases = testCases;
    }

    @NonNull
    @Override
    public WordTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wordtest, parent, false);

        return new WordTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WordTestViewHolder holder, int position) {
        final WordTestCase testCase = testCases.get(position);

        for (int i = 0; i < 4; i++) {
            holder.button[i].setText(testCases.get(position).getWord(i).getAffilix().getTranslation());
            holder.button[i].setBackgroundResource(R.color.wordTestChoiceDefault);

            final int finalI = i;
            holder.button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (testCase.isCorrect(finalI)) {
                        holder.button[finalI].setBackgroundResource(R.color.wordTestChoiceCorrect);
                    } else {
                        holder.button[finalI].setBackgroundResource(R.color.wordTestChoiceIncorrect);
                        int correctIndex = testCase.getTestedWordIndexInTheCase();
                        holder.button[correctIndex].setBackgroundResource(R.color.wordTestChoiceCorrect);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return testCases.size();
    }

    public static class WordTestViewHolder extends RecyclerView.ViewHolder {
        Button[] button = new Button[4];

        public WordTestViewHolder(View itemView) {
            super(itemView);

            initViews();
        }

        private void initViews() {
            button[0] = itemView.findViewById(R.id.btnWordTestChoice1);
            button[1] = itemView.findViewById(R.id.btnWordTestChoice2);
            button[2] = itemView.findViewById(R.id.btnWordTestChoice3);
            button[3] = itemView.findViewById(R.id.btnWordTestChoice4);
        }

    }

}
