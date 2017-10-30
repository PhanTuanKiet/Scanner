package com.datviet.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.datviet.fragment.DetailFragment;
import com.datviet.fragment.HistoryFragment;
import com.datviet.fragment.ScanFragment;
import com.datviet.fragment.SettingFragment;
import com.datviet.model.History;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;

public class MainActivity extends AppCompatActivity implements TransferData{

    TextView tvBarTitle;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBarTitle = (TextView) findViewById(R.id.tvBarTitle);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_one:
                                tvBarTitle.setText("Lịch Sử");
                                selectedFragment = HistoryFragment.newInstance();
                                break;
                            case R.id.action_two:
                                tvBarTitle.setText("Scan");
                                selectedFragment = ScanFragment.newInstance();
                                break;
                            case R.id.action_three:

                                tvBarTitle.setText("Cài Đặt");
                                selectedFragment = SettingFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        if (savedInstanceState == null) {
            tvBarTitle.setText("Scan");
            ScanFragment scan = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_content, scan);
            fragmentTransaction.commit();

        }

    }

    public void addFragmentDetail(History history){
        Fragment detailFragment = DetailFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA, history);
        detailFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, detailFragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataManager.clear();
    }

    @Override
    public void onDataSelected(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("data", "From Activity");
        HistoryFragment historyFragment = new HistoryFragment();
        historyFragment.setArguments(bundle);
    }
}
