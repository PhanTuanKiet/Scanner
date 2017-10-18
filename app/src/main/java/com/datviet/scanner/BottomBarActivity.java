package com.datviet.scanner;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.HistoryModel;

import java.util.ArrayList;

public class BottomBarActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ListView lvHistory;
    ArrayList<HistoryModel> mData;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_bar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_one:
                        break;
                    case R.id.action_two:
                        // Switch to page two
                        break;
                    case R.id.action_three:
                        // Switch to page three
                        break;
                }
                return true;
            }
        });

    }
}
