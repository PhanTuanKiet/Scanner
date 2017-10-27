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
import android.widget.Toast;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.History;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.datviet.utils.SpacingItemDecoration;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class HistoryFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    History history;
    ArrayList<History> arrayList;
    private static HistoryFragment fragment;
    ImageView ivBook;
    private final HistoryAdapter.OnItemClickListener listener;

    private DatabaseReference mData;

    public HistoryFragment() {
        listener = null;
    }


    public static HistoryFragment newInstance() {
        if (fragment == null) fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.history_layout, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);

        //mData = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<History>();
        history = new History();

        ivBook = (ImageView) viewGroup.findViewById(R.id.ivBookIcon);

        mAdapter = new HistoryAdapter(DataManager.sHistoryData, listener);
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

                if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure delete this entry?");

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.removeItem(position);
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        prepareHistoryData();

        recyclerView.setAdapter(new HistoryAdapter(DataManager.sHistoryData, new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getContext(), "Clicked " + pos, Toast.LENGTH_SHORT).show();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.addFragmentDetail(DataManager.sHistoryData.get(pos));
            }
        }));


        return viewGroup;
    }


    private void prepareHistoryData() {
        if (DataManager.sHistoryData.size() == 0) {
            DataManager.sHistoryData.add(new History("11111981", "22-10-2014,16:08"));
            DataManager.sHistoryData.add(new History("33322456", "4-6-2015,18:01"));
            DataManager.sHistoryData.add(new History("77555221", "1-11-2017,7:33"));
        }
        mAdapter.notifyDataSetChanged();
    }

//    private void loadFireBase(){
//        prepareHistoryData();
//        mData.child("history").push().setValue(DataManager.sHistoryData);
//    }
//    private void realtimeFirebase(){
//
//        mData.child("history").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                history = dataSnapshot.getValue(History.class);
//                createData();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {

//            }
//        });
//    }

//    private void createData(){
//        arrayList.add(new History(history.Code));
//        mAdapter.notifyDataSetChanged();
//   }
}
