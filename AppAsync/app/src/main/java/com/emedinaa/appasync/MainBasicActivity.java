package com.emedinaa.appasync;

import android.os.Bundle;
import android.widget.TextView;

import com.emedinaa.appasync.ui.BaseActivity;

/**
 * Created by emedinaa on 8/08/17.
 */

public class MainBasicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_runnable);
        ui();
    }

    private void ui() {
        textViewThread= (TextView) findViewById(R.id.textViewThread);
    }


    @Override
    protected void onResume() {
        super.onResume();
        renderThreads();
    }

}
