package com.emedinaa.appasync;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.emedinaa.appasync.model.JsonData;
import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.presenter.MovieContract;
import com.emedinaa.appasync.presenter.MoviePresenter;
import com.emedinaa.appasync.ui.BaseActivity;
import com.emedinaa.appasync.ui.adapter.MovieAdapter;
import com.google.common.collect.ImmutableList;

public class MainActivity extends BaseActivity implements MovieContract.MovieContractView {

    private final int SPAN_COUNT= 2;

    private RecyclerView recyclerViewMovies;
    private StaggeredGridLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    private ImmutableList<Movie> movies;
    private MoviePresenter moviePresenter;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ui();
        loadMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderThreads();
    }

    private void renderThreads() {
        int count = Thread.activeCount();
        Log.v("CONSOLE", "threads "+count);

        Thread[] threads = new Thread[count];
        Thread.enumerate(threads);
        for (Thread thread : threads) {
            if (thread != null) {
                String text = thread.getName() + " " + thread.getState().name() + "/\n";
                Log.v("CONSOLE", "text  "+text);
            }
        }

        handler.postDelayed(refreshThread, 200);
    }

    private Runnable refreshThread = new Runnable() {
        @Override public void run() {
            renderThreads();
        }
    };

    private void ui() {
        progressView= findViewById(R.id.frameLayoutProgress);
        recyclerViewMovies= (RecyclerView)findViewById(R.id.recyclerViewMovies);
        layoutManager= new StaggeredGridLayoutManager(SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager= new GridLayoutManager(this,SPAN_COUNT);
        //recyclerViewMovies.setLayoutManager(layoutManager);
        recyclerViewMovies.setLayoutManager(gridLayoutManager);
        //recyclerViewMovies.setHasFixedSize(true);
    }

    private void loadMovies(){
        MovieJsonData jsonData=null;
        try {
            jsonData= assetJsonHelper.convertObjectToJsonAssets("movies.json",MovieJsonData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonData!=null && jsonData.getData()!=null){
            ImmutableList<Movie> mMovies= ImmutableList.copyOf(jsonData.getData());
            renderMovies(mMovies);
        }
    }

    @Override
    public void renderMovies(ImmutableList<Movie> movies) {
        MovieAdapter movieAdapter= new MovieAdapter(movies);
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showLoading() {showLoading();}

    @Override
    public void hideLoading() {hideLoading();}

    @Override
    protected void setPresenter(Object presenter) {

    }

    private class MovieJsonData extends JsonData<Movie>{}
}
