package com.emedinaa.appasync.presenter;

import com.emedinaa.appasync.model.Movie;
import com.google.common.collect.ImmutableList;

/**
 * Created by emedinaa on 5/08/17.
 */

public interface MovieContract {

    public interface MovieContractView{

        void renderMovies(ImmutableList<Movie> movies);
        void showError(String errorMessage);

        void showLoading();
        void hideLoading();
    }
}
