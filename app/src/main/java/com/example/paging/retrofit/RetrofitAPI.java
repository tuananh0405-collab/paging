package com.example.paging.retrofit;

import com.example.paging.model.response.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("movie/popular")
    Single<MovieResponse> getMovies(@Query("api_key") String apiKey, @Query("page") int page);

    class Factory{
        public static RetrofitAPI create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            return retrofit.create(RetrofitAPI.class);
        }
    }
}
