package com.example.h_dj.wuziqidemo.activity;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.h_dj.wuziqidemo.R;
import com.example.h_dj.wuziqidemo.widget.WuZiQiPanle;

import butterknife.BindView;

/**
 * Created by H_DJ on 2017/6/11.
 */

public class PlayActivity extends BaseActivity {

    private final String TAG = "PlayActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.w)
    WuZiQiPanle mW;
    @BindView(R.id.msg)
    TextView mMsg;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void init() {
        initToolbar();
        initGameListener();
    }

    private void initGameListener() {
        mW.setPieceType(0);
        mW.setPlayMode(1);
        mW.setOnGameStateListener(new WuZiQiPanle.OnGameStateListener() {
            @Override
            public void isPause(boolean isPause) {
                if (isPause) {
                    new AlertDialog.Builder(PlayActivity.this)
                            .setTitle("游戏已暂停！")
                            .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mW.pauseGame(false);
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }

            }

            @Override
            public void isGameOver(boolean isGameOver) {
                if (isGameOver) {
                    mMsg.setVisibility(View.VISIBLE);
                } else {
                    mMsg.setVisibility(View.GONE);
                }

            }

            @Override
            public void gameOverResult(String winer) {
                mMsg.setText(winer);
            }

            @Override
            public void gameState(Point point, boolean isWhitePiece) {
                Log.e(TAG, "gameState: " + point.x + " : " + point.y + " ：" + isWhitePiece);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.reStart:
                mW.restart();
                break;
            case R.id.pause:
                mW.pauseGame(true);
                break;
            case R.id.Undo:
                mW.Undo();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        mW.clearCache();
        super.onDestroy();
    }
}
