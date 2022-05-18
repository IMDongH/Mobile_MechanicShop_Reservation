package com.example.se_project.User.Reservation;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.se_project.R;
import com.example.se_project.User.Search.SearchTitleClass;

import java.util.ArrayList;
import java.util.Locale;

public class ReservationListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    public ArrayList<ReservationListClass> titlesList;
    public ArrayList<ReservationListClass> arraylist;

    public ReservationListViewAdapter(Context context, ArrayList<ReservationListClass> titlesList) {
        mContext = context;
        this.titlesList = new ArrayList<ReservationListClass>();
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ReservationListClass>();
        this.arraylist.addAll(titlesList);
        Log.e("adapter", "ListViewAdapter: " + arraylist.size());
    }

    public class ViewHolder {
        TextView Date;
        TextView Name;
        TextView Location;
    }

    @Override
    public int getCount() {

        return titlesList.size();
    }

    @Override
    public ReservationListClass getItem(int position) {
        return titlesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_user_reservation_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.Date = (TextView) view.findViewById(R.id.time_value);
            holder.Name = (TextView) view.findViewById(R.id.name_name);
            holder.Location = (TextView) view.findViewById(R.id.Location_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        System.out.println("HOLDER : "+titlesList.get(position).getDate()+titlesList.get(position).getName()+titlesList.get(position).getLocation());
        if(!titlesList.isEmpty())
        {
            holder.Date.setText(titlesList.get(position).getDate());
            holder.Name.setText(titlesList.get(position).getName());
            holder.Location.setText(titlesList.get(position).getLocation());
        }

        return view;
    }

    public void addItem()
    {
        titlesList.clear();
        for (ReservationListClass wp : arraylist) {

                titlesList.add(wp);
                System.out.println("test : "+wp.getDate()+wp.getName()+wp.getLocation());

        }
        notifyDataSetChanged();
    }
    public void deleteItem(int position)
    {
        titlesList.remove(position);
        arraylist.remove(position);
        notifyDataSetChanged();
    }

    private void StartToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
   }

