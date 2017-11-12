package com.datviet.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datviet.scanner.R;
import com.datviet.utils.Constant;
import com.datviet.utils.SharedPreferenceUtil;



public class BookDetailFragment extends BaseFragment {

    private static BookDetailFragment mFragment;

    private ImageView ivBookImage;
    private TextView tvBookGenre,tvBookName;
    private LinearLayout lnrDetailHeader;

    public static BookDetailFragment newInstance() {
        if (mFragment == null) mFragment = new BookDetailFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.book_detail_layout, container, false);
        ivBookImage = (ImageView) viewGroup.findViewById(R.id.ivBookImage);
        tvBookGenre = (TextView) viewGroup.findViewById(R.id.tvBookGenre);
        tvBookName = (TextView) viewGroup.findViewById(R.id.tvBookName);
        lnrDetailHeader = (LinearLayout) viewGroup.findViewById(R.id.lnrDetailHeader);

        return viewGroup;
    }

    public void changeImage() {
        ivBookImage.setVisibility(View.GONE);
        lnrDetailHeader.setMinimumHeight(140);
        tvBookName.setGravity(Gravity.CENTER);
    }

    @Override
    public void onStart() {
        super.onStart();
        Boolean isChecked = SharedPreferenceUtil.getInstance().getBoolean(Constant.LOADING_IMAGE);
        if (isChecked == true)
           changeImage();
        else {
        }
    }
}
