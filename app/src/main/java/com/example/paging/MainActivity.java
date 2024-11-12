package com.example.paging;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paging.adapter.MovieAdapter;
import com.example.paging.datasource.MovieRepository;
import com.example.paging.retrofit.RetrofitAPI;
import com.example.paging.utils.Constants;
import com.example.paging.viewmodel.MovieViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MovieAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        RetrofitAPI apiService = RetrofitAPI.Factory.create();
        MovieRepository repository = new MovieRepository(apiService, Constants.API_KEY);
        MovieViewModel.Factory factory = new MovieViewModel.Factory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        disposable.add(viewModel.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pagingData -> {
                    adapter.submitData(getLifecycle(), pagingData);
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}