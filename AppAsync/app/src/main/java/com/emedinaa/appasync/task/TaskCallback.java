package com.emedinaa.appasync.task;

/**
 * Created by eduardomedina on 21/08/17.
 */

public interface TaskCallback<T>{
    void onResponse(T response);
    void onFailure(Throwable throwable );
}
