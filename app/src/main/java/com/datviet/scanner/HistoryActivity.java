package com.datviet.scanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.HistoryModel;

import java.util.ArrayList;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryActivity extends AppCompatActivity {

    ListView lvHistory;
    ArrayList<HistoryModel> mData;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        lvHistory = (ListView) findViewById(R.id.lvHistory);

        //List<HistoryModel> history_list = getListData();
        loadListView();
    }

    private void loadListView(){
        mData = new ArrayList<>();
        mAdapter = new HistoryAdapter(HistoryActivity.this);
        for (int i = 0; i < 12; i++) {
            mData.add(new HistoryModel());
        }
        mAdapter.setData(mData);
        lvHistory.setAdapter(mAdapter);
    }
}
