package com.datviet.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.datviet.model.History;
import com.datviet.scanner.Common;
import com.datviet.scanner.CoreApplication;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.google.gson.Gson;
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

/**
 * Created by Phong Phan on 19-Oct-17.
 */

public class ScanFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Transfer mCallback;
    CompoundBarcodeView barcodeView;
    ImageButton flash;
    boolean check = true;
    boolean id_flash = true;
    IntentIntegrator intentIntegrator;
    ArrayList<History> arrayList;

    History history;
    Camera camera;
    Camera.Parameters parameters;

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    public interface Transfer{
        void trasnferFragment();
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

        check = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (check == false) {
            Toast.makeText(getContext(), "No Flash On Your Device", Toast.LENGTH_LONG).show();
        }

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
        flash = (ImageButton) v.findViewById(R.id.flash);

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id_flash == true) {
                    On_Flash();
                } else {
                    Off_Flash();
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
                String test = sdf.format(date);
                if(DataManager.sHistoryData == null){
                    DataManager.sHistoryData = new ArrayList<>();
                }
                history = new History(result.getText().toString(),test);
                DataManager.sHistoryData.add(history);
                Common.saveData();

                mCallback.trasnferFragment();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

//    private void getCamera() {
//        if (camera != null && parameters != null) {
//            camera = Camera.open();
//            parameters = camera.getParameters();
//        }
//    }

    private void On_Flash() {
        if (id_flash == true) {
            parameters = camera.getParameters();
            parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            id_flash = false;
        }
    }

    private void Off_Flash() {
        if (id_flash == false) {
            parameters = camera.getParameters();
            parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.startPreview();
            id_flash = true;
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
