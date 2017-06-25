package com.example.h_dj.wuziqidemo.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by H_DJ on 2017/6/16.
 * 进行下棋的房间
 */

public class RoomBeans extends BmobObject{

    private int roomId;//房间Id
    private Player mPlayer1;//玩家1
    private Player mPlayer2;//玩家2


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Player getPlayer1() {
        return mPlayer1;
    }

    public void setPlayer1(Player player1) {
        mPlayer1 = player1;
    }

    public Player getPlayer2() {
        return mPlayer2;
    }

    public void setPlayer2(Player player2) {
        mPlayer2 = player2;
    }
}
