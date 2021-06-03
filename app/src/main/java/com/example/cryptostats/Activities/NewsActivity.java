package com.example.cryptostats.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptostats.Adapters.NewsAdapter;
import com.example.cryptostats.ApiCalls.NewsAPIcall;
import com.example.cryptostats.Model.News.Articles;
import com.example.cryptostats.Model.News.NewsModel;
import com.example.cryptostats.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<NewsModel> newsdatalist;
    NewsAdapter newsadapter;
//    NestedScrollView nestedScrollView;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


//        progressBar = findViewById(R.id.progress_bar1);
//        nestedScrollView = findViewById(R.id.nested1);

//        progressBar.setVisibility(View.GONE);
        newsdatalist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);


        initrecyclerview();
        getData2();

    }

    private void initrecyclerview() {
        newsadapter = new NewsAdapter(getApplicationContext(), newsdatalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(newsadapter);
    }


    public void getData2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        NewsAPIcall myNewsAPIcall = retrofit.create(NewsAPIcall.class);


        Call<Articles> call = myNewsAPIcall.getData2();


        call.enqueue(new Callback <Articles>() {
            @Override
            public void onResponse(Call <Articles> call, Response <Articles> response) {
                Log.d("TAG", "Inside !!!!!!!!!!!!!!!!!!!!!");


                if(response.code() != 200 && response.body() != null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Fetching Failure! :( Try after some time",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    List<NewsModel> ndm = response.body().getArticles();
                newsdatalist.addAll(ndm);
                    initrecyclerview();
                }
            }
            @Override
            public void onFailure(Call <Articles> call, Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Failed!!!");
            }
        });


    }



}