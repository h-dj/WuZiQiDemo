package com.example.h_dj.wuziqidemo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.h_dj.wuziqidemo.R;
import com.example.h_dj.wuziqidemo.entity.Player;
import com.example.h_dj.wuziqidemo.entity.RoomBeans;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static java.lang.Integer.parseInt;

public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";
    @BindView(R.id.startGame)
    Button mStartGame;
    @BindView(R.id.setLevel)
    Button mSetLevel;
    @BindView(R.id.online)
    Button mOnline;

    @Override
    protected void init() {
        //第一：默认初始化
        Bmob.initialize(this, "f11c77b0e8893bd025d204c7635445f1");
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.startGame, R.id.setLevel, R.id.online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startGame:
                goTo(PlayActivity.class);
                break;
            case R.id.setLevel:
                break;
            case R.id.online:
                chooseOrSetupRoom();
                break;
        }
    }

    /**
     * 加入或创建房间
     */
    private void chooseOrSetupRoom() {
        new AlertDialog.Builder(this)
                .setTitle("进行联网对战")
                .setPositiveButton("创建房间", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createRoom();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("加入房间", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        joinRoom();
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create()
                .show();
    }

    /**
     * 加入房间
     */
    private void joinRoom() {
        final EditText editText = new EditText(this);
        editText.setHint("输入房间Id");
        new AlertDialog.Builder(this)
                .setTitle("加入房间")
                .setView(editText)
                .setPositiveButton("加入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        //只返回Person表的objectId这列的值
                        BmobQuery<RoomBeans> bmobQuery = new BmobQuery<RoomBeans>();
                        final int roomId = Integer.parseInt(editText.getText().toString().trim());
                        bmobQuery.addWhereEqualTo("roomId", roomId);
                        bmobQuery.addQueryKeys("objectId");
                        bmobQuery.findObjects(new FindListener<RoomBeans>() {
                            @Override
                            public void done(List<RoomBeans> object, BmobException e) {
                                if (e == null) {
                                    if (object.size() > 0) {
                                        Log.e(TAG, "done: roomId" + roomId);
                                        addPlayerToRoomBeans(object.get(0).getObjectId(), roomId);
                                    } else {
                                        Toast.makeText(MainActivity.this, "没有这个房间", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setCancelable(true)
                .create()
                .show();
    }

    /**
     * 添加玩家到房间
     *
     * @param objectId
     */
    private void addPlayerToRoomBeans(final String objectId, int roomId) {
        RoomBeans roomBeans = new RoomBeans();
        Player player = new Player();
        player.setPlayerName("玩家二");
        player.setTrueToMe(false);
        roomBeans.setPlayer2(player);
        roomBeans.setRoomId(roomId);
        roomBeans.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("objectId", objectId);
                    bundle.putInt("type", 1);
                    goTo(PlayOnLineActivity.class, bundle);
                } else {
                    Toast.makeText(MainActivity.this, "加入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 创建房间
     */
    private void createRoom() {
        final EditText roomId = new EditText(this);
        roomId.setHint("输入房间id");
        new AlertDialog.Builder(this)
                .setTitle("创建房间")
                .setView(roomId)
                .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RoomBeans roomBeans = new RoomBeans();
                        roomBeans.setRoomId(parseInt(roomId.getText().toString()));
                        Player play = new Player();
                        play.setPlayerName("玩家一");
                        play.setTrueToMe(true);
                        roomBeans.setPlayer1(play);
                        roomBeans.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Log.e(TAG, "done: " + s);
                                    Toast.makeText(MainActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("objectId", s);
                                    bundle.putInt("type", 0);
                                    goTo(PlayOnLineActivity.class, bundle);
                                } else {
                                    Toast.makeText(MainActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
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
}
