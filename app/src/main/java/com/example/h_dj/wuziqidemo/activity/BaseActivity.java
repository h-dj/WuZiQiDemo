package com.example.h_dj.wuziqidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by H_DJ on 2017/6/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getlayoutId() != 0) {
            setContentView(getlayoutId());
        }
        // TODO: add setContentView(...) invocation
        bind = ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    protected abstract int getlayoutId();

    protected void goTo(Class mClass) {
        this.goTo(mClass, null);
    }

    protected void goTo(Class mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
