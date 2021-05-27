package ru.mirea.makarov.loadermanger;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.Collections;
import java.util.Random;

public class MyLoader extends AsyncTaskLoader<String> {
    private String firstName;
    public static final String ARG_WORD = "word";
    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null){
            firstName = args.getString(ARG_WORD);
            char[] words = firstName.toCharArray();
            for (int ind = 0;  ind < words.length; ++ind){
                int newInd = getRandomNumber(0, words.length);
                char symbolSave = words[newInd];
                words[newInd] = words[ind];
                words[ind] = symbolSave;
             }
            firstName = String.copyValueOf(words);
        }
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        SystemClock.sleep(5000);
        return firstName;
    }
}
