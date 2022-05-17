package com.example.se_project.Center;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.se_project.R;

import java.util.ArrayList;
import java.util.Locale;

public class Popup_ListViewAdapter extends BaseAdapter  {

    private static final String TAG = "팝업 어뎁터";

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Popup_CenterNameInfo> SearchedList;
    private ArrayList<Popup_CenterNameInfo> DataList;

    public Popup_ListViewAdapter(Context context, ArrayList<Popup_CenterNameInfo> dataList) {
        mContext = context;
        // 인플레이터 초기화인듯
        inflater = LayoutInflater.from(mContext);
        this.SearchedList = new ArrayList<Popup_CenterNameInfo>();
        this.DataList = new ArrayList<Popup_CenterNameInfo>();
        this.DataList.addAll(dataList);
        Log.e(TAG, "ListViewAdapter: " + DataList.size());
    }

    public class ViewHolder {
        TextView itemTextView;
    }

    public View getView(final int position, View itemview, ViewGroup parent) {

        final ViewHolder holder;
        if (itemview == null) {
            holder = new ViewHolder();
            //inflater를 이용하여 리스트 뷰 안에 들어갈 아이템 뷰를 메모리 위에 할당
            itemview = inflater.inflate(R.layout.activity_search_popup_item, null);
            // 아이템뷰에서 TextView를 찾는다
            holder.itemTextView = (TextView) itemview.findViewById(R.id.centerName);
            itemview.setTag(holder);

        } else {
            //이건 뭔지 모르겠넹
            holder = (ViewHolder) itemview.getTag();
        }

        // 검색된 텍스트 List가 있다면 setText를 이용하여 화면에 보여준다
        if(!SearchedList.isEmpty())
        {
            holder.itemTextView.setText(SearchedList.get(position).getCenterName());
        }

        return itemview;
    }

    // 입력된 텍스트가 포함된 data들을 뽑아서 SearchedList 에 넣어줌
    public void filter(String SearchedText) {
        SearchedText = SearchedText.toLowerCase(Locale.getDefault());
        // 새 텍스트가 추가됐으니 일단 기존의 리스트를 비우고 일치하는 단어로 이루어진 리스트를 새로 만든다.\
        // 입력된 텍스트의 길이가 0이면(입력이 안됐으면) 다시 이전의 리스트들을 띄운다.
        if (SearchedText.length() == 0) {
            SearchedList.clear();
            notifyDataSetChanged();
        } else {
            SearchedList.clear();
            for (Popup_CenterNameInfo wp : DataList) {
                if (wp.getCenterName().toLowerCase(Locale.getDefault()).contains(SearchedText)) {
                    SearchedList.add(wp);
                    System.out.println("test : "+wp.getCenterName());
                }
            }
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return SearchedList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Popup_CenterNameInfo getItem(int position) {
        return SearchedList.get(position);
    }

}
