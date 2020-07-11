package com.example.mymovieapp.view.fragments.search;

import android.widget.Toast;

import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPresenter implements SearchContract.PresenterInterface {

    private final SearchContract.ViewInterface viewInterface;
    private final MovieRepository movieRepository;

    public SearchPresenter(SearchContract.ViewInterface viewInterface, MovieRepository movieRepository) {
        this.viewInterface = viewInterface;
        this.movieRepository = movieRepository;
    }

    @Override
    public void searchMovies() {
        viewInterface.showLoadingBar();
        movieRepository.searchMovies(viewInterface.textToSearchFor())
                .observe(viewInterface.getViewOwner(), movies ->
                {
                    viewInterface.submitList(movies);
                    viewInterface.hideLoadingBar();
                });
    }

    @Override
    public void searchRandomMovies() {
        viewInterface.showLoadingBar();
        movieRepository.getAllMovies().observe(viewInterface.getViewOwner(), movies ->
        {
            viewInterface.submitList(movies);
            viewInterface.hideLoadingBar();
        });
    }


    @Override
    public void saveMovies(List<Movie> moviesToSave) {

        final List<Movie> checkedMovies = moviesToSave
                .stream()
                .filter(Movie::isWatched)
                .collect(Collectors.toList());

        if (checkedMovies.isEmpty())
            viewInterface.displayErrorMessage("You don't have any checked movies to save");
        else
        {
            checkedMovies.forEach(movieRepository::insert);
           viewInterface.displayMessage("Checked movies were saved!");
        }
    }
}
