package com.example.mymovieapp.view;

import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MainPresenter implements MainContract.PresenterInterface {

    private MovieRepository movieRepository;
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MovieRepository movieRepository, MainContract.ViewInterface viewInterface) {
        this.movieRepository = movieRepository;
        this.viewInterface = viewInterface;
    }

    @Override
    public void shareAllSavedMovies() {
        movieRepository.getAllSavedMovies().observe(viewInterface.getLifeCycleOwner(), movies -> {
            if (movies == null || movies.size() == 0)
                viewInterface.displayMessage("You don't have saved movies to share");
            else {
                final String savedMoviesToText = movies
                        .stream()
                        .map(Movie::toText)
                        .collect(Collectors.joining(System.getProperty("line.separator")));

                viewInterface.startNewIntent(savedMoviesToText);
            }
        });
    }
}
