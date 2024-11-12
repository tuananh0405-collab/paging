package com.example.paging.datasource;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.paging.model.Movie;
import com.example.paging.retrofit.RetrofitAPI;

import io.reactivex.rxjava3.core.Flowable;

public class MovieRepository {
    private final RetrofitAPI retrofitAPI;
    private final String apiKey;

    public MovieRepository(RetrofitAPI apiService, String apiKey) {
        this.retrofitAPI = apiService;
        this.apiKey = apiKey;
    }

    public Flowable<PagingData<Movie>> getMovies() {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(20),
                        () -> new MovieDataSource(retrofitAPI, apiKey)
                )
        );
    }
}
