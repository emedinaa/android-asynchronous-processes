package com.emedinaa.appasync.task;

import android.os.Handler;
import android.os.Looper;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.utils.AssetJsonHelper;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by eduardomedina on 21/08/17.
 */

public class TaskManager<T> {

    final int DEFAULT_THREAD_POOL_SIZE = 4;
    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    final int KEEP_ALIVE_TIME = 1;
    final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    final ExecutorService mExecutorService;
    final BlockingQueue<Runnable> mTaskQueue;

    public TaskManager() {
        mTaskQueue = new LinkedBlockingQueue<Runnable>();
        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mTaskQueue);
    }

    public void  execute(Callable<T> callable){
        Future<T> future= mExecutorService.submit(callable);
    }


    class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    }
}
