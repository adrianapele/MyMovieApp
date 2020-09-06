package com.example.mymovieapp.view;

import androidx.lifecycle.LifecycleOwner;

public interface MainContract {

    interface PresenterInterface
    {
        void shareAllSavedMovies();
    }

    interface ViewInterface
    {
        void displayMessage(String message);
        void startNewIntent(String moviesToText);
        LifecycleOwner getLifeCycleOwner();
    }
}
