package com.datviet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datviet.scanner.R;

/**
 * Created by Phong Phan on 21-Oct-17.
 */

public class DetailFragment extends android.support.v4.app.Fragment {

    private static DetailFragment fragment;

    public static DetailFragment newInstance() {
        if (fragment == null) fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent it = get;
////Nhận gói dữ liệu từ Activity_Input
//        Bundle b = it.getBundleExtra("data");
////Đọc dữ liệu và gán vào hai biến a và b
//        String x = b.getString("code");

        Bundle bundle = getArguments();
        if(bundle!=null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_layout, container, false);
    }
}
