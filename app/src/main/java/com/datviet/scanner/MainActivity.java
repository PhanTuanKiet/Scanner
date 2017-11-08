package com.datviet.scanner;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.fragment.DetailFragment;
import com.datviet.fragment.MainHistoryFragment;
import com.datviet.fragment.ScanFragment;
import com.datviet.fragment.SettingFragment;
import com.datviet.fragment.StudentDetailFragment;
import com.datviet.model.History;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;

public class MainActivity extends AppCompatActivity implements ScanFragment.Transfer {

    private static final String CAMERA = "android.permission.CAMERA";
    TextView tvBarTitle;
    Fragment selectedFragment;
    Toolbar toolbar;
    public static final int CAMERA_SERVICE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBarTitle = (TextView) findViewById(R.id.tvBarTitle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);

        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_one:
                                toolbar.setVisibility(View.GONE);
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

    public void addFragmentDetail(History history) {
        Fragment detailFragment = DetailFragment.newInstance();
        String str = history.getCode().toString();
        tvBarTitle.setText(str);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA, history);
        detailFragment.setArguments(bundle);
        fragmentTransaction(detailFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkCameraPermission()) {
        } else {
            requestCameraPermission();
        }

        DataManager.loadHistoryData();
    }

    @Override
    public void trasnferBookFragment() {
        MainHistoryFragment historyFrag = new MainHistoryFragment().newInstance();
        fragmentTransaction(historyFrag);
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
                Toast.makeText(MainActivity.this, "Permission denied access your camera", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    public boolean checkCameraPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void trasnferStudentFragment() {
        StudentDetailFragment studentDetailFrag = new StudentDetailFragment().newInstance();
        fragmentTransaction(studentDetailFrag);
    }

    public void fragmentTransaction(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragment);
        transaction.commit();
    }
}
