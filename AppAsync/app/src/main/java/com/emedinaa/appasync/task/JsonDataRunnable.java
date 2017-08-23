package com.emedinaa.appasync.task;

import android.util.Log;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.model.MovieJsonData;
import com.emedinaa.appasync.utils.AssetJsonHelper;
import com.google.common.collect.ImmutableList;

/**
 * Created by emedinaa on 8/08/17.
 */

public class JsonDataRunnable implements Runnable{

    private final String TAG= "CONSOLE";
    private final AssetJsonHelper assetJsonHelper;
    private final UICallback uiCallback;
    private ImmutableList<Movie> mMovies=null;

    public JsonDataRunnable(AssetJsonHelper assetJsonHelper, UICallback uiCallback) {
        this.assetJsonHelper = assetJsonHelper;
        this.uiCallback = uiCallback;
    }

    public JsonDataRunnable(AssetJsonHelper assetJsonHelper) {
        this.assetJsonHelper = assetJsonHelper;
        this.uiCallback = null;
    }

    private void loadMovies(){
        MovieJsonData jsonData=null;
        try {
            jsonData= assetJsonHelper.convertObjectToJsonAssets("movies.json",MovieJsonData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonData!=null && jsonData.getData()!=null){
            mMovies= ImmutableList.copyOf(jsonData.getData());
        }
    }

    @Override
    public void run() {
        loadMovies();
        Log.v(TAG, "Runnable task complete");
        if(uiCallback!=null)uiCallback.updateUICallback(mMovies);
    }
}
