package com.example.wordlistapp.include;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordResources {

    // 词库资源的管理类
    // 在应用启动时从保存的词库配置文件载入词库
    // 如是初次启动，则调用init()初始化

    private static AssetManager assetManager;
    private static List<WordList> wordLists = new ArrayList<>();
    private static List<Word> allWords = new ArrayList<>();
    private static Map<String, WordAffilix> wordMap = new HashMap<>();
    private int wordLearned = 0;

    public static WordList getWordList(int index) {
        return wordLists.get(index);
    }

    public static int getWordListCount() {
        return wordLists.size();
    }

    public static void init(AssetManager manager) throws IOException {
        assetManager = manager;

        List<Word> words = loadList("CET4.txt");
        Collections.shuffle(words);

        allWords = words;
        int count = allWords.size();
        int wordListSize = 50;

        for (int i = 0; i < count / wordListSize; i++) {
            int r = Math.min((i + 1) * wordListSize, count);

            List<Word> list = allWords.subList(i * wordListSize, r);
            wordLists.add(new WordList(list, "WordList " + i));

            for (Word wrd : list) {
                wordMap.put(wrd.getString(), wrd.getAffilix());
            }
        }
    }

    public static WordAffilix searchWord(String word) {
        return wordMap.get(word);
    }

    public static void prepare() {

    }

    public static void saveData() {

    }

    public static List<Word> loadList(String filePath) throws IOException {
        List<Word> words = new ArrayList<>();

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

    public static Word getRandomWord() {
        int wordIndex = (int) (allWords.size() * Math.random());

        return allWords.get(wordIndex);
    }

    public static Word getRandomWord(String exclude) {
        Word word = getRandomWord();

        while (word.getString().equals(exclude)) {
            word = getRandomWord();
        }

        return word;
    }

    public static Word getRandomWord(Word exclude) {
        Word word = getRandomWord();

        while (word.equals(exclude)) {
            word = getRandomWord();
        }

        return word;
    }

}
