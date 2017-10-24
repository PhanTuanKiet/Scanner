package com.datviet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datviet.model.History;
import com.datviet.scanner.R;

import java.util.List;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codeNumber;

        public MyViewHolder(View view) {
            super(view);
            codeNumber = (TextView) view.findViewById(R.id.tvBarcodeNumber);
        }
    }


    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
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
        holder.codeNumber.setText(history.getCode());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void removeItem(int position) {
        historyList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(History item, int position) {
        historyList.add(position, item);
        notifyItemInserted(position);
    }
}
