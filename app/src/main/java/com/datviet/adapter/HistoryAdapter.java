package com.datviet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datviet.model.HistoryModel;
import com.datviet.scanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryModel> objects = new ArrayList<HistoryModel>();
    private Context context;
    private LayoutInflater layoutInflater;


    public HistoryAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<HistoryModel> data) {
        if (data != null) this.objects = data;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.history_item,null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((HistoryModel) getItem(position),(ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    public void initializeViews(final HistoryModel object, ViewHolder holder, final int position) {
    }

    protected class ViewHolder {
        private TextView tvBarcodeNumber;
        private ImageView ivBarcodeIcon;

        public ViewHolder(View view) {
            tvBarcodeNumber = (TextView) view.findViewById(R.id.tvBarcodeNumber);
            ivBarcodeIcon = (ImageView) view.findViewById(R.id.ivBarcodeIcon);
        }
    }
}
