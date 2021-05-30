package com.example.cryptostats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        progressBar = findViewById(R.id.progress_bar1);
        nestedScrollView = findViewById(R.id.nested1);

        progressBar.setVisibility(View.GONE);
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


        Call<List<NewsModel>> call = myNewsAPIcall.getData2();


        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {

                if(response.code() != 200 && response.body() != null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Fetching Failure! :( Try after some time",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    List<NewsModel> ndm = response.body();
                    newsdatalist.addAll(ndm);
                    initrecyclerview();
                }
            }
            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Failed!!!");
            }
        });


    }



}