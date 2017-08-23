package com.emedinaa.appasync;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.task.JsonDataRunnable;
import com.emedinaa.appasync.task.UICallback;
import com.emedinaa.appasync.ui.BaseActivity;
import com.google.common.collect.ImmutableList;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by emedinaa on 8/08/17.
 */

public class MainRunnableActivity extends BaseActivity implements UICallback {

    private final String TAG= "CONSOLE";

    private ImmutableList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_runnable);
        ui();
        startThread();
    }

    private void ui() {
        textViewThread= (TextView) findViewById(R.id.textViewThread);
    }

    private void startThread() {
        final int DEFAULT_THREAD_POOL_SIZE = 4;
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        final int KEEP_ALIVE_TIME = 1;
        final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        final BlockingQueue<Runnable> mTaskQueue;
        final ExecutorService mExecutorService;

        mTaskQueue = new LinkedBlockingQueue<Runnable>();

        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES*2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mTaskQueue);
        mExecutorService.submit(new JsonDataRunnable(assetJsonHelper,this));

        Log.v(TAG, "1 start Thread ...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderThreads();
    }

    @Override
    public void updateUICallback(ImmutableList<Movie> movies) {
        this.movies=movies;
        if(handler!=null){
            handler.sendEmptyMessage(100);
        }
    }

    protected void updateUI(Bundle bundle, int type) {
        Log.v(TAG, "3 update UI...");
    }


}
