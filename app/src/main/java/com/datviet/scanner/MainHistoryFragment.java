package com.datviet.scanner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datviet.fragment.DetailFragment;
import com.datviet.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phong Phan on 08-Nov-17.
 */

public class MainHistoryFragment extends Fragment {

    private static MainHistoryFragment fragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public static MainHistoryFragment newInstance() {
        if (fragment == null) fragment = new MainHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.histoy_tab, container, false);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) viewGroup.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return viewGroup;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HistoryFragment(), "SÁCH");
        adapter.addFragment(new DetailFragment(), "SINH VIÊN");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
