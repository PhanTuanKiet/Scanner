package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.datviet.scanner.SpacingItemDecoration;
import com.google.firebase.database.DatabaseReference;


public class BookHistoryFragment extends BaseFragment implements HistoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private static BookHistoryFragment mFragment;
    private ImageView ivBookBG;

    private DatabaseReference mData;

    public BookHistoryFragment() {
    }


    public static BookHistoryFragment newInstance() {
        if (mFragment == null) mFragment = new BookHistoryFragment();
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.history_layout, container, false);
        initialize(viewGroup);
        return viewGroup;
    }

    public void initialize(ViewGroup viewGroup){
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        ivBookBG = (ImageView) viewGroup.findViewById(R.id.ivBookIconBG);
        mAdapter = new HistoryAdapter(DataManager.sBookHistoryData, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpacingItemDecoration(20));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                    builder.setMessage("Bạn có chắc chắn muốn xóa ?");

                    builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.removeItem(position);
                            DataManager.saveBookHistory();
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(mAdapter);
                            if (mAdapter.getItemCount()==0)
                                ivBookBG.setVisibility(View.VISIBLE);
                        }
                    }).setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }).show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter.getItemCount()==0)
            ivBookBG.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(int pos) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.transferBookDetailFragment(DataManager.sBookHistoryData.get(pos));
    }
}
