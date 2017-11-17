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
import com.datviet.model.RecyclerViewItem;
import com.datviet.network.API;
import com.datviet.network.RequestListener;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.SharedPreferenceUtil;
import com.datviet.utils.StringUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BookDetailFragment extends BaseFragment implements RequestListener {

    private static BookDetailFragment mFragment;

    private ImageView ivBookImage;
    private TextView tvBookCode, tvBookCategory, tvBookName, tvBookRemain,
            tvPublisher, tvPulishYear, tvSupplierName, tvBookAuthor, tvBookDescription, tvShortDescription;
    private LinearLayout lnrDetailHeader;
    private String bookCode;
    AlertDialog.Builder builder;
    Boolean isChecked;

    private RecyclerViewItem mRecyclerViewItem;

    public static BookDetailFragment newInstance() {
        if (mFragment == null) mFragment = new BookDetailFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setData(RecyclerViewItem recyclerViewItem) {
        mRecyclerViewItem = recyclerViewItem;
        bookCode = mRecyclerViewItem != null ? mRecyclerViewItem.code : "-1";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.book_detail_layout, container, false);
        initialize(viewGroup);
        return viewGroup;
    }

    public void initialize(ViewGroup viewGroup){
        ivBookImage = (ImageView) viewGroup.findViewById(R.id.ivBookImage);
        tvBookCode = (TextView) viewGroup.findViewById(R.id.tvBookCode);
        tvBookCategory = (TextView) viewGroup.findViewById(R.id.tvBookCategory);
        tvBookName = (TextView) viewGroup.findViewById(R.id.tvBookName);
        tvBookRemain = (TextView) viewGroup.findViewById(R.id.tvBookRemain);
        tvPublisher = (TextView) viewGroup.findViewById(R.id.tvBookPublisher);
        tvPulishYear = (TextView) viewGroup.findViewById(R.id.tvBookPublishYear);
        tvSupplierName = (TextView) viewGroup.findViewById(R.id.tvBookSupplierName);
        tvBookAuthor = (TextView) viewGroup.findViewById(R.id.tvBookAuthor);
        tvBookDescription = (TextView) viewGroup.findViewById(R.id.tvBookDescription);
        tvShortDescription = (TextView) viewGroup.findViewById(R.id.tvBookShortDescription);
        lnrDetailHeader = (LinearLayout) viewGroup.findViewById(R.id.lnrDetailHeader);
    }

    public void turnOffImage() {
        ivBookImage.setVisibility(View.GONE);
        lnrDetailHeader.setMinimumHeight(140);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check loading image in book detail
        isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE);
        if (isChecked == false)
            turnOffImage();
        // Check current tab group
        MainActivity activity = getParentActivity();
        if(activity!=null) {
            activity.updateTabStatus(2);
            activity.setTitle(Constant.BOOK_DETAIL_FRAGMENT_TITLE);
        }
        // Get book detail
        API.getBookInfo(bookCode, this);

    }

    @Override
    public void onStartRequest() {
        showLoadingDialog();
    }


    @Override
    public void onResponse(String response) {
        // Parse JSON from Book API
        if (!TextUtils.isEmpty(response))
            try {
                JSONObject jsonRoot = new JSONObject(response);
                JSONArray jsonArray = jsonRoot.getJSONArray("ResponseData");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String code = jsonObject.getString("BookCode");
                String name = StringUtil.convertToUTF8(jsonObject.getString("BookName"));

                String bookNumber = jsonObject.getString("NumberBookInLib");
                String category = StringUtil.convertToUTF8(jsonObject.getString("CategoryName"));
                String publisher = StringUtil.convertToUTF8(jsonObject.getString("PublishingCompany"));
                String author = StringUtil.convertToUTF8(jsonObject.getString("AuthorName"));
                String publishYear = jsonObject.getString("PublishYear");
                String supplierName = StringUtil.convertToUTF8(jsonObject.getString("SupplierName"));
                String description = StringUtil.convertToUTF8(jsonObject.getString("BookDescription"));
                String shortDescription = StringUtil.convertToUTF8(jsonObject.getString("BookShortDescription"));

                if (isChecked == true) {
                    String imageLink = jsonObject.getString("FaceBookImage");
                    Picasso.with(getContext()).load(imageLink).placeholder(R.drawable.book_placeholder).into(ivBookImage);
                }

                tvBookCode.setText(code);
                tvBookName.setText(name);
                tvBookAuthor.setText(author);
                tvPublisher.setText(publisher);
                tvPulishYear.setText(publishYear);
                tvSupplierName.setText(supplierName);
                tvBookCategory.setText(category);
                tvBookRemain.setText(bookNumber);
                tvBookDescription.setText(description);
                tvShortDescription.setText(shortDescription);

                String responseStatus = jsonRoot.getString("ResponseStatus");
                if (responseStatus.equals("")) {
                    onError();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setCancelable(false);
                builder.setMessage("Mã sách này không tồn tại \nVui lòng kiểm tra lại. ");
                builder.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                    }
                }).show();

            } catch (Exception e) {
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
        builder.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        }).show();
    }


}
