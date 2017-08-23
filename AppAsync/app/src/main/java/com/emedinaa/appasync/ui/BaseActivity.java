package com.emedinaa.appasync.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.emedinaa.appasync.utils.AssetJsonHelper;

/**
 * Created by emedinaa on 5/08/17.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    protected View progressView;
    protected TextView textViewThread;
    protected AssetJsonHelper assetJsonHelper;
    protected Handler handler;
    private Handler customHandler= new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetJsonHelper= new AssetJsonHelper(getAssets());
        handler= new UIHandler(Looper.getMainLooper());
    }

    private Runnable refreshRunnable = new Runnable() {
        @Override public void run() {
            renderThreads();
        }
    };

    protected void renderThreads() {
        int count = Thread.activeCount();
        String threadsText = "";
        Thread[] threads = new Thread[count];
        Thread.enumerate(threads);
        for (Thread thread : threads) {
            if (thread != null) {
                String text = thread.getName() + " " + thread.getState().name() + "/\n";
                threadsText += text;
            }
        }
        textViewThread.setText(threadsText);
        customHandler.postDelayed(refreshRunnable, 200);
    }

    protected void updateUI(Bundle bundle,int type){}

    //protected  abstract void setPresenter(Object presenter);

    protected void showLoading(){
        progressView.setVisibility(View.VISIBLE);
    }

    protected void hideLoading(){
        progressView.setVisibility(View.GONE);
    }

    private class UIHandler extends Handler{
        private final int COMPLETE= 100;

        public UIHandler(Looper looper) { super(looper);}

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case COMPLETE:
                    Bundle bundle= msg.getData();
                    updateUI(bundle,0);
                    break;
            }
        }
    }
}
