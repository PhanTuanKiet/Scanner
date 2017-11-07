package com.datviet.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.SharedPreferenceUtil;

/**
 * Created by Phong Phan on 21-Oct-17.
 */

public class DetailFragment extends android.support.v4.app.Fragment {

    private static DetailFragment fragment;

    ImageView ivBookImage;
    TextView tvTheLoai,tvTensach;
    LinearLayout lnrDetailHeader;

    public static DetailFragment newInstance() {
        if (fragment == null) fragment = new DetailFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.detail_layout, container, false);
        ivBookImage = (ImageView) viewGroup.findViewById(R.id.ivBookImage);
        tvTheLoai = (TextView) viewGroup.findViewById(R.id.tvTheLoai);
        tvTensach = (TextView) viewGroup.findViewById(R.id.tvTensach);
        lnrDetailHeader = (LinearLayout) viewGroup.findViewById(R.id.lnrDetailHeader);

        return viewGroup;
    }

    public void changeImage() {
        ivBookImage.setVisibility(View.GONE);
        lnrDetailHeader.setMinimumHeight(140);
        tvTensach.setGravity(Gravity.CENTER);
    }

    @Override
    public void onStart() {
        super.onStart();
        Boolean isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.CHANGING_SETTING);
        if (isChecked == true)
           changeImage();
        else {

        }
    }
}
