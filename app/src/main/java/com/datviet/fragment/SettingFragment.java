package com.datviet.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.model.History;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.scanner.TransferData;
import com.datviet.utils.DataManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    TextView tvDelHistory;
    DataManager data;
    ArrayList<History> arrayList;
    History history;

    private static SettingFragment fragment;

    public static SettingFragment newInstance() {
        if (fragment == null) fragment = new SettingFragment();
        return fragment;
    }

//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        try
//        {
//            test = (TransferData) context;
//        }
//        catch (ClassCastException e)
//        {
//            throw new ClassCastException(context.toString()+ " must implement TransferData");
//        }
//    }

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

                arrayList = new ArrayList<>();
                String str = tvDelHistory.getText().toString();
                history = new History(str,"2-11-2017");
                arrayList.add(history);
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(arrayList);
                editor.putString("GSON", json);
                editor.commit();
                Log.d("GSON",json);
            }
        });

        return viewGroup;
    }



}