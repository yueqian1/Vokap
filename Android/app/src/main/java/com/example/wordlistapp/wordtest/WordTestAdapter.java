package com.example.wordlistapp.wordtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.Word;
import com.example.wordlistapp.include.WordList;
import com.example.wordlistapp.include.WordResources;
import com.example.wordlistapp.include.WordTestCase;
import com.example.wordlistapp.include.WordTestStatus;
import com.example.wordlistapp.notebook.InterfaceActivity;

import java.util.List;

public class WordTestAdapter extends RecyclerView.Adapter<WordTestAdapter.WordTestViewHolder> {

    public static final int MAX_TESTPAGES = 120;

    private List<WordTestCase> testCases;
    private WordTestActivity activity;
    private WordList wordList;

    public WordTestAdapter(WordTestActivity activity, List<WordTestCase> testCases, int wordListIndex) {
        this.activity = activity;
        this.testCases = testCases;
        this.wordList = WordResources.getWordList(wordListIndex);
    }

    private WordTestCase getRemainingTestCase() {
        int index = (int) (Math.random() * testCases.size());
        return testCases.get(index);
    }

    private void setChoiceBarInvisible(WordTestViewHolder holder) {
        for (int i = 0; i < 4; i++) {
            holder.button[i].setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public WordTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wordtest, parent, false);

        return new WordTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WordTestViewHolder holder, final int position) {

        final WordTestCase testCase = getRemainingTestCase();

        activity.addTitle(testCase.getTestedWord());

        holder.choice = -1;

        for (int i = 0; i < 4; i++) {
            holder.button[i].setText(testCase.getWord(i).getAffilix().getTranslation());
            holder.button[i].setBackgroundResource(R.color.wordTestChoiceDefault);
        }

        if (wordList.getWordLearnedStatus(testCase.getTestCaseIndex())) {
            return;
        }

        for (int i = 0; i < 4; i++) {

            final int finalI = i;

            holder.button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.choice != -1) return;

                    holder.choice = finalI;

                    holder.button[finalI].setBackgroundResource(R.color.wordTestChoiceIncorrect);
                    holder.button[testCase.getTestedWordIndexInTheCase()]
                            .setBackgroundResource(R.color.wordTestChoiceCorrect);

                    testCase.toNextStatus(holder.choice);

                    switch (testCase.getStatus()) {
                        case WordTestStatus.STATUS_PASS:
                            activity.displayResult("Congratulations! You have remembered this word");

                            wordList.setWordLearnedStatus(testCase.getTestCaseIndex(), true);
                            testCases.remove(testCase);
                            break;
                        case WordTestStatus.STATUS_PASSONE:
                            activity.displayResult("Great. You'll see this word again later");
                            break;
                        case WordTestStatus.STATUS_FAILONE:
                        case WordTestStatus.STATUS_FAILTWO:
                            activity.displayResult("Oops. You'll see this word again later");
                            break;
                        case WordTestStatus.STATUS_FAIL:
                            activity.displayResult("Sorry you failed. Added to new words");

                            Word word = testCase.getTestedWord();
                            int index = testCase.getTestCaseIndex();

                            if (wordList.getWordFailedStatus(index)) {
                                break;
                            }

                            wordList.setWordFailedStatus(index, true);

                            InterfaceActivity inf = new InterfaceActivity();
                            inf.addOne(
                                    word.getString(),
                                    word.getAffilix().getType() + " " + word.getAffilix().getTranslation(),
                                    ""
                            );

                            break;
                    }

                    if (wordList.isFinished()) {
                        setChoiceBarInvisible(holder);
                    }

                    activity.updateViews();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return MAX_TESTPAGES;
    }

    public static class WordTestViewHolder extends RecyclerView.ViewHolder {
        private Button[] button = new Button[4];
        private int choice = -1;

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
