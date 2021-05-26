package com.example.cryptostats;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class MainActivity<pagecount> extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataModel> datalist;
    Adapter adapter;
    Context c;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;

    int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        progressBar = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested);

        progressBar.setVisibility(View.GONE);
        datalist = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        initrecyclerview();
        getData1(1);
        setUpPagination(true);
        swiperefresh();


    }

    private void setUpPagination(boolean isapplicable) {

        if(isapplicable){
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){


                        page++;
                        progressBar.setVisibility(View.VISIBLE);
                        getData1(page);
                        Toast toast = Toast.makeText(getApplicationContext(),"Loading Data, please wait!" + "Count = "+ page,Toast.LENGTH_LONG);
                        toast.show();
//                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }


    private void initrecyclerview() {
        adapter = new Adapter(getApplicationContext(), datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void getData1(int pg) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nomics.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MyAPIcall myAPIcall = retrofit.create(MyAPIcall.class);


        Call<List<DataModel>> call = myAPIcall.getData(pg);


        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {

                if(response.code() != 200 && response.body() != null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Data Fetching Failure! :( Try after some time",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    List<DataModel> dm = response.body();
                    datalist.addAll(dm);
                    initrecyclerview();
                }
            }
            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TAG", "Failed!!!");
            }
        });


    }


    private void swiperefresh(){
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datalist.clear();
                Log.d("TAG","Datalist Cleared!!");
                getData1(1);
                Log.d("TAG","getData1() executed!!!");
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                page = 1;
            }
        });
    }

}
