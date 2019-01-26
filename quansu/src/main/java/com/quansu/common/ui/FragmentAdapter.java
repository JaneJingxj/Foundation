package com.quansu.common.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.quansu.common.mvp.TAB;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/7/18.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<TAB> tabs = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm, ArrayList<TAB> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {

        tabs.get(position).fragment.setArguments(tabs.get(position).bundle);

        return tabs.get(position).fragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).name;
    }

//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        //super.destroyItem(container, position, object);
//    }
}
