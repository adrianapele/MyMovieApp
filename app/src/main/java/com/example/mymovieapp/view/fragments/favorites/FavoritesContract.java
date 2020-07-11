package com.example.mymovieapp.view.fragments.favorites;

import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.mymovieapp.data.model.Movie;

import java.util.List;

public class FavoritesContract {

    interface PresenterInterface {
        LiveData<List<Movie>> loadSavedMovies();
        void deleteAllMovies();
        boolean canDeleteAllMovies();
    }

    interface ViewInterface {
        void openDetailsFragment(View view, Movie movie);
        void displayMessage(String message);
        void displayErrorMessage(String errorMessage);
    }
}
