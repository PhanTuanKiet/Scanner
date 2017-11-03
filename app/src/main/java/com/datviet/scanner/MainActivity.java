package com.datviet.scanner;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.fragment.DetailFragment;
import com.datviet.fragment.HistoryFragment;
import com.datviet.fragment.ScanFragment;
import com.datviet.fragment.SettingFragment;
import com.datviet.model.History;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ScanFragment.Transfer {

    private static final String CAMERA ="android.permission.CAMERA" ;
    TextView tvBarTitle;
    Fragment selectedFragment;
    public static final int CAMERA_SERVICE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBarTitle = (TextView) findViewById(R.id.tvBarTitle);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_one:
                                tvBarTitle.setText("Lịch Sử");
                                selectedFragment = HistoryFragment.newInstance();
                                break;
                            case R.id.action_two:
                                tvBarTitle.setText("Scan");
                                selectedFragment = ScanFragment.newInstance();
                                break;
                            case R.id.action_three:

                                tvBarTitle.setText("Cài Đặt");
                                selectedFragment = SettingFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        if (savedInstanceState == null) {
            tvBarTitle.setText("Scan");
            ScanFragment scan = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_content, scan);
            fragmentTransaction.commit();

        }

    }

    public void addFragmentDetail(History history) {
        Fragment detailFragment = DetailFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA, history);
        detailFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, detailFragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(checkCameraPermission()){

        }
        else {
            requestCameraPermission();
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String json = sharedPrefs.getString("GSON", "");

        if (TextUtils.isEmpty(json)){
            DataManager.sHistoryData = new ArrayList<>();
        }else{
            Type listType = new TypeToken<List<History>>() {}.getType();
            List<History> arr = DataManager.gson.fromJson(json, listType);
            if (arr != null)
                DataManager.sHistoryData = arr;
            else{
                DataManager.sHistoryData = new ArrayList<>();
            }
        }
//        DataManager.clear();
    }

    @Override
    public void trasnferFragment() {
        HistoryFragment hisFrag = new HistoryFragment().newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, hisFrag);
        transaction.commit();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{CAMERA},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean SendSMSPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied access your camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public boolean checkCameraPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
    }


}
