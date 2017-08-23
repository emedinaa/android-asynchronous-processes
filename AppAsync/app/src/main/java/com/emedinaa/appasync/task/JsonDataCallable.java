package com.emedinaa.appasync.task;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.model.MovieJsonData;
import com.emedinaa.appasync.utils.AssetJsonHelper;
import com.google.common.collect.ImmutableList;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/**
 * Created by eduardomedina on 21/08/17.
 */

public class JsonDataCallable implements Callable<ImmutableList<Movie>> {

    private final AssetJsonHelper assetJsonHelper;
    private ImmutableList<Movie> mMovies=null;
    private final Executor executor;

    public JsonDataCallable(AssetJsonHelper assetJsonHelper, Executor executor) {
        this.assetJsonHelper = assetJsonHelper;
        this.executor = executor;
    }

    @Override
    public ImmutableList<Movie> call()  {
        try {
            loadMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMovies;
    }

    private void loadMovies() throws Exception{
        MovieJsonData jsonData=null;
            jsonData= assetJsonHelper.convertObjectToJsonAssets("movies.json",MovieJsonData.class);

        if(jsonData!=null && jsonData.getData()!=null){
            mMovies= ImmutableList.copyOf(jsonData.getData());
        }
    }
}
