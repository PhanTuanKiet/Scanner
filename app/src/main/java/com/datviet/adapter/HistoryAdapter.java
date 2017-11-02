package com.datviet.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datviet.model.History;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historyList;
    private final OnItemClickListener listener;
    Context context;

    public HistoryAdapter(List<History> historyList,OnItemClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codeNumber;
        TextView tvHistoryDate, tvHistoryTime;

        public MyViewHolder(View view) {
            super(view);
            codeNumber = (TextView) view.findViewById(R.id.tvBarcodeNumber);
            tvHistoryDate = (TextView) view.findViewById(R.id.tvHistoryDate);
            tvHistoryTime = (TextView) view.findViewById(R.id.tvHistoryTime);
        }

        public void bind(History history, final int pos, final OnItemClickListener listener) {

            codeNumber.setText(history.getCode());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(pos);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.bind(history,position,listener);

        holder.codeNumber.setText(history.getCode());


        String strDateFormat = history.getDatetime();

        String strSplit = strDateFormat;
        String newSplit = strSplit;
        String[] dateTime = newSplit.split("[,]");

        holder.tvHistoryDate.setText(dateTime[0]);
        holder.tvHistoryTime.setText(dateTime[1]);
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void removeItem(int position) {
        if(historyList!=null){
            historyList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, historyList.size());
        }
        notifyDataSetChanged();
    }

    public void restoreItem(History item, int position) {
        historyList.add(position, item);
        notifyItemInserted(position);
    }
}
