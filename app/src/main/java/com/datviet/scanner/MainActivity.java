package com.datviet.scanner;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.datviet.fragment.DetailFragment;
import com.datviet.fragment.HistoryFragment;
import com.datviet.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    TextView tvBarTitle;

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
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_one:
                                tvBarTitle.setText("Lịch Sử");
                                selectedFragment = HistoryFragment.newInstance();
                                break;
                            case R.id.action_two:
                                selectedFragment = DetailFragment.newInstance();
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



    }
}
