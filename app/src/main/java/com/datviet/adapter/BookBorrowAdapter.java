package com.datviet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.datviet.model.RecyclerViewItem;
import com.datviet.scanner.R;

import java.util.List;

public class BookBorrowAdapter extends RecyclerView.Adapter<BookBorrowAdapter.MyViewHolder>  {
    private List<RecyclerViewItem> bookBorrowList;
    private final OnItemClickListener listener;

    public BookBorrowAdapter(List<RecyclerViewItem> bookBorrowList, OnItemClickListener listener) {
        this.bookBorrowList = bookBorrowList;
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBookBorrowName, tvBookBorrowCode;
        TextView tvBeginDate, tvExpireDate;

        public MyViewHolder(View view) {
            super(view);
            tvBookBorrowName = (TextView) view.findViewById(R.id.tvBorrowName);
            tvBookBorrowCode = (TextView) view.findViewById(R.id.tvBorrowCode);
            tvBeginDate = (TextView) view.findViewById(R.id.tvBeginDate);
            tvExpireDate = (TextView) view.findViewById(R.id.tvExpireDate);
        }

        public void bind(RecyclerViewItem bookBorrow, final int pos, final OnItemClickListener listener) {

            tvBookBorrowName.setText(bookBorrow.getName());
            tvBookBorrowCode.setText(bookBorrow.getCode());
            tvBeginDate.setText(bookBorrow.getBeginDate());
            tvExpireDate.setText(bookBorrow.getExpireDate());

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
                .inflate(R.layout.book_borrow_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerViewItem book = bookBorrowList.get(position);
        holder.bind(book,position,listener);
    }


    @Override
    public int getItemCount() {
        return bookBorrowList.size();
    }

    public void removeItem(int position) {
        if(bookBorrowList !=null){
            bookBorrowList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, bookBorrowList.size());
        }
        notifyDataSetChanged();
    }

    public void restoreItem(RecyclerViewItem recyclerViewItem, int position) {
        bookBorrowList.add(position, recyclerViewItem);
        notifyItemInserted(position);
    }
}

