package com.example.mymovieapp.view.fragments.details;

import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;

public class DetailsPresenter implements DetailsContract.PresenterInterface
{
    private final DetailsContract.ViewInterface viewInterface;
    private final MovieRepository movieRepository;

    public DetailsPresenter(DetailsContract.ViewInterface viewInterface, MovieRepository movieRepository) {
        this.viewInterface = viewInterface;
        this.movieRepository = movieRepository;
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
        viewInterface.displayMessage("Movie deleted successfully!");
    }

    @Override
    public Movie loadMovie(int movieId) {
        final Movie loadedMovie = movieRepository.getMovieById(movieId);
        if (loadedMovie == null)
            viewInterface.displayError("Movie could not be loaded");

        return loadedMovie;
    }
}
