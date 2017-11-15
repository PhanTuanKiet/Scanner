package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.datviet.utils.Constant;
import com.datviet.utils.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class BookDetailFragment extends BaseFragment implements RequestListener {

    private static BookDetailFragment mFragment;

    private ImageView ivBookImage;
    private TextView tvBookCode, tvBookCategory, tvBookName, tvBookQuantity,
            tvPublisher, tvPulishYear, tvSupplierName, tvBookAuthor, tvBookDescription, tvShortDescription;
    private LinearLayout lnrDetailHeader;
    private String bookCode;
    AlertDialog.Builder builder;
    Boolean isChecked;

    private History mHistory;

    public static BookDetailFragment newInstance() {
        if (mFragment == null) mFragment = new BookDetailFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.book_detail_layout, container, false);
        ivBookImage = (ImageView) viewGroup.findViewById(R.id.ivBookImage);
        tvBookCode = (TextView) viewGroup.findViewById(R.id.tvBookCode);
        tvBookCategory = (TextView) viewGroup.findViewById(R.id.tvBookCategory);
        tvBookName = (TextView) viewGroup.findViewById(R.id.tvBookName);
        tvBookQuantity = (TextView) viewGroup.findViewById(R.id.tvBookQuantity);
        tvPublisher = (TextView) viewGroup.findViewById(R.id.tvPublisher);
        tvPulishYear = (TextView) viewGroup.findViewById(R.id.tvPublishYear);
        tvSupplierName = (TextView) viewGroup.findViewById(R.id.tvSupplierName);
        tvBookAuthor = (TextView) viewGroup.findViewById(R.id.tvBookAuthor);
        tvBookDescription = (TextView) viewGroup.findViewById(R.id.tvBookDescription);
        tvShortDescription = (TextView) viewGroup.findViewById(R.id.tvBookShortDescription);

        lnrDetailHeader = (LinearLayout) viewGroup.findViewById(R.id.lnrDetailHeader);

        bookCode = mHistory != null ? mHistory.code : "-1";

        API.getBookInfo(bookCode, this);
        return viewGroup;
    }

    public void turnoffImage() {
        ivBookImage.setVisibility(View.GONE);
        lnrDetailHeader.setMinimumHeight(140);
        tvBookName.setGravity(Gravity.CENTER);
    }

    @Override
    public void onStart() {
        super.onStart();
        isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE);
        if (isChecked == false)
            turnoffImage();
        else {
        }
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
                JSONArray jsonArray = jsonRoot.getJSONArray("ResponseData");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String code = jsonObject.getString("BookCode");
                String name = jsonObject.getString("BookName");
                String author = jsonObject.getString("AuthorName");
                String booknumber = jsonObject.getString("NumberBookInLib");
                String category = new String(jsonObject.getString("CategoryName").getBytes("ISO-8859-1"), "UTF-8");
                String pulisher = new String(jsonObject.getString("PublishingCompany").getBytes("ISO-8859-1"), "UTF-8");
                ;
                String publishyear = jsonObject.getString("PublishYear");
                String suppliername = jsonObject.getString("SupplierName");
                String description = jsonObject.getString("BookDescription");
                String shortdescription = jsonObject.getString("BookShortDescription");

                if (isChecked == true) {
                    String imagelink = jsonObject.getString("FaceBookImage");
                    String img = "https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png";
                    Picasso.with(getContext()).load(img).placeholder(R.drawable.book_placeholder).into(ivBookImage);

                }

                tvBookCode.setText(code);
                tvBookName.setText(name);
                tvBookAuthor.setText(author);
                tvPublisher.setText(pulisher);
                tvPulishYear.setText(publishyear);
                tvSupplierName.setText(suppliername);
                tvBookCategory.setText(category);
                tvBookQuantity.setText(booknumber);
                tvBookDescription.setText(description);
                tvShortDescription.setText(shortdescription);

                String responseStatus = jsonRoot.getString("ResponseStatus");
                if (!responseStatus.equals("200")) {
                    onError();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setCancelable(false);
                builder.setMessage("Mã sách này không tồn tại \nVui lòng kiểm tra lại. ");
                builder.setPositiveButton("ẨN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                    }
                }).show();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        hiddenLoadingDialog();
    }

    @Override
    public void onError() {
        hiddenLoadingDialog();
        builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.setMessage("Lỗi kết nối \nVui lòng thử lại sau. ");
        builder.setPositiveButton("ẨN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        }).show();
    }


}
