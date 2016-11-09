package com.qb.simpledemo.view.movie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qb.simpledemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class IMovieFragmentList extends Fragment {
    public static final int MOVIE_TYPE_TOP = 0;
    public static final int MOVIE_TYPE_KOUBEI = 1;
    public static final int MOVIE_TYPE_NORTH = 2;
    @Bind(R.id.tab_layout)
    TabLayout mTablayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moives_fragment, null);
        ButterKnife.bind(this, view);
        setupViewPager(mViewPager);
        mTablayout.addTab(mTablayout.newTab().setText("top250"));
        mTablayout.addTab(mTablayout.newTab().setText("正在热映"));
        mTablayout.addTab(mTablayout.newTab().setText("即将上映"));
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(IMovieFragment.newInstance(MOVIE_TYPE_TOP), "top250");
        adapter.addFragment(IMovieFragment.newInstance(MOVIE_TYPE_KOUBEI), "正在热映");
        adapter.addFragment(IMovieFragment.newInstance(MOVIE_TYPE_NORTH), "即将上映");
        mViewPager.setAdapter(adapter);
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
