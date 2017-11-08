package com.datviet.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
    Switch swImage,swSound,swVibrate;

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
        tvAboutUS = (TextView) viewGroup.findViewById(R.id.tvAboutUs);
        swImage = (Switch) viewGroup.findViewById(R.id.swImageSwitch);
        swSound = (Switch) viewGroup.findViewById(R.id.swSoundSwitch);
        swVibrate = (Switch) viewGroup.findViewById(R.id.swVibrate);
        tvDelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setMessage("Bạn có chắc chắn muốn xóa toàn bộ lịch sử ?");
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.sHistoryData.clear();
                        DataManager.saveHistory();
                        Toast.makeText(getContext(),"Đã xóa toàn bộ lịch sử",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
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

        swVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.VIBRATE,true);
                }else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.VIBRATE,false);
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

        Boolean isTurnOnSound = SharedPreferenceUtil.getInstance().getBoolean(Constant.SOUND);
        if (isTurnOnSound == true)
            swSound.setChecked(true);
        else {
            swSound.setChecked(false);
        }

        Boolean isTurnOnVibrate = SharedPreferenceUtil.getInstance().getBoolean(Constant.VIBRATE);
        if (isTurnOnVibrate == true)
            swVibrate.setChecked(true);
        else {
            swVibrate.setChecked(false);
        }
    }
}