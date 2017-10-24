package com.datviet.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.History;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.datviet.utils.SpacingItemDecoration;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HistoryFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    History history;
    ArrayList<History> arrayList;
    private static HistoryFragment fragment;

    private DatabaseReference mData;



    public static HistoryFragment newInstance() {
        if (fragment == null) fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.history_layout, container, false);
        recyclerView = (RecyclerView) vg.findViewById(R.id.recycler_view);

        //firebase();

        mAdapter = new HistoryAdapter(DataManager.sHistoryData);
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
                    mAdapter.notifyItemRemoved(position);
                    DataManager.sHistoryData.remove(position);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

       prepareHistoryData();
        return vg;
    }


//    private void firebase(){
//        mData = FirebaseDatabase.getInstance().getReference();
//        arrayList = new ArrayList<History>();
//        history = new History();
//
//    }


    private void prepareHistoryData() {
        if (DataManager.sHistoryData.size() == 0) {
            DataManager.sHistoryData.add(new History( "11111981"));
            DataManager.sHistoryData.add(new History( "33322456"));
            DataManager.sHistoryData.add(new History( "77555221"));
        }
        mAdapter.notifyDataSetChanged();
}

//    private void loadFireBase(){
//        prepareHistoryData();
//        mData.child("history").push().setValue(DataManager.sHistoryData);
//
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
//
//            }
//        });
//    }

//    private void createData(){
//        arrayList.add(new History(history.Code));
//        mAdapter.notifyDataSetChanged();
//   }
}
