package com.emedinaa.appasync;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.emedinaa.appasync.model.JsonData;
import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.ui.BaseActivity;
import com.emedinaa.appasync.ui.adapter.MovieAdapter;
import com.google.common.collect.ImmutableList;

public class MainUIActivity extends BaseActivity {

    private final int SPAN_COUNT = 2;

    private RecyclerView recyclerViewMovies;
    private StaggeredGridLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    private ImmutableList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ui();
        loadMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderThreads();
    }


    private void ui() {
        progressView = findViewById(R.id.frameLayoutProgress);
        recyclerViewMovies = (RecyclerView) findViewById(R.id.recyclerViewMovies);
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        recyclerViewMovies.setLayoutManager(gridLayoutManager);
        textViewThread = (TextView) findViewById(R.id.textViewThread);
    }

    private void loadMovies() {
        MovieJsonData jsonData = null;
        try {
            //jsonData = assetJsonHelper.convertObjectToJsonAssets("movies.json", MovieJsonData.class);
            jsonData = assetJsonHelper.convertObjectToJsonAssets("heavy_movies.json", MovieJsonData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonData != null && jsonData.getData() != null) {
            ImmutableList<Movie> mMovies = ImmutableList.copyOf(jsonData.getData());
            renderMovies(mMovies);
        }
    }

    public void renderMovies(ImmutableList<Movie> movies) {
        MovieAdapter movieAdapter = new MovieAdapter(movies);
        recyclerViewMovies.setAdapter(movieAdapter);
    }


    private class MovieJsonData extends JsonData<Movie> {}
}
