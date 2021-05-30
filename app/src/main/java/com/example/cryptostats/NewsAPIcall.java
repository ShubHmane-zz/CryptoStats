package com.example.cryptostats;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPIcall {



    @GET("YOUR_URL")
//    Call<String> STRING_CALL();
    Call<List<NewsModel>> getData2();


}


