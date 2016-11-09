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
                frag = QuestionsListFragment.newInstance(BaseActivity.CODING);
                break;
            case 1:
                frag = QuestionsListFragment.newInstance(BaseActivity.ANDROID);
                break;
            case 2:
                frag = QuestionsListFragment.newInstance(BaseActivity.JAVA);
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
                title = BaseActivity.CODING;
                break;
            case 1:
                title = BaseActivity.ANDROID;
                break;
            case 2:
                title = BaseActivity.JAVA;
                break;
        }
        return title;
    }
}
