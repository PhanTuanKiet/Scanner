package com.datviet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datviet.network.API;
import com.datviet.network.RequestListener;
import com.datviet.scanner.R;
import com.datviet.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class StudentDetailFragment extends BaseFragment implements RequestListener {

    private final String TAG = StudentDetailFragment.class.getSimpleName();
    private static StudentDetailFragment mFragment;

    private ImageView ivBookImage;
    private TextView tvStudentName, tvStudentID, tvPhone, tvBorrowBookList, tvBorrowBookName, tvBeginDate, tvExpireDate;
    private LinearLayout lnrDetailHeader;


    public static StudentDetailFragment newInstance() {
        if (mFragment == null) mFragment = new StudentDetailFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.student_detail_layout, container, false);

        tvStudentID = (TextView) viewGroup.findViewById(R.id.tvStudentID);
        tvStudentName = (TextView) viewGroup.findViewById(R.id.tvStudentName);
        tvPhone = (TextView) viewGroup.findViewById(R.id.tvPhone);
        tvBorrowBookList = (TextView) viewGroup.findViewById(R.id.tvBorrowBookList);
        tvBorrowBookName = (TextView) viewGroup.findViewById(R.id.tvBorrowBookName);
        tvBeginDate = (TextView) viewGroup.findViewById(R.id.tvBeginDate);
        tvExpireDate = (TextView) viewGroup.findViewById(R.id.tvExpireDate);

        API.getMemberInfo("TV3", this);
        return viewGroup;
    }

    @Override
    public void onStartRequest() {
        showLoadingDialog();
    }

    @Override
    public void onResponse(String response) {

        if (!TextUtils.isEmpty(response))
            try {
                JSONObject jsonRoot = new JSONObject(response);
                JSONObject jsonObj = jsonRoot.getJSONObject("ResponseData");

                String code = jsonObj.getString("MemberCode");
                String name = jsonObj.getString("MemberName");
                String ID = jsonObj.getString("IdentificationNumber");
                String phone = jsonObj.getString("Phone");
                String email = jsonObj.getString("Email");
                String bookname = jsonObj.getString("BookName");
                String begindate = jsonObj.getString("BeginDate");
                String expiredate = jsonObj.getString("ExpirationDate");


                tvStudentID.setText(ID);
                tvStudentName.setText(name);
                tvPhone.setText(phone);
                tvBorrowBookList.setText(bookname);
                tvBorrowBookName.setText(bookname);

                Date dateBG = DateUtil.parseDateServer(begindate);
                tvBeginDate.setText(DateUtil.formatToString(dateBG));
                Date dateEx = DateUtil.parseDateServer(expiredate);
                tvExpireDate.setText(DateUtil.formatToString(dateEx));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        hiddeLoadingDialog();
    }

    @Override
    public void onError() {
        hiddeLoadingDialog();
    }

}
