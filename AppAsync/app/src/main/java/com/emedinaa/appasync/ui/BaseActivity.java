package com.emedinaa.appasync.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.emedinaa.appasync.utils.AssetJsonHelper;

/**
 * Created by emedinaa on 5/08/17.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    protected View progressView;
    protected AssetJsonHelper assetJsonHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetJsonHelper= new AssetJsonHelper(getAssets());
    }

    protected  abstract void setPresenter(Object presenter);

    protected void showLoading(){
        progressView.setVisibility(View.VISIBLE);
    }

    protected void hideLoading(){
        progressView.setVisibility(View.GONE);
    }
}
