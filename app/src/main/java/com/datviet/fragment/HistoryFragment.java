package com.datviet.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.History;
import com.datviet.scanner.Common;
import com.datviet.scanner.MainActivity;
import com.datviet.scanner.R;
import com.datviet.utils.DataManager;
import com.datviet.utils.SpacingItemDecoration;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends android.support.v4.app.Fragment implements HistoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    History history;
    ArrayList<History> arrayList;
    private static HistoryFragment fragment;
    ImageView ivBook;
    TextView tv;

    private DatabaseReference mData;

    public HistoryFragment() {
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
        tv = (TextView) viewGroup.findViewById(R.id.tv);

        mAdapter = new HistoryAdapter(DataManager.sHistoryData, this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                    builder.setMessage("Bạn có chắc chắn muốn xóa ?");

                    builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.removeItem(position);
                            Common.saveData();
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(mAdapter);
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

        //prepareHistoryData();

        return viewGroup;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    private void prepareHistoryData() {
           DataManager.sHistoryData.add(new History("111222","22-11-2017,6:77"));
           DataManager.sHistoryData.add(new History("114443222","22-11-2017,6:77"));
           DataManager.sHistoryData.add(new History("11142222","22-11-2017,6:77"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int pos) {
        Toast.makeText(getContext(), "Clicked " + pos, Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.addFragmentDetail(DataManager.sHistoryData.get(pos));
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
