package com.example.mymovieapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;

import com.example.mymovieapp.R;
import com.example.mymovieapp.data.repository.MovieRepository;
import com.example.mymovieapp.view.fragments.favorites.FavoritesFragment;
import com.example.mymovieapp.view.fragments.home.HomeFragment;
import com.example.mymovieapp.view.fragments.search.SearchFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainContract.ViewInterface
{
    private DrawerLayout drawerLayout;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();

            navigationView.setCheckedItem(R.id.nav_home);
        }

        setupPresenter();
    }

    private void setupPresenter()
    {
        final MovieRepository movieRepository = new MovieRepository(this.getApplication());
        mainPresenter = new MainPresenter(movieRepository, this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;

            case R.id.nav_favorite:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FavoritesFragment())
                        .commit();
                break;

            case R.id.nav_search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SearchFragment())
                        .commit();
                break;

            case R.id.nav_share:
                mainPresenter.shareAllSavedMovies();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startNewIntent(String moviesToText) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:pele_adriana@yahoo.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Movie List");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi there! Checkout my favorite movie list: " + System.getProperty("line.separator") + moviesToText);
        startActivity(intent);
    }

    @Override
    public LifecycleOwner getLifeCycleOwner() {
        return this;
    }
}
