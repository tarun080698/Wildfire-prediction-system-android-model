package com.example.forestfire;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyAlertAdapter extends RecyclerView.Adapter<MyAlertAdapter.AlertViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    MyAlertAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new AlertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String date = mCursor.getString(mCursor.getColumnIndex(MyHelper.COLUMN_D_T));
        double temp = mCursor.getDouble(mCursor.getColumnIndex(MyHelper.COLUMN_TEMP));
        double humidity = mCursor.getDouble(mCursor.getColumnIndex(MyHelper.COLUMN_HUMIDITY));
        double moist = mCursor.getDouble(mCursor.getColumnIndex(MyHelper.COLUMN_SM));
        double pressure = mCursor.getDouble(mCursor.getColumnIndex(MyHelper.COLUMN_ATM_P));
        double alt = mCursor.getDouble(mCursor.getColumnIndex(MyHelper.COLUMN_ALT));
        String result = mCursor.getString(mCursor.getColumnIndex(MyHelper.COLUMN_INTENSITY_RES));

        holder.date.setText(date);
        holder.temp.setText(String.valueOf(temp));
        holder.humid.setText(String.valueOf(humidity));
        holder.soilMoist.setText(String.valueOf(moist));
        holder.alt.setText(String.valueOf(pressure));
        holder.atm.setText(String.valueOf(alt));
        holder.result.setText(result);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    static class AlertViewHolder extends RecyclerView.ViewHolder {

        TextView date, temp, humid, soilMoist, alt, atm, result;

        AlertViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            humid = itemView.findViewById(R.id.humidity);
            soilMoist = itemView.findViewById(R.id.soil_moist);
            alt = itemView.findViewById(R.id.alt);
            atm = itemView.findViewById(R.id.atm);
            result = itemView.findViewById(R.id.results);

        }
    }
}
