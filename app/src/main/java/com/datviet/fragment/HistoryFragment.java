package com.datviet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    TextView tv;
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
        tv = (TextView) viewGroup.findViewById(R.id.tv);

        String data = getArguments().getString("data"); // đây là thứ bạn cần :D
        tv.setText(data);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogStyle);
                    builder.setMessage("Bạn có chắc chắn muốn xóa ?");

                    builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.removeItem(position);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String link = bundle.getString("url");
            tv.setText(link);
        }
    }

    //    private void prepareHistoryData() {
//        if (DataManager.sHistoryData.size() == 0) {
//            DataManager.sHistoryData.add(new History("11111981", "22-10-2014,16:08"));
//            DataManager.sHistoryData.add(new History("33322456", "4-6-2015,18:01"));
//            DataManager.sHistoryData.add(new History("77555221", "1-11-2017,7:33"));
//        }
//        mAdapter.notifyDataSetChanged();
//    }

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
