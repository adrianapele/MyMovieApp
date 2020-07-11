package com.example.mymovieapp.view.fragments.details;

import com.example.mymovieapp.data.model.Movie;

public interface DetailsContract {

    interface PresenterInterface
    {
        void deleteMovie(Movie movie);
        Movie loadMovie(int movieId);
    }

    interface ViewInterface
    {
        void displayMessage(String message);
        void displayError(String errorMessage);
    }
}
