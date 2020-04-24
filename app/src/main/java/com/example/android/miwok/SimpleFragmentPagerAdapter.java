package com.example.android.miwok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    public SimpleFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Numbers";
        if(position == 1)
            return "Family";
        if(position == 2)
            return "Colors";
        return "Phrases";
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new NumbersFragment();
        if(position == 1)
            return new FamilyMembersFragment();
        if(position == 2)
            return new ColorsFragment();
        return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
