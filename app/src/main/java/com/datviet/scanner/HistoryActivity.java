package com.datviet.scanner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.datviet.adapter.HistoryAdapter;
import com.datviet.model.History;
import com.datviet.utils.DataManager;
import com.datviet.utils.SpacingItemDecoration;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private ImageView bookBackground;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        mAdapter = new HistoryAdapter(DataManager.sHistoryData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                    builder.setMessage("Are you sure to delete?");

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position);
                            DataManager.sHistoryData.remove(position);

                            return;
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position + 1);
                            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                            return;
                        }
                    }).show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        prepareHistoryData();

        if (mAdapter.getItemCount() != 0 ){
            bookBackground.setVisibility(View.INVISIBLE);
        }

    }

    private void prepareHistoryData() {
        History history = new History("11111981");
        DataManager.sHistoryData.add(history);

        history = new History("33322456");
        DataManager.sHistoryData.add(history);

        history = new History("77555221");
        DataManager.sHistoryData.add(history);

        history = new History("6650221");
        DataManager.sHistoryData.add(history);

        mAdapter.notifyDataSetChanged();
    }
}
