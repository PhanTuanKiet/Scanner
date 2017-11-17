package com.datviet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datviet.model.RecyclerViewItem;
import com.datviet.scanner.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<RecyclerViewItem> historyList;
    private final OnItemClickListener listener;

    public HistoryAdapter(List<RecyclerViewItem> historyList, OnItemClickListener listener) {
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

        public void bind(RecyclerViewItem recyclerViewItem, final int pos, final OnItemClickListener listener) {
            String strDateFormat = recyclerViewItem.getDatetime();
            String strSplit = strDateFormat;
            String newSplit = strSplit;
            String[] dateTime = newSplit.split("[,]");
            tvHistoryDate.setText(dateTime[0]);
            tvHistoryTime.setText(dateTime[1]);
            codeNumber.setText(recyclerViewItem.getCode());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null)
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
        RecyclerViewItem history = historyList.get(position);
        holder.bind(history,position,listener);
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void removeItem(int position) {
        if(historyList !=null){
            historyList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,historyList.size());
        }
        notifyDataSetChanged();
    }

    public void restoreItem(RecyclerViewItem recyclerViewItem, int position) {
        historyList.add(position, recyclerViewItem);
        notifyItemInserted(position);
    }
}
