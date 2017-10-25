package com.datviet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datviet.model.History;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;

import java.util.List;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historyList;
    Context context;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codeNumber;
        TextView tvHistoryDate, tvHistoryTime;

        public MyViewHolder(View view) {
            super(view);
            codeNumber = (TextView) view.findViewById(R.id.tvBarcodeNumber);
            tvHistoryDate = (TextView) view.findViewById(R.id.tvHistoryDate);
            tvHistoryTime = (TextView) view.findViewById(R.id.tvHistoryTime);

            view.setOnClickListener(new View.OnClickListener() {
                Fragment selectedFragment = null;

                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame_content, selectedFragment);
                    transaction.commit();
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
        holder.codeNumber.setText(history.getCode());


//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
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
        historyList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(History item, int position) {
        historyList.add(position, item);
        notifyItemInserted(position);
    }
}
