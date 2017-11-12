package com.datviet.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.datviet.model.History;
import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.SharedPreferenceUtil;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ScanFragment extends BaseFragment implements View.OnClickListener {

    private Transfer mCallback;
    private CompoundBarcodeView barcodeView;
    private IntentIntegrator intentIntegrator;
    private ArrayList<History> arrayList;

    private History history;
    private Camera camera;
    private Camera.Parameters parameters;
    private ToggleButton tgbScanMode;

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    public interface Transfer {
        void trasnferMainHistoryFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (Transfer) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TransferData");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();

        intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setCameraId(0);  // Use a specific camera of the device
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setOrientationLocked(false);

        if (arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;
        v = inflater.inflate(R.layout.scan_layout, container, false);
        barcodeView = (CompoundBarcodeView) v.findViewById(R.id.barcode_scanner);
        tgbScanMode = (ToggleButton) v.findViewById(R.id.tgbScanMode);
        tgbScanMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SCAN_MODE, true);
                } else {
                    SharedPreferenceUtil.getInstance().saveBoolean(Constant.SCAN_MODE, false);
                }
            }
        });

        barcodeView.decodeContinuous(callback);
        return v;
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

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeView.setStatusText(result.getText());
                Calendar c = Calendar.getInstance();
                DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                Date date = new Date();
                String convertdate = sdf.format(date);
                if (tgbScanMode.isChecked()) {
                    if (DataManager.sStudentHistoryData == null) {
                        DataManager.sStudentHistoryData = new ArrayList<>();
                    }
                    history = new History(result.getText().toString(), convertdate);
                    DataManager.sStudentHistoryData.add(history);
                    DataManager.saveStudentHistory();
                } else {
                    if (DataManager.sBookHistoryData == null) {
                        DataManager.sBookHistoryData = new ArrayList<>();
                    }
                    history = new History(result.getText().toString(), convertdate);
                    DataManager.sBookHistoryData.add(history);
                    DataManager.saveBookHistory();
                }
                mCallback.trasnferMainHistoryFragment();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };



    @Override
    public void onStart() {
        super.onStart();
        Boolean isTurnOnImage = SharedPreferenceUtil.getInstance().getBoolean(Constant.SCAN_MODE);
        if (isTurnOnImage == true)
            tgbScanMode.setChecked(true);
        else {
            tgbScanMode.setChecked(false);
        }
    }

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }

    @Override
    public void onClick(View v) {

    }

}
