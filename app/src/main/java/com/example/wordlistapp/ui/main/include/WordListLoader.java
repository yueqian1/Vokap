package com.example.wordlistapp.ui.main.include;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class WordListLoader {

    public static List<Word> loadList(AssetManager manager, String filePath) throws IOException {
        List<Word> words = new LinkedList<Word>();

        String wordStr = loadString(manager, filePath);
        String[] lines = wordStr.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String[] wrd = lines[i].split("\\s+");
            words.add(new Word(wrd[0], wrd[1], wrd[2]));
        }

        return words;
    }

    public static String loadString(AssetManager manager, String filePath) throws IOException {
        InputStream is = manager.open("CET4.txt");
        int length = is.available();
        byte buffer[] = new byte[length];
        is.read(buffer);

        return new String(buffer, "utf8");
    }
}
