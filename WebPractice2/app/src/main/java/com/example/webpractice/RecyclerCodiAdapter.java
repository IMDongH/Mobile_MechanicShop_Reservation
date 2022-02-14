package com.example.webpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerCodiAdapter extends RecyclerView.Adapter<RecyclerCodiAdapter.ItemViewHolder> {

    private ArrayList<ItemDTO> listData = new ArrayList<>();
    private ArrayList<CodiDTO> listCodi = new ArrayList<>();
    private ArrayList<SimilarDTO> listSimilar = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerCodiAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCodiAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.onBind(listCodi.get(i));
    }

    @Override
    public int getItemCount() {
        return listCodi.size();
    }

    void addItem(ItemDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    void addCodi(CodiDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listCodi.add(data);
    }
    void addSimilar(SimilarDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listSimilar.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_Spec, txt_ItemTitle;
        private ImageView img_Item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ItemTitle = itemView.findViewById(R.id.txt_ItemTitle);
            //txt_Spec = itemView.findViewById(R.id.txt_CodiItemSpec);
            img_Item     = itemView.findViewById(R.id.img_Item);

        }

        void onBind(CodiDTO data){
            txt_Spec.setText(data.getSpec());
            txt_ItemTitle.setText(data.getTitle());

            Glide.with(itemView.getContext()).load(data.getCodiUrl()).into(img_Item);

        }
    }
}
