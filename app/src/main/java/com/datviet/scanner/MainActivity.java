package com.datviet.scanner;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.fragment.BookDetailFragment;
import com.datviet.fragment.MainHistoryFragment;
import com.datviet.fragment.ScanFragment;
import com.datviet.fragment.SettingFragment;
import com.datviet.fragment.StudentDetailFragment;
import com.datviet.fragment.Transfer;
import com.datviet.model.RecyclerViewItem;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.SharedPreferenceUtil;

public class MainActivity extends BaseActivity implements Transfer, BottomNavigationView.OnNavigationItemSelectedListener {
    private String TAG = MainActivity.class.getSimpleName();
    private static final String CAMERA = "android.permission.CAMERA";
    TextView tvBarTitle;
    Fragment selectedFragment;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBarTitle = (TextView) findViewById(R.id.tvBarTitle);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            tvBarTitle.setText(Constant.SCAN_FRAGMENT_TITLE);
            ScanFragment scanFrag = new ScanFragment();
            fragmentTransaction(scanFrag);
        }

    }

    public void transferBookDetailFragment(RecyclerViewItem recyclerViewItem) {
        BookDetailFragment detailFragment = BookDetailFragment.newInstance();
        tvBarTitle.setText(Constant.BOOK_DETAIL_FRAGMENT_TITLE);
        detailFragment.setData(recyclerViewItem);
        fragmentTransaction(detailFragment);
    }


    public void transferStudentDetailFragment(RecyclerViewItem recyclerViewItem) {
        StudentDetailFragment studentDetailFragment = StudentDetailFragment.newInstance();
        tvBarTitle.setText(Constant.STUDENT_DETAIL_FRAGMENT_TITLE);
        studentDetailFragment.setData(recyclerViewItem);
        fragmentTransaction(studentDetailFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check and grant camera permission at start
        if (checkCameraPermission()) {
        } else {
            requestCameraPermission();
        }
        if (SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE) == null)
            SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE, true);
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
                boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
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
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        } else {
            super.onBackPressed();
        }
    }

    // Update which tab is highlighting
    public void updateTabStatus(int group) {
        navigation.setOnNavigationItemSelectedListener(null);
        switch (group) {
            case 1:
                navigation.setSelectedItemId(R.id.action_scan);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.action_history);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.action_setting);
                break;
        }
        navigation.setOnNavigationItemSelectedListener(this);
    }

    public void setTitle(String title){
        tvBarTitle.setText(title);
    }

    // Initialize bottom bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.action_scan:
                tvBarTitle.setText(Constant.SCAN_FRAGMENT_TITLE);
                selectedFragment = ScanFragment.newInstance();
                break;
            case R.id.action_history:
                tvBarTitle.setText(Constant.HISTORY_FRAGMENT_TITLE);
                selectedFragment = MainHistoryFragment.newInstance();
                break;
            case R.id.action_setting:
                tvBarTitle.setText(Constant.SETTING_FRAGMENT_TITLE);
                selectedFragment = SettingFragment.newInstance();
                break;
        }
        fragmentTransaction(selectedFragment);
        return true;
    }
}
