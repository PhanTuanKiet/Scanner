package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datviet.model.History;
import com.datviet.network.API;
import com.datviet.network.RequestListener;
import com.datviet.scanner.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class StudentDetailFragment extends BaseFragment implements RequestListener {

    private final String TAG = StudentDetailFragment.class.getSimpleName();
    private static StudentDetailFragment mFragment;

    private ImageView ivBookImage;
    private TextView tvMemberCode,tvStudentName,tvStudentID, tvPhone, tvEmail, tvBorrowBookList, tvBorrowBookName, tvBeginDate, tvExpireDate;
    private LinearLayout lnrDetailHeader;
    private String studentCode;

    private History mHistory;


    public static StudentDetailFragment newInstance() {
        if (mFragment == null) mFragment = new StudentDetailFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setData(History history) {
        mHistory = history;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.student_detail_layout, container, false);

        tvMemberCode = (TextView) viewGroup.findViewById(R.id.tvMemberCode);
        tvStudentID = (TextView) viewGroup.findViewById(R.id.tvStudentID);
        tvStudentName = (TextView) viewGroup.findViewById(R.id.tvStudentName);
        tvPhone = (TextView) viewGroup.findViewById(R.id.tvPhone);
        tvEmail = (TextView) viewGroup.findViewById(R.id.tvEmail);
        tvBorrowBookList = (TextView) viewGroup.findViewById(R.id.tvBorrowBookList);
        tvBorrowBookName = (TextView) viewGroup.findViewById(R.id.tvBorrowBookName);
        tvBeginDate = (TextView) viewGroup.findViewById(R.id.tvBeginDate);
        tvExpireDate = (TextView) viewGroup.findViewById(R.id.tvExpireDate);

//        studentCode = mHistory != null ? mHistory.code : "-1";

        API.getMemberInfo("0000", this);
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
                JSONArray jsonArray = jsonObj.getJSONArray("ListApiBookBorrowOfMember");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String bookborrowcode = jsonObject.getString("BookCode");
                    String bookborrowname = jsonObject.getString("BookName");
                    String begindate = jsonObject.getString("BeginDate");
                    String expiredate = jsonObject.getString("ExpirationDate");

                    tvBorrowBookList.setText(bookborrowcode);
                }


                String code = jsonObj.getString("MemberCode");
                String name = jsonObj.getString("MemberName");
                String ID = jsonObj.getString("IdentificationNumber");
                String phone = jsonObj.getString("Phone");
                String email = jsonObj.getString("Email");


                tvMemberCode.setText(code);
                tvStudentID.setText(ID);
                tvStudentName.setText(name);
                tvPhone.setText(phone);
                tvEmail.setText(email);
//                    tvBorrowBookList.setText(bookname);
//                    tvBorrowBookName.setText(bookname);

//                    Date dateBG = DateUtil.parseDateServer(begindate);
//                    tvBeginDate.setText(DateUtil.formatToString(dateBG));
//                    Date dateEx = DateUtil.parseDateServer(expiredate);
//                    tvExpireDate.setText(DateUtil.formatToString(dateEx));
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setMessage("Mã sinh viên này không tồn tại \nVui lòng kiểm tra lại. ");
                builder.setPositiveButton("ẨN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }

        hiddenLoadingDialog();
    }


    @Override
    public void onError() {
        hiddenLoadingDialog();
    }

}
