package com.example.mymovieapp.view;

import com.example.mymovieapp.data.model.Movie;

public interface MainContract {

    interface PresenterInterface
    {
        void shareAllSavedMovies();
    }

    interface ViewInterface
    {
        void displayMessage(String message);
        void startNewIntent(String moviesToText);
    }
}
