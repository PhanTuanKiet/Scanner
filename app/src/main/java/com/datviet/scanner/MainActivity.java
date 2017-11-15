package com.datviet.scanner;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.fragment.BookDetailFragment;
import com.datviet.fragment.MainHistoryFragment;
import com.datviet.fragment.ScanFragment;
import com.datviet.fragment.SettingFragment;
import com.datviet.fragment.StudentDetailFragment;
import com.datviet.fragment.Transfer;
import com.datviet.model.History;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity implements Transfer {

    private static final String CAMERA = "android.permission.CAMERA";
    TextView tvBarTitle;
    Fragment selectedFragment;
    Toolbar toolbar;
    public static final int CAMERA_SERVICE_CODE = 1;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();


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
                                selectedFragment = MainHistoryFragment.newInstance();
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
                        fragmentTransaction(selectedFragment);
                        return true;
                    }
                });

        if (savedInstanceState == null) {
            tvBarTitle.setText("Scan");
            ScanFragment scanFrag = new ScanFragment();
            fragmentTransaction(scanFrag);

        }

    }

    public void transferBookDetailFragment(History history) {
        BookDetailFragment detailFragment = BookDetailFragment.newInstance();
        tvBarTitle.setText("Chi Tiết Sách");
        detailFragment.setData(history);
        fragmentTransaction(detailFragment);
    }

    public void transferStudentDetailFragment(History history) {
        StudentDetailFragment studentDetailFragment = StudentDetailFragment.newInstance();
        tvBarTitle.setText("Chi Tiết Sinh Viên");
        studentDetailFragment.setData(history);
        fragmentTransaction(studentDetailFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkCameraPermission()) {
        } else {
            requestCameraPermission();
        }
        if (SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE) == null)
            SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE,true);
        DataManager.loadBookHistoryData();
        DataManager.loadStudentHistoryData();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{CAMERA}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean SendSMSPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            } else {
                Toast.makeText(MainActivity.this, "Truy cập Camera bị từ chối", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    public boolean checkCameraPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    public void fragmentTransaction(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragment).addToBackStack(null);
        transaction.commit();
        transaction.addToBackStack(null);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            super.onBackPressed();
        }
    }
}
