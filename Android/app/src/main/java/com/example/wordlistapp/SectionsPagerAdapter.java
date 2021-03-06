package com.example.wordlistapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wordlistapp.notebook.NotebookFragment;
import com.example.wordlistapp.schedule.ScheduleFragment;
import com.example.wordlistapp.settings.SettingsFragment;
import com.example.wordlistapp.wordtest.WordCardFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_text_wordcards,
            R.string.tab_text_newwords,
            R.string.tab_text_myplan,
            R.string.tab_text_options
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WordCardFragment();
            case 1:
                return new NotebookFragment();
            case 2:
                return new ScheduleFragment();
            case 3:
                return new SettingsFragment();
            default:
                return new WordCardFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}