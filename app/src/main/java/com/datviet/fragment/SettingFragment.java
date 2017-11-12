package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.model.History;
import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.SharedPreferenceUtil;

import java.util.ArrayList;

public class SettingFragment extends BaseFragment {
    private TextView tvDelHistory, tvAboutUs;
    private ArrayList<History> arrayList;
    private History history;
    private Switch swImage, swSound, swVibrate;
    private int index;

    private static SettingFragment mFragment;

    public static SettingFragment newInstance() {
        if (mFragment == null) mFragment = new SettingFragment();
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.setting_layout, container, false);
        tvDelHistory = (TextView) viewGroup.findViewById(R.id.tvDelHistory);
        tvAboutUs = (TextView) viewGroup.findViewById(R.id.tvAboutUs);
        swImage = (Switch) viewGroup.findViewById(R.id.swImageSwitch);
        swSound = (Switch) viewGroup.findViewById(R.id.swSoundSwitch);
        swVibrate = (Switch) viewGroup.findViewById(R.id.swVibrate);
        tvDelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((DataManager.sBookHistoryData.size() == 0) && (DataManager.sStudentHistoryData.size() == 0) )
                    Toast.makeText(getContext(), "Chưa có lịch sử quét", Toast.LENGTH_LONG).show();
                else {
                    AlertDialogView();
                }
            }
        });

        swImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE, true);
                else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE, false);
                }
            }
        });

        swSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SOUND, true);
                else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SOUND, false);
                }
            }
        });

        swVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.VIBRATE, true);
                else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.VIBRATE, false);
                }
            }
        });

        tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setTitle("Giới Thiệu");
                builder.setMessage("Công ty Đất Việt \n \n"  + "Lầu 7 (Tòa nhà CĐ Viễn Đông), Lô 2, Đường 16, Công viên phần mềm Quang Trung, P. Tân Chánh Hiệp, Q.12, TP.HCM\n");
                builder.setPositiveButton("ẨN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
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

    private void AlertDialogView() {
        final CharSequence[] items = {"Sách", "Sinh Viên"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setTitle("Bạn có chắc chắn muốn xóa");
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        index = item;
                    }
                });

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (items[index] == "Sách") {
                    DataManager.sBookHistoryData.clear();
                    DataManager.saveBookHistory();
                    Toast.makeText(getContext(),"Đã xóa toàn bộ lịch sử mã sách",Toast.LENGTH_LONG).show();
                }

                if (items[index] == "Sinh Viên"){
                    DataManager.sStudentHistoryData.clear();
                    DataManager.saveStudentHistory();
                    Toast.makeText(getContext(),"Đã xóa toàn bộ lịch sử mã sinh viên",Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}