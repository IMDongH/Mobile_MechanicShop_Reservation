package com.example.se_project.User.Search;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.se_project.R;

import java.util.ArrayList;
import java.util.Locale;

public class SearchListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<SearchTitleClass> titlesList;
    private ArrayList<SearchTitleClass> arraylist;
    RelativeLayout itemList;

    public SearchListViewAdapter(Context context, ArrayList<SearchTitleClass> titlesList) {
        mContext = context;
        this.titlesList = new ArrayList<SearchTitleClass>();
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchTitleClass>();
        this.arraylist.addAll(titlesList);
        Log.e("adapter", "ListViewAdapter: " + arraylist.size());
    }

    public class ViewHolder {
        TextView Name;
        TextView Location;
    }

    @Override
    public int getCount() {

        return titlesList.size();
    }

    @Override
    public SearchTitleClass getItem(int position) {
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
            view = inflater.inflate(R.layout.activity_search_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.Name = (TextView) view.findViewById(R.id.Name_name);
            holder.Location = (TextView) view.findViewById(R.id.Location_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        System.out.println("HOLDER : "+titlesList.get(position).getName()+titlesList.get(position).getLocation());
        if(!titlesList.isEmpty())
        {

            holder.Name.setText(titlesList.get(position).getName());
            holder.Location.setText(titlesList.get(position).getLocation());
        }
        itemList = (RelativeLayout) view.findViewById(R.id.search_item);
        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        // 새 텍스트가 추가됐으니 일단 기존의 리스트를 비우고 일치하는 단어로 이루어진 리스트를 새로 만든다.

        // 입력된 텍스트의 길이가 0이면(입력이 안됐으면) 다시 이전의 리스트들을 띄운다.
        if (charText.length() == 0) {
            titlesList.clear();
            notifyDataSetChanged();
        } else {
            titlesList.clear();
            for (SearchTitleClass wp : arraylist) {
                if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    titlesList.add(wp);
                    System.out.println("test : "+wp.getName()+wp.getLocation());
                }
            }
            notifyDataSetChanged();
        }

    }
}

