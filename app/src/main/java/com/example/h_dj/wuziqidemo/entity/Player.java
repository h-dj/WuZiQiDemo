package com.example.h_dj.wuziqidemo.entity;

import android.graphics.Point;

import java.util.List;

/**
 * Created by H_DJ on 2017/6/16.
 */
public class Player {

    private String playerName;
    private boolean isTrueToMe;//是否轮到下棋
    private List<Point> mPoints;//已下的棋子

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isTrueToMe() {
        return isTrueToMe;
    }

    public void setTrueToMe(boolean trueToMe) {
        isTrueToMe = trueToMe;
    }

    public List<Point> getPoints() {
        return mPoints;
    }

    public void setPoints(List<Point> points) {
        mPoints = points;
    }
}
