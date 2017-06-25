package com.example.h_dj.wuziqidemo.activity;

import android.os.Handler;
import android.os.Message;

/**
 * Created by H_DJ on 2017/6/11.
 */

public class SplashActivity extends BaseActivity {

    private static final int INIT_SUCCESS = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == INIT_SUCCESS) {
                goTo(MainActivity.class);
                SplashActivity.this.finish();
            }
        }
    };

    @Override
    protected void init() {
        mHandler.sendEmptyMessageDelayed(INIT_SUCCESS, 1000);
    }

    @Override
    protected int getlayoutId() {
        return 0;
    }
}
