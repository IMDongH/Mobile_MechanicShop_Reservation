package com.example.se_project.Center;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.se_project.R;

import java.util.ArrayList;
import java.util.Locale;

public class ReservationAdapter extends BaseAdapter  {

    private static final String TAG = "예약관 어뎁터";

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Reservation_Info> DataList;
    CardView itemList;

    public ReservationAdapter(Context context, ArrayList<Reservation_Info> dataList) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.DataList = new ArrayList<Reservation_Info>();
        this.DataList.addAll(dataList);
        Log.e(TAG, "ListViewAdapter: " + DataList.size());
    }

    public View getView(final int position, View itemview, ViewGroup parent) {

        View view = inflater.inflate(R.layout.activity_center_reservation_items, null);

        TextView name = (TextView)view.findViewById(R.id.name);
        TextView phone = (TextView)view.findViewById(R.id.phone);
        TextView time = (TextView)view.findViewById(R.id.time);
        TextView type = (TextView)view.findViewById(R.id.type);
        TextView why = (TextView)view.findViewById(R.id.why);
        TextView date = (TextView)view.findViewById(R.id.date);

        name.setText(DataList.get(position).getName());
        phone.setText(DataList.get(position).getPhone());
        time.setText(DataList.get(position).getTime());
        type.setText(DataList.get(position).getType());
        why.setText(DataList.get(position).getWhy());
        date.setText(DataList.get(position).getDate());

        itemList = (CardView) view.findViewById(R.id.reservation_item);
        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("CLICK : "+ DataList.get(position).getName());
                Toast.makeText(mContext, DataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteItem(int position)
    {
        DataList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public Reservation_Info getItem(int position) {
        return DataList.get(position);
    }


}
