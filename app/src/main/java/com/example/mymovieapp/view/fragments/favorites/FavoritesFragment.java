package com.example.mymovieapp.view.fragments.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mymovieapp.R;
import com.example.mymovieapp.data.model.Movie;
import com.example.mymovieapp.data.repository.MovieRepository;
import com.example.mymovieapp.view.MyRecyclerView;
import com.example.mymovieapp.view.adapters.FavoritesAdapter;
import com.example.mymovieapp.view.fragments.details.DetailsFragment;

public class FavoritesFragment extends Fragment implements FavoritesContract.ViewInterface, FavoritesAdapter.RecyclerViewClickListener {

    public static final String TAG = "favoritesFragmentTag";

    private FavoritesPresenter favoritesPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setupPresenter();
    }

    private void setupPresenter()
    {
        MovieRepository movieRepository = new MovieRepository(getActivity().getApplication());
        favoritesPresenter = new FavoritesPresenter(this, movieRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        MyRecyclerView recyclerView = rootView.findViewById(R.id.favoritesRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        final RelativeLayout emptyView = rootView.findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);

        FavoritesAdapter adapter = new FavoritesAdapter();
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(adapter);

        favoritesPresenter
                .loadSavedMovies()
                .observe(getViewLifecycleOwner(), adapter::submitList);

        getActivity().setTitle("Favorites");

        return rootView;
    }

    @Override
    public void openDetailsFragment(View view, Movie movie) {

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment detailsFragment = fragmentManager.findFragmentByTag(DetailsFragment.DETAILS_FRAGMENT_TAG);

        if (detailsFragment == null)
            detailsFragment = DetailsFragment.newInstance(movie.getId());

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, detailsFragment, DetailsFragment.DETAILS_FRAGMENT_TAG)
                .addToBackStack(DetailsFragment.DETAILS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerViewItemClick(View view, Movie movie) {
        openDetailsFragment(view, movie);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.delete_all_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.delete_all_favorites_movie)
        {
            if (favoritesPresenter.canDeleteAllMovies())
                favoritesPresenter.deleteAllMovies();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}