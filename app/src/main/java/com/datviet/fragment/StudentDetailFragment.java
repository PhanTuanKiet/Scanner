package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datviet.adapter.BookBorrowAdapter;
import com.datviet.model.RecyclerViewItem;
import com.datviet.network.API;
import com.datviet.network.RequestListener;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.scanner.SpacingItemDecoration;
import com.datviet.utils.Constant;
import com.datviet.utils.DataManager;
import com.datviet.utils.DateUtil;
import com.datviet.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;


public class StudentDetailFragment extends BaseFragment implements RequestListener, BookBorrowAdapter.OnItemClickListener {

    private final String TAG = StudentDetailFragment.class.getSimpleName();
    private static StudentDetailFragment mFragment;

    private RecyclerView rclBookBorrow;
    private BookBorrowAdapter mAdapter;
    private TextView tvMemberCode, tvStudentName, tvStudentID, tvPhone, tvEmail, tvNumberBookBorrow;
    private String studentCode;
    private AlertDialog.Builder builder;

    private RecyclerViewItem mRecyclerViewItem;


    public static StudentDetailFragment newInstance() {
        if (mFragment == null) mFragment = new StudentDetailFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setData(RecyclerViewItem recyclerViewItem) {
        mRecyclerViewItem = recyclerViewItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.student_detail_layout, container, false);
        initialize(viewGroup);
        return viewGroup;
    }

    public void initialize(ViewGroup viewGroup) {
        mAdapter = new BookBorrowAdapter(DataManager.sBookBorrowData, this);

        tvMemberCode = (TextView) viewGroup.findViewById(R.id.tvMemberCode);
        tvStudentID = (TextView) viewGroup.findViewById(R.id.tvStudentID);
        tvStudentName = (TextView) viewGroup.findViewById(R.id.tvStudentName);
        tvPhone = (TextView) viewGroup.findViewById(R.id.tvPhone);
        tvEmail = (TextView) viewGroup.findViewById(R.id.tvEmail);
        tvNumberBookBorrow = (TextView) viewGroup.findViewById(R.id.tvNumberBookBorrow);

        rclBookBorrow = (RecyclerView) viewGroup.findViewById(R.id.book_borrow_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rclBookBorrow.setLayoutManager(mLayoutManager);
        rclBookBorrow.setItemAnimator(new DefaultItemAnimator());
        rclBookBorrow.addItemDecoration(new SpacingItemDecoration(20));
        rclBookBorrow.setAdapter(mAdapter);

        studentCode = mRecyclerViewItem != null ? mRecyclerViewItem.code : "-1";
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check current tab group
        MainActivity activity = getParentActivity();
        if (activity != null) {
            activity.updateTabStatus(2);
            activity.setTitle(Constant.STUDENT_DETAIL_FRAGMENT_TITLE);
        }
        // Get student detail
        API.getMemberInfo(studentCode, this);

    }

    @Override
    public void onStartRequest() {
        showLoadingDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        DataManager.sBookBorrowData.clear();
    }

    @Override
    public void onResponse(String response) {
        // Parse JSON from Member API
        if (!TextUtils.isEmpty(response))
            try {
                JSONObject jsonRoot = new JSONObject(response);
                JSONObject jsonObject = jsonRoot.getJSONObject("ResponseData");
                JSONArray jsonArray = jsonObject.getJSONArray("ListApiBookBorrowOfMember");

                String numberBookBorrow = String.valueOf(jsonArray.length());
                tvNumberBookBorrow.setText("("+numberBookBorrow+")");

                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject jsonBookObject = jsonArray.getJSONObject(i);
                        String bookBorrowCode = jsonBookObject.getString("BookCode");
                        String bookBorrowName = StringUtil.convertToUTF8(jsonBookObject.getString("BookName"));
                        String beginDate = jsonBookObject.getString("BeginDate");
                        String expireDate = jsonBookObject.getString("ExpirationDate");

                        Date dateBG = DateUtil.parseDateServerWithoutMills(beginDate);
                        Date dateEX = DateUtil.parseDateServerWithoutMills(expireDate);

                        String bgDate = DateUtil.formatToString(dateBG);
                        String exDate = DateUtil.formatToString(dateEX);
                        DataManager.sBookBorrowData.add(new RecyclerViewItem(bookBorrowCode, bookBorrowName, bgDate, exDate));
                    }
                }
                mAdapter.notifyDataSetChanged();

                String code = jsonObject.getString("MemberCode");
                String name = StringUtil.convertToUTF8(jsonObject.getString("MemberName"));
                String id = jsonObject.getString("IdentificationNumber");
                String phone = jsonObject.getString("Phone");
                String email = jsonObject.getString("Email");

                tvMemberCode.setText(code);
                tvStudentID.setText(id);
                tvStudentName.setText(name);
                tvPhone.setText(phone);
                tvEmail.setText(email);

                String responseStatus = jsonRoot.getString("ResponseStatus");
                if (responseStatus.equals("")) {
                    onError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setCancelable(false);
                builder.setMessage("Mã thẻ này không tồn tại \nVui lòng kiểm tra lại. ");
                builder.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                    }
                }).show();
            }
        hiddenLoadingDialog();
    }

    @Override
    public void onError() {
        hiddenLoadingDialog();
        builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.setMessage("Lỗi kết nối \nVui lòng thử lại sau. ");
        builder.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        }).show();
    }

    @Override
    public void onItemClick(int pos) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.transferBookDetailFragment(DataManager.sBookBorrowData.get(pos));
    }


}
