package com.datviet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datviet.scanner.R;

/**
 * Created by Phong Phan on 08-Nov-17.
 */

public class StudentDetailFragment extends Fragment {

    private static StudentDetailFragment fragment;

    ImageView ivBookImage;
    TextView tvTheLoai,tvTensach;
    LinearLayout lnrDetailHeader;

    public static StudentDetailFragment newInstance() {
        if (fragment == null) fragment = new StudentDetailFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.student_detail_layout, container, false);

        return viewGroup;
    }

}
