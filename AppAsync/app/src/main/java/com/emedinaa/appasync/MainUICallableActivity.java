package com.emedinaa.appasync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.model.MovieJsonData;
import com.emedinaa.appasync.ui.adapter.MovieAdapter;
import com.emedinaa.appasync.utils.AssetJsonHelper;
import com.google.common.collect.ImmutableList;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.android.AndroidDeferredManager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainUICallableActivity extends AppCompatActivity {

    private static final int NUMBER_OF_CPUS = Runtime.getRuntime().availableProcessors();

    private View frameLayoutProgress;
    private AndroidDeferredManager androidDeferredManager;
    private final int SPAN_COUNT = 2;

    private RecyclerView recyclerViewMovies;
    private GridLayoutManager gridLayoutManager;
    private ImmutableList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ui();
        app();
    }

    private void loadMoviesJson() {
        showLoading();
        androidDeferredManager.when(new Callable<ImmutableList<Movie>>() {
            @Override
            public ImmutableList<Movie> call() throws Exception {
                MovieJsonData jsonData = null;
                ImmutableList<Movie> mMovies = null;
                jsonData = new AssetJsonHelper(getAssets()).convertObjectToJsonAssets("heavy_movies.json", MovieJsonData.class);

                if (jsonData != null && jsonData.getData() != null) {
                    mMovies = ImmutableList.copyOf(jsonData.getData());
                }
                return mMovies;
            }
        }).done(new DoneCallback<ImmutableList<Movie>>() {
            @Override
            public void onDone(ImmutableList<Movie> result) {
                hideLoading();
                Toast.makeText(MainUICallableActivity.this, "Complete !", Toast.LENGTH_SHORT).show();
                renderMovies(result);
            }
        }).fail(new FailCallback<Throwable>() {
            @Override
            public void onFail(Throwable result) {
                hideLoading();
                Toast.makeText(MainUICallableActivity.this, "Error ! "+result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void renderMovies(ImmutableList<Movie> result) {
        this.movies= result;
        MovieAdapter movieAdapter = new MovieAdapter(movies);
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    private void ui() {
        frameLayoutProgress= findViewById(R.id.frameLayoutProgress);
        recyclerViewMovies = (RecyclerView) findViewById(R.id.recyclerViewMovies);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        recyclerViewMovies.setLayoutManager(gridLayoutManager);
    }

    private void app() {

        final ExecutorService mExecutorService;
        mExecutorService = Executors.newFixedThreadPool(NUMBER_OF_CPUS);
        androidDeferredManager= new AndroidDeferredManager(mExecutorService);

        loadMoviesJson();
    }

    private void showLoading(){
        frameLayoutProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        frameLayoutProgress.setVisibility(View.GONE);
    }

}
