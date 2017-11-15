package com.datviet.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Phong Phan on 11-Nov-17.
 */

public class BaseFragment extends android.support.v4.app.Fragment {
    private ProgressDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");

    }

    protected void showLoadingDialog() {
        if (pDialog != null) pDialog.show();
    }

    protected void hiddenLoadingDialog() {
        if (pDialog != null) pDialog.cancel();
    }
}
