package com.example.webpractice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Activity_Item extends AppCompatActivity {

    RecyclerView ItemView;
    RecyclerAdapter ItemAdapter;

    RecyclerView CodiView;
    RecyclerCodiAdapter CodiAdapter;

    RecyclerView SimilarView;
    RecyclerSimilarAdapter SimilarAdapter;

    String Item_url = "https://store.musinsa.com/app/goods/1609490";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_codi);

        ItemView = findViewById(R.id.txt_ItemHashtag);
        LinearLayoutManager ItemManager = new LinearLayoutManager(this);
        ItemView.setLayoutManager(ItemManager);
        ItemAdapter = new RecyclerAdapter();
        ItemView.setAdapter(ItemAdapter);

        CodiView = findViewById(R.id.txt_codi);
        LinearLayoutManager CodiManager = new LinearLayoutManager(this);
        CodiView.setLayoutManager(CodiManager);
        CodiAdapter = new RecyclerCodiAdapter();
        CodiView.setAdapter(CodiAdapter);

        SimilarView = findViewById(R.id.txt_Similar);
        LinearLayoutManager SimilarManager = new LinearLayoutManager(this);
        SimilarView.setLayoutManager(SimilarManager);
        SimilarAdapter = new RecyclerSimilarAdapter();
        SimilarView.setAdapter(SimilarAdapter);

        getData();
    }

    private void getData(){
        ItemJsoup jsoupAsyncTask = new ItemJsoup();
        jsoupAsyncTask.execute();
    }

    private class ItemJsoup extends AsyncTask<Void, Void, Void> {

        ArrayList<String> listHashTag = new ArrayList<>();
        ArrayList<String> listCodi = new ArrayList<>();
        ArrayList<String> listSimilar = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(Item_url).get();
                //final Elements ItemTitle = doc.select("div.wrap_right_area page_detail_product_right_contents section_product_summary span.product_title em");
                final Elements ItemTitle = doc.select("div.product_article_contents span");
                final Elements ItemHashTag= doc.select("div.wrap_right_area page_detail_product_right_contents section_product_summary span.product_title em");
                final Elements ItemPrice = doc.select("div.wrap_right_area page_detail_product_right_contents section_product_summary_warp_product_product_right product_order_info_explan_product price_info_section ul.product_article li.box_info_products div.product_article_contents a.product_article_price span ");
                final Elements ItemImage = doc.select("div.wrap_right_area page_detail_product_right_contents section_product_summary_wrap_product_wrap_product_wrap_product div.product-img img");
                final Elements CodiImage = doc.select("div[class=right_contents related-styling]  ul[class=style_list] li[class=list_item] img");
                final Elements SimilarImage = doc.select("div[class=right_contents related-styling]  ul[class=style_list] li[class=list_item] img");

                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                ItemDTO data = new ItemDTO();
                CodiDTO CodiData = new CodiDTO();
                SimilarDTO SimilarData = new SimilarDTO();

                data.setTitle("test1");
                data.setPrice("test1");
                data.setImageUrl(ItemImage.attr("src"));

                TextView txt_HashTag, txt_ItemPrice, txt_ItemTitle;
                 ImageView img_Item;
                txt_ItemTitle = ItemView.findViewById(R.id.txt_ItemTitle);
                txt_ItemPrice = ItemView.findViewById(R.id.txt_ItemPrice);
                img_Item     = ItemView.findViewById(R.id.img_Item);
                txt_ItemTitle.setText("a");
                Log.v("test",ItemPrice.text()+"helloasdasd");
                txt_ItemPrice.setText(ItemPrice.text());
                ItemAdapter.addItem(data);
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        for (Element element : CodiImage){
                            listCodi.add(element.attr("src"));
                        }

                        for (Element element : SimilarImage){
                            listSimilar.add(element.attr("src"));
                        }
                        for (Element element : ItemHashTag){
                            listHashTag.add(element.text());
                        }

                        for (int i = 0; i < ItemHashTag.size() ; i++) {
                            ItemDTO data = new ItemDTO();
                            data.setHashTag(listHashTag.get(i));
                            ItemAdapter.addItem(data);
                        }
                        for (int i = 0; i < CodiImage.size() ; i++) {
                            CodiDTO data = new CodiDTO();
                            data.setCodiUrl(listCodi.get(i));
                            CodiAdapter.addCodi(data);
                        }
                        for (int i = 0; i < SimilarImage.size() ; i++) {
                            SimilarDTO data = new SimilarDTO();
                            data.setSimilarUrl(listSimilar.get(i));
                            SimilarAdapter.addSimilar(data);
                        }


                        ItemAdapter.notifyDataSetChanged();
                        CodiAdapter.notifyDataSetChanged();
                        SimilarAdapter.notifyDataSetChanged();

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
