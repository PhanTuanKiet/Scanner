package com.datviet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.SwitchCompat;

import com.datviet.model.History;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.SharedPreferenceUtil;


import java.util.ArrayList;

public class SettingFragment extends Fragment {
    TextView tvDelHistory,tvAboutUS;
    DataManager data;
    ArrayList<History> arrayList;
    History history;
    Switch swImage,swSound;
    private ChangingFragment mCallback;

    private static SettingFragment fragment;

    public interface ChangingFragment{
        void changingSetting();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ChangingFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TransferData");
        }
    }

    public static SettingFragment newInstance() {
        if (fragment == null) fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setting_layout, container, false);
        tvDelHistory = (TextView) viewGroup.findViewById(R.id.tvDelHistory);
        tvAboutUS = (TextView) viewGroup.findViewById(R.id.tvAboutUs);
        swImage = (Switch) viewGroup.findViewById(R.id.swImageSwitch);
        swSound = (Switch) viewGroup.findViewById(R.id.swSoundSwitch);
        tvDelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.sHistoryData.clear();
                Toast.makeText(getContext(),"Đã xóa lịch sử",Toast.LENGTH_LONG).show();
            }
        });

        swImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE,true);
                }else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE,false);
                }
            }
        });

        swSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SOUND,true);
                }else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SOUND,false);
                }
            }
        });

        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        Boolean isTurnOnImage = SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE);
        if (isTurnOnImage == true)
            swImage.setChecked(true);
        else {
            swImage.setChecked(false);
        }

        Boolean isTurnOnSound = SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE);
        if (isTurnOnSound == true)
            swSound.setChecked(true);
        else {
            swSound.setChecked(false);
        }
    }
}