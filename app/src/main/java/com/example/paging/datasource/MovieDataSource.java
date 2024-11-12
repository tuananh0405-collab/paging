package com.example.paging.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.example.paging.model.Movie;
import com.example.paging.retrofit.RetrofitAPI;


public class MovieDataSource extends RxPagingSource<Integer, Movie> {
    private final RetrofitAPI retrofitAPI;
    private final String apiKey;

    public MovieDataSource(RetrofitAPI retrofitAPI, String apiKey) {
        this.retrofitAPI= retrofitAPI;
        this.apiKey = apiKey;
    }
    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
        return retrofitAPI.getMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    return new LoadResult.Page<>(
                            response.getMovies(),
                            page == 1 ? null : page - 1,
                            page < response.getTotalPages() ? page + 1 : null
                    );
                });
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return pagingState.getAnchorPosition();
    }
}
