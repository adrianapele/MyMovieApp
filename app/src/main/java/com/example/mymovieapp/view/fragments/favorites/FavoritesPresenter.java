package com.example.mymovieapp.view.fragments.favorites;

import androidx.lifecycle.LiveData;

import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;

import java.util.List;

public class FavoritesPresenter implements FavoritesContract.PresenterInterface {

    private final FavoritesContract.ViewInterface viewInterface;
    private final MovieRepository movieRepository;

    public FavoritesPresenter(FavoritesContract.ViewInterface viewInterface, MovieRepository movieRepository) {
        this.viewInterface = viewInterface;
        this.movieRepository = movieRepository;
    }


    @Override
    public LiveData<List<Movie>> loadSavedMovies() {
       return movieRepository.getAllSavedMovies();
    }

    @Override
    public void deleteAllMovies() {
        movieRepository.deleteAllSavedMovies();
        viewInterface.displayMessage("All saved movies were deleted");
    }

    @Override
    public boolean canDeleteAllMovies() {
        final List<Movie> movies = loadSavedMovies().getValue();

        if (movies == null || movies.isEmpty())
        {
            viewInterface.displayErrorMessage("You don't have saved movies to delete");
            return false;
        }

        return true;
    }
}
