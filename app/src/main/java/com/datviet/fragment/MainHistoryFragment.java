package com.datviet.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;



public class MainHistoryFragment extends BaseFragment {

    private static MainHistoryFragment mFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public static MainHistoryFragment newInstance() {
        if (mFragment == null) mFragment = new MainHistoryFragment();
        return mFragment;
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

        viewPager.setCurrentItem(0);

        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        Boolean isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.SCAN_MODE);
        if (isChecked == true)
            viewPager.setCurrentItem(1);
        else {
            viewPager.setCurrentItem(0);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BookHistoryFragment(), "SÁCH");
        adapter.addFragment(new StudentHistoryFragment(), "SINH VIÊN");
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
