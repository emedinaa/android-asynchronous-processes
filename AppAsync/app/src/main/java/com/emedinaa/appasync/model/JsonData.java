package com.emedinaa.appasync.model;

import java.util.List;

/**
 * Created by emedinaa on 6/08/17.
 */

public class JsonData<T> {

    protected List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
