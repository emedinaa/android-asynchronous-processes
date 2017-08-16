package com.emedinaa.appasync.task;

import com.emedinaa.appasync.model.Movie;
import com.google.common.collect.ImmutableList;

/**
 * Created by emedinaa on 8/08/17.
 */

public interface UICallback {

    void updateUICallback(ImmutableList<Movie> movies);
}
