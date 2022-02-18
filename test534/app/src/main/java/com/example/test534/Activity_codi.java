package com.example.test534;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Activity_codi extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView_Similar;
    RecyclerAdapter adapter;
    //ProductAdapter Padapter;
    String melon_chart_url = "https://store.musinsa.com/app/goods/1609490";
    ImageView imageView ;
    TextView txt_ProductTitle, txt_ProductName,txt_ProductPrice,txt_ProductTag;
    String TAG="DONG";
    String DEFAULT_URL="https://store.musinsa.com/app/goods/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);

        Intent intent = getIntent();
        String action =intent.getAction();
        String data = intent.getDataString();
        final String DEFAULT_PATH = "deeplink://fashion/";
        if (action!=null && data != null) {
            if (data.startsWith(DEFAULT_PATH)) {
                String param = data.replace(DEFAULT_PATH, "");
                melon_chart_url= DEFAULT_URL+param;
                Log.w(TAG,param);
            }
            else
            {
                Log.w(TAG,"nonononono");
            }
        }
        txt_ProductTitle=findViewById(R.id.txt_ProductTitle);
        txt_ProductName=findViewById(R.id.txt_ProductName);
        txt_ProductPrice=findViewById(R.id.txt_ProductPrice);
        txt_ProductTag=findViewById(R.id.txt_ProductTag);
        recyclerView = findViewById(R.id.recyclerView_chart);
        //recyclerView_Similar=findViewById(R.id.recyclerView_Similar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        //LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView_Similar.setLayoutManager(linearLayoutManager1);
        adapter = new RecyclerAdapter();
        //Padapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        //recyclerView_Similar.setAdapter(Padapter);

        getData();

    }

    private void getData(){
        MelonJsoup jsoupAsyncTask = new MelonJsoup();
        jsoupAsyncTask.execute();
    }

    private class MelonJsoup extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listTitle = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listTag = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(melon_chart_url).get();


                /*final Elements image_list1 = doc.select("div.wrap_right_area page_detail_product_right_contents related-styling_tabBox-Staff_tab coordi active ul.style_list li.list_item a.img-block img ");
                final Elements  rank_list1 = doc.select("div.wrap_right_area page_detail_product_right_contents related-styling_tabBox-Staff_tab coordi active ul.style_list li.list_item p");

                final Elements rank_list_name = doc.select("div.wrap_right_area page_detail_product_right_contents related-styling_tabBox-Staff_tab coordi active ul.style_list li.list_item h5");*/
                final Elements image_list1 = doc.select("div[class=right_contents related-styling]  ul[class=style_list] li[class=list_item] img");
                final Elements  rank_list1 = doc.select("div[class=right_contents related-styling]  ul[class=style_list] li[class=list_item] h5");
                final Elements rank_list_name = doc.select("div[class=right_contents related-styling]  ul[class=style_list] li[class=list_item] p");

                final Elements productINFO= doc.select("span[class=product_title]");//제품명
                final Elements productBrand= doc.select("div[class=explan_product product_info_section] ul p[class=product_article_contents] a");
                //회사명 및 해시태그
                final Elements productPrice= doc.select("div[class=member_price] ul li ");
                Element eleP = productPrice.select("span[class=txt_price_member]").first();
                //가격

                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Elements productImg = doc.select("div[class=product-img] img"); //제품사진
                        imageView=  findViewById(R.id.txt_ProductImg);
                       // Glide.with(imageView).load("https:"+productImg.attr("src")).error(R.drawable.ic_launcher_background).into(txt_ProductImg);
                        txt_ProductName.setText(productINFO.text());
                        txt_ProductPrice.setText(eleP.text());
                       //
                        int count=0;
                        for (Element element : productBrand){
                            if(count==0){
                                txt_ProductTitle.setText(element.text());}
                            else if(count>1)
                            {
                                txt_ProductTag.setText(element.text());
                            }
                            count++;
                        }
                        for(Element element: rank_list1) {
                            listTitle.add(element.text());
                        }
                        //가수정보
                        for (Element element : rank_list_name) {
                            listName.add(element.text());
                        }
                        // 이미지정보
                        for (Element element : image_list1){
                            listUrl.add("https:"+element.attr("src"));
                        }

                        for (int i = 0; i < listUrl.size() ; i++) {
                            ChartDTO data = new ChartDTO();
                            data.setTitle(listTitle.get(i));
                            data.setImageUrl(listUrl.get(i));
                            data.setName(listName.get(i));

                            adapter.addItem(data);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
