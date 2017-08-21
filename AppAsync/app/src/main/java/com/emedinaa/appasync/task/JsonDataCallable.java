package com.emedinaa.appasync.task;

import com.emedinaa.appasync.model.Movie;
import com.emedinaa.appasync.model.MovieJsonData;
import com.emedinaa.appasync.utils.AssetJsonHelper;
import com.google.common.collect.ImmutableList;

import java.util.concurrent.Callable;

/**
 * Created by eduardomedina on 21/08/17.
 */

public class JsonDataCallable implements Callable<ImmutableList<Movie>> {

    private final AssetJsonHelper assetJsonHelper;
    private ImmutableList<Movie> mMovies=null;

    public JsonDataCallable(AssetJsonHelper assetJsonHelper) {
        this.assetJsonHelper = assetJsonHelper;
    }

    @Override
    public ImmutableList<Movie> call() throws Exception {
        loadMovies();
        return mMovies;
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
}
