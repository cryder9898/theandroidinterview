package com.android.interview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.interview.navigationdrawer.QuestionsListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag=new QuestionsListFragment();
                break;
            case 1:
                frag=new QuestionsListFragment();
                break;
            case 2:
                frag=new QuestionsListFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Coding";
                break;
            case 1:
                title="Android";
                break;
            case 2:
                title="Java";
                break;
        }

        return title;
    }
}
