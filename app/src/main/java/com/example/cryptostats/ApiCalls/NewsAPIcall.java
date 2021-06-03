package com.example.cryptostats.ApiCalls;


import com.example.cryptostats.Model.News.Articles;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPIcall {



    @GET("ADDRESS")
//    Call<String> STRING_CALL();
    Call<Articles> getData2();


}


