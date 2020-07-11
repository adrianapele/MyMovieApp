package com.example.mymovieapp.view.fragments.search;

import androidx.lifecycle.LifecycleOwner;

import com.example.mymovieapp.data.model.Movie;

import java.util.List;
import java.util.ListIterator;

public class SearchContract {

    interface PresenterInterface {
        void searchMovies();
        void searchRandomMovies();

        void saveMovies(List<Movie> moviesToSave);
    }

    interface ViewInterface {
        void showLoadingBar();
        void hideLoadingBar();

        String textToSearchFor();
        void submitList(List<Movie> listForAdapter);

        LifecycleOwner getViewOwner();

        void displayMessage(String message);
        void displayErrorMessage(String errorMessage);
    }
}
