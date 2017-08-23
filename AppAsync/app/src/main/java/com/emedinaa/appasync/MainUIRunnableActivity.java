package com.emedinaa.appasync;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.task.JsonDataRunnable;
import com.emedinaa.appasync.task.UICallback;
import com.emedinaa.appasync.ui.BaseActivity;
import com.emedinaa.appasync.ui.adapter.MovieAdapter;
import com.google.common.collect.ImmutableList;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainUIRunnableActivity extends BaseActivity implements UICallback {

    private final String TAG="CONSOLE";
    private final int SPAN_COUNT = 2;

    private RecyclerView recyclerViewMovies;
    private GridLayoutManager gridLayoutManager;

    private ImmutableList<Movie> movies;

    private void ui() {
        progressView = findViewById(R.id.frameLayoutProgress);
        recyclerViewMovies = (RecyclerView) findViewById(R.id.recyclerViewMovies);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        recyclerViewMovies.setLayoutManager(gridLayoutManager);
        textViewThread = (TextView) findViewById(R.id.textViewThread);
    }


    private void startThread() {
        final int DEFAULT_THREAD_POOL_SIZE = 4;
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        final int KEEP_ALIVE_TIME = 1;
        final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        final BlockingQueue<Runnable> mTaskQueue;
        final ExecutorService mExecutorService;

        mTaskQueue = new LinkedBlockingQueue<Runnable>();

        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mTaskQueue);
        mExecutorService.submit(new JsonDataRunnable(assetJsonHelper, this));

        Log.v(TAG, "1. Start Thread");
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderThreads();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ui();
        startThread();
    }

    @Override
    protected void updateUI(Bundle bundle, int type) {
        super.updateUI(bundle, type);
        Log.v(TAG, "3. Update UI");
        MovieAdapter movieAdapter = new MovieAdapter(movies);
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    @Override
    public void updateUICallback(ImmutableList<Movie> movies) {
        this.movies = movies;
        Log.v(TAG, "2. Complete Task -> UI");
        if (handler != null) {
            handler.sendEmptyMessage(100);
        }
    }

}
