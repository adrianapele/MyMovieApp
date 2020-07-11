package com.example.mymovieapp.view.fragments.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mymovieapp.R;
import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;
import com.example.mymovieapp.view.adapters.SearchAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SearchFragment extends Fragment implements SearchContract.ViewInterface {

    public static final String TAG = "searchFragment";

    private SearchPresenter searchPresenter;

    private ProgressBar progressBar;
    private RecyclerView myRecyclerView;
    private SearchAdapter searchAdapter;
    private EditText searchEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieRepository movieRepository = new MovieRepository(getActivity().getApplication());
        searchPresenter = new SearchPresenter(this, movieRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        final ImageView searchIcon = rootView.findViewById(R.id.searchImageViewId);
        searchIcon.setOnClickListener(v -> searchPresenter.searchMovies());
        searchEditText = rootView.findViewById(R.id.searchMovieEditTextId);

        myRecyclerView = rootView.findViewById(R.id.recyclerViewId);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setHasFixedSize(true);

        searchAdapter = new SearchAdapter();
        myRecyclerView.setAdapter(searchAdapter);

        final FloatingActionButton saveFab = rootView.findViewById(R.id.floatingActionBtnId);
        saveFab.setOnClickListener(v -> searchPresenter.saveMovies(searchAdapter.getCurrentList()));

        progressBar = rootView.findViewById(R.id.progressBarId);

        searchPresenter.searchRandomMovies();

        getActivity().setTitle("Search Movie");

        return rootView;
    }

    @Override
    public void showLoadingBar() {
        progressBar.setVisibility(View.VISIBLE);
        myRecyclerView.setEnabled(false);
    }

    @Override
    public void hideLoadingBar() {
        progressBar.setVisibility(View.GONE);
        myRecyclerView.setEnabled(true);
    }

    @Override
    public String textToSearchFor() {
        return searchEditText.getText().toString();
    }

    @Override
    public void submitList(List<Movie> listForAdapter) {
        searchAdapter.submitList(listForAdapter);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LifecycleOwner getViewOwner() {
        return getViewLifecycleOwner();
    }
}