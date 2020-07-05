package com.example.wordlistapp.include;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WordResources {

    private static AssetManager assetManager;
    private static List<WordList> wordLists = new ArrayList<>();

    public static WordList getWordList(int index) {
        return wordLists.get(index);
    }

    public static int getWordListCount() {
        return wordLists.size();
    }

    public static void init(AssetManager manager) throws IOException {
        assetManager = manager;

        List<Word> allWords = loadList("CET4.txt");
        Collections.shuffle(allWords);

        int count = allWords.size();
        int wordListSize = 50;

        for (int i = 0; i < count / wordListSize; i++) {
            int r = Math.min((i + 1) * wordListSize, count);

            List<Word> list = allWords.subList(i * wordListSize, r);
            wordLists.add(new WordList(list, "Wordlist " + i));
        }
    }

    public static void prepare() {

    }

    public static void saveData() {

    }

    public static List<Word> loadList(String filePath) throws IOException {
        List<Word> words = new LinkedList<Word>();

        String wordStr = loadString(filePath);
        String[] lines = wordStr.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String[] wrd = lines[i].split("\\s+");
            words.add(new Word(wrd[0], new WordAffilix(wrd[1], wrd[2])));
        }

        return words;
    }

    public static String loadString(String filePath) throws IOException {
        InputStream is = assetManager.open(filePath);
        int length = is.available();
        byte buffer[] = new byte[length];
        is.read(buffer);

        return new String(buffer, "utf8");
    }

}
