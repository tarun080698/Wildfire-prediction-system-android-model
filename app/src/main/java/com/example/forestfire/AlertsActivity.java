package com.example.forestfire;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.forestfire.MyHelper.COLUMN_INTENSITY;


public class AlertsActivity extends AppCompatActivity {


    MyHelper helper;
    public SQLiteDatabase databaseWrite;
    RecyclerView recyclerView;
    MyAlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        helper = new MyHelper(this);
        databaseWrite = helper.getWritableDatabase();

        recyclerView = findViewById(R.id.logs_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        recyclerView.setItemAnimator(itemAnimator);
        layoutManager.setReverseLayout(true);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        alertAdapter = new MyAlertAdapter(this, getAllItems());
        recyclerView.setAdapter(alertAdapter);
        recyclerView.scrollToPosition(alertAdapter.getItemCount() - 1);
        alertAdapter.swapCursor(getAllItems());

    }
    private Cursor getAllItems() {
        return databaseWrite.query(
                MyHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_INTENSITY + " DESC");
    }
}