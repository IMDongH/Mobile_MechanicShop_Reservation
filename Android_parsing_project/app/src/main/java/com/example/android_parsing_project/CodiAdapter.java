package com.example.android_parsing_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CodiAdapter extends RecyclerView.Adapter<CodiAdapter.ItemViewHolder> {

    private ArrayList<CodiDTO> listData = new ArrayList<>();

    @NonNull
    @Override
    public CodiAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_codi, viewGroup, false);
        return new
                ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodiAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.onBind(listData.get(i));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    void addItem(CodiDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_ProductBrand, txt_ProductTitle;
        private ImageView Img_ProductImg;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ProductBrand = itemView.findViewById(R.id.txt_ProductBrand);
            txt_ProductTitle = itemView.findViewById(R.id.txt_ProductTitle);
            Img_ProductImg     = itemView.findViewById(R.id.Img_ProductImg);

        }

        void onBind(CodiDTO data){

            txt_ProductBrand.setText(data.getBrand());
            txt_ProductTitle.setText(data.getTitle());
            Glide.with(itemView.getContext()).load(data.getImageUrl()).error(R.drawable.ic_launcher_background).into(Img_ProductImg);

        }
    }
}