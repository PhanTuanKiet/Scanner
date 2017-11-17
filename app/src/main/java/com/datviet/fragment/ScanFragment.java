package com.datviet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.datviet.model.RecyclerViewItem;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.DateUtil;
import com.datviet.utils.SharedPreferenceUtil;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.ArrayList;
import java.util.List;


public class ScanFragment extends BaseFragment implements View.OnClickListener, BarcodeCallback {

    public Transfer mCallback;
    private static ScanFragment mFragment;
    private CompoundBarcodeView barcodeView;
    private TextView tvScanMode;
    private IntentIntegrator mIntentIntegrator;

    private ToggleButton tgbScanMode;

    public static ScanFragment newInstance() {
        if (mFragment == null) mFragment = new ScanFragment();
        return mFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (Transfer) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Transfer interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntentIntegrator = new IntentIntegrator(getActivity());
        mIntentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        mIntentIntegrator.setPrompt("Scan a barcode");
        mIntentIntegrator.setCameraId(0);  // Use a specific camera of the device
        mIntentIntegrator.setBeepEnabled(false);
        mIntentIntegrator.setBarcodeImageEnabled(true);
        mIntentIntegrator.setOrientationLocked(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scan_layout, container, false);
        initialize(v);
        return v;
    }

    public void initialize(View v){
        barcodeView = (CompoundBarcodeView) v.findViewById(R.id.barcode_scanner);
        tvScanMode = (TextView) v.findViewById(R.id.tvScanMode);
        tgbScanMode = (ToggleButton) v.findViewById(R.id.tgbScanMode);
        tgbScanMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SCAN_MODE, true);
                    tvScanMode.setText("Quét mã sinh viên");
                } else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SCAN_MODE, false);
                    tvScanMode.setText("Quét mã sách");
                }
            }
        });
        barcodeView.setStatusText("");
        barcodeView.decodeContinuous(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "khong co du lieu", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check scan mode
        Boolean isTurnOnMode = SharedPreferenceUtil.getInstance().getBoolean(Constant.SCAN_MODE);
        if (isTurnOnMode == true) {
            tgbScanMode.setChecked(true);
        }else {
            tgbScanMode.setChecked(false);
        }
        // Check scanner sound
        Boolean isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.SOUND);
        if (isChecked == true)
            mIntentIntegrator.setBeepEnabled(true);
        // Check current tab group
        MainActivity activity = getParentActivity();
        if(activity!=null){
            activity.updateTabStatus(1);
            activity.setTitle(Constant.SCAN_FRAGMENT_TITLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void barcodeResult(BarcodeResult result) {

        barcodeView.pause();

        if (result.getText() != null) {
            // Transfer data depend on scan mode
            RecyclerViewItem recyclerViewItem;
            if (tgbScanMode.isChecked()) {

                if (DataManager.sStudentHistoryData == null) {
                    DataManager.sStudentHistoryData = new ArrayList<>();
                }
                recyclerViewItem = new RecyclerViewItem(result.getText().toString(), DateUtil.getCurrentDate());
                DataManager.sStudentHistoryData.add(recyclerViewItem);
                DataManager.saveStudentHistory();
                mCallback.transferStudentDetailFragment(recyclerViewItem);

            } else {
                if (DataManager.sBookHistoryData == null) {
                    DataManager.sBookHistoryData = new ArrayList<>();
                }

                recyclerViewItem = new RecyclerViewItem(result.getText().toString(), DateUtil.getCurrentDate());
                DataManager.sBookHistoryData.add(recyclerViewItem);
                DataManager.saveBookHistory();
                mCallback.transferBookDetailFragment(recyclerViewItem);
            }
        }
        barcodeView.resume();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}
