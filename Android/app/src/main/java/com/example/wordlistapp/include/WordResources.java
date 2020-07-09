package com.example.wordlistapp.include;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
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
    private static int wordLearned = 0;

    public static WordList getWordList(int index) {
        return wordLists.get(index);
    }

    public static int getWordListCount() {
        return wordLists.size();
    }

    private static void loadAllWords(boolean shuffle) throws IOException {
        List<Word> words = loadList("CET4.txt");

        if (shuffle) {
            Collections.shuffle(words);
        }

        allWords = words;
    }

    private static void setupWordMap() {
        for (Word wrd : allWords) {
            wordMap.put(wrd.getString(), wrd.getAffilix());
        }
    }

    private static void setupWordLists() {
        int count = allWords.size();
        int wordListSize = 20;

        for (int i = 0; i < count / wordListSize; i++) {
            int r = Math.min((i + 1) * wordListSize, count);

            List<Word> list = allWords.subList(i * wordListSize, r);
            wordLists.add(new WordList(list, "WordList " + (i + 1)));
        }
    }

    public static void init(AssetManager manager) throws IOException {
        assetManager = manager;

        loadAllWords(true);
        setupWordMap();
        setupWordLists();
    }

    public static void prepare(Context context) throws IOException {
        assetManager = context.getResources().getAssets();
        loadAllWords(false);
        setupWordMap();
        loadListsFromSavedFile(context);
    }

    private static void loadListsFromSavedFile(Context context) throws IOException {

        String fileName = "savedLists.txt";

        InputStream is = context.openFileInput(fileName);
        int length = is.available();
        byte buffer[] = new byte[length];
        is.read(buffer);

        String wordString = new String(buffer, "utf8");

        String[] lines = wordString.split("\\r?\\n");

        /*for (int i = 0; i < lines.length; i++) {
            Log.i(">>>>>>>", lines[i]);
        }*/

        int count = Integer.parseInt(lines[0]);
        int curLine = 1;

        for (int i = 0; i < count; i++) {

            String[] str = lines[curLine++].split("\\|");
            int listSize = Integer.parseInt(str[1]);

            WordList list = new WordList();
            list.setListName(str[0]);

            for (int j = 0; j < listSize; j++) {
                String[] tmp = lines[curLine++].split("\\|");

                boolean learned = tmp[1].equals("1");

                list.add(tmp[0], learned);
            }

            wordLists.add(list);
        }
    }

    public static String wordListsToString() {
        StringBuilder result = new StringBuilder("" + wordLists.size() + "\r\n");

        for (WordList wordList : wordLists) {
            result.append(wordList.getListName()).append("|").append(wordList.size()).append("\r\n");

            for (int i = 0; i < wordList.size(); i++) {
                String newLine = wordList.getWord(i).getString() + "|"
                        + (wordList.getWordLearnedStatus(i) ? 1 : 0) + "\r\n";
                //Log.i("<<<<<<<<<<", newLine);
                result.append(newLine);
            }
        }

        return result.toString();
    }

    public static void saveData(Context context) throws IOException {
        Log.i("WordLists", "Saving lists to file");

        String fileContent = wordListsToString();

        try {
            String fileName = "savedLists.txt";

            FileOutputStream os = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            os.write(fileContent.getBytes());
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("WordResources", "Error writing file");
        }
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

    public static WordAffilix searchWord(String word) {
        return wordMap.get(word);
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

    public static Word getMapped(String word) {
        return new Word(word, wordMap.get(word));
    }

}
