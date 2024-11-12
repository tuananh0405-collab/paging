package com.example.paging.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;

import com.example.paging.datasource.MovieRepository;
import com.example.paging.model.Movie;

import io.reactivex.rxjava3.core.Flowable;

public class MovieViewModel extends ViewModel{
    private MovieRepository repository;

    public MovieViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public Flowable<PagingData<Movie>> getMovies() {
        return repository.getMovies();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final MovieRepository repository;

        public Factory(MovieRepository repository) {
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MovieViewModel.class)) {
                return (T) new MovieViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
