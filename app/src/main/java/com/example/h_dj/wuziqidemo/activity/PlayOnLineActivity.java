package com.example.h_dj.wuziqidemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h_dj.wuziqidemo.R;
import com.example.h_dj.wuziqidemo.entity.Player;
import com.example.h_dj.wuziqidemo.entity.RoomBeans;
import com.example.h_dj.wuziqidemo.widget.WuZiQiPanle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.GsonUtil;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by H_DJ on 2017/6/16.
 */

public class PlayOnLineActivity extends BaseActivity {

    private final String TAG = "PlayOnLineActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.play_1)
    TextView mPlay1;
    @BindView(R.id.play_2)
    TextView mPlay2;
    @BindView(R.id.wuZiQi)
    WuZiQiPanle mWuZiQi;

    private BmobRealTimeData rtd;
    /**
     * 监听行的id
     */
    private String objectId;
    /**
     * 是否已初始化信息
     */
    private boolean isInitInfo;
    /**
     * 棋子类型
     */
    private int pieceType;
    /**
     * 白棋
     */
    private List<Point> mWhitePiece;
    /**
     * 黑棋
     */
    private List<Point> mBlackPiece;

    private RoomBeans mRoomBeans;

    @Override
    protected void init() {
        mBlackPiece = new ArrayList<>();
        mWhitePiece = new ArrayList<>();
        initToolbar();
        initIntent();
        initWuZiQi();
        initRealData();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle data = intent.getBundleExtra("data");
            if (data != null) {
                objectId = data.getString("objectId");
                pieceType = data.getInt("type");
            }
        }
    }

    /**
     * 初始化实时数据同步
     */
    private void initRealData() {
        // 监听行更新
        if (!TextUtils.isEmpty(objectId)) {
            Log.e(TAG, "initRealData: " + objectId);
            rtd = new BmobRealTimeData();
            rtd.start(new ValueEventListener() {
                @Override
                public void onDataChange(JSONObject data) {
                    try {
                        Log.e(TAG, "onDataChange: " + data.getString("data"));
                        mRoomBeans = (RoomBeans) GsonUtil.toObject(data.getString("data"), RoomBeans.class);
                        updateData(mRoomBeans);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConnectCompleted(Exception ex) {
                    Log.d("bmob", "连接成功:" + rtd.isConnected());
                    if (rtd.isConnected()) {
                        rtd.subRowUpdate("RoomBeans", objectId);
                    }
                }
            });
        }
    }

    private void updateData(RoomBeans room) {
        Player player1 = room.getPlayer1();
        Player player2 = room.getPlayer2();
        if(player2!=null){
            mWuZiQi.setPieces(false, player2.getPoints());
            mWuZiQi.setTureToWhite(player2.isTrueToMe());
        }
        if(player1!=null){
            mWuZiQi.setPieces(true, player1.getPoints());
            mWuZiQi.setTureToWhite(player1.isTrueToMe());
        }
        mWuZiQi.invalidate();
    }


    /**
     * 初始化棋盘
     */
    private void initWuZiQi() {
        mWuZiQi.restart();
        mWuZiQi.setPieceType(pieceType);
        mWuZiQi.setPlayMode(0);
        mWuZiQi.setOnGameStateListener(new WuZiQiPanle.OnGameStateListener() {
            @Override
            public void isPause(boolean isPause) {
                Log.e("isPause", "isPause: " + isPause);
            }

            @Override
            public void isGameOver(boolean isGameOver) {
            }

            @Override
            public void gameOverResult(String winer) {
                new AlertDialog.Builder(PlayOnLineActivity.this)
                        .setTitle("游戏结束")
                        .setMessage(winer)
                        .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Player play1 = new Player();
                                mWhitePiece.clear();
                                play1.setPoints(mWhitePiece);
                                play1.setTrueToMe(true);

                                Player play2 = new Player();
                                mBlackPiece.clear();
                                play2.setPoints(mBlackPiece);
                                play2.setTrueToMe(false);

                                RoomBeans room = new RoomBeans();
                                room.update(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            mWuZiQi.restart();
                                        } else {
                                            Toast.makeText(PlayOnLineActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .create()
                        .show();
            }

            @Override
            public void gameState(Point point, boolean isWhitePiece) {
                Log.e(TAG, "gameState: " + point.x + " ； " + point.y + isWhitePiece + pieceType);
                Player player = new Player();
                RoomBeans roomBeans = new RoomBeans();
                if (pieceType == 0) {
                    player.setTrueToMe(isWhitePiece);
                    player.setPlayerName("玩家一");
                    mWhitePiece.add(point);
                    player.setPoints(mWhitePiece);
                    roomBeans.setValue("mPlayer1", player);
                } else if (pieceType == 1) {
                    player.setTrueToMe(!isWhitePiece);
                    player.setPlayerName("玩家二");
                    mBlackPiece.add(point);
                    player.setPoints(mBlackPiece);
                    roomBeans.setValue("mPlayer2", player);
                }
                roomBeans.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e(TAG, "done: 成功");
                        } else {
                            Log.e(TAG, "done:失败");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_play_online;
    }


    @Override
    protected void onDestroy() {
        if (mRoomBeans != null) {
            mRoomBeans = null;
        }
        if (mWuZiQi != null) {
            mWuZiQi.clearCache();
        }
        if (rtd != null) {
            rtd.unsubRowUpdate("RoomBeans", objectId);
        }
        super.onDestroy();
    }
}
