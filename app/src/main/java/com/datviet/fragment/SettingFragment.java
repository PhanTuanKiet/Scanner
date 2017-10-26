package com.datviet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.scanner.R;
import com.datviet.utils.DataManager;

public class SettingFragment extends Fragment {

    TextView tvDelHistory;
    DataManager data;

    private static SettingFragment fragment;

    public static SettingFragment newInstance() {
        if (fragment == null) fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setting_layout, container, false);
        tvDelHistory = (TextView) viewGroup.findViewById(R.id.tvDelHistory);
        tvDelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.sHistoryData.clear();
                Toast.makeText(getContext(),"Đã xóa lịch sử",Toast.LENGTH_LONG).show();

            }
        });


        return viewGroup;
    }


}