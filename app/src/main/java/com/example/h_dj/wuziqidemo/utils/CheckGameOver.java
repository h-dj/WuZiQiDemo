package com.example.h_dj.wuziqidemo.utils;

import android.graphics.Point;

import java.util.List;

/**
 * Created by H_DJ on 2017/6/13.
 */

public class CheckGameOver {

    private static CheckGameOver mCheckGameOver;
    /**
     * 多少个棋子可以胜利
     */
    private final int WIN_IN_LINE_NUM = 5;
    private final int MAX_LINE = 10;

    private CheckGameOver() {
    }

    /**
     * 实例化
     *
     * @return
     */
    public static CheckGameOver newInstance() {
        if (mCheckGameOver == null) {
            synchronized (CheckGameOver.class) {
                if (mCheckGameOver == null) {
                    mCheckGameOver = new CheckGameOver();
                }
            }
        }
        return mCheckGameOver;
    }

    /**
     * 销毁
     */
    public void destory() {
        if (mCheckGameOver != null) {
            mCheckGameOver = null;
        }
    }

    /**
     * 检查是否平手
     *
     * @return
     */
    public boolean checkDeuce(List<Point> black, List<Point> white) {
        for (int i = 0; i < MAX_LINE; i++) {
            for (int j = 0; j < MAX_LINE; j++) {
                if (!black.contains(new Point(i, j)) && !white.contains(new Point(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查是白棋子胜利
     */
    public boolean checkWhiteWin(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            boolean win1 = checkHorizontal(x, y, points);
            if (win1) {
                return true;
            }
            boolean win2 = checkVertical(x, y, points);
            if (win2) {
                return true;
            }
            boolean win3 = checkBevel(x, y, points);
            if (win3) {
                return true;
            }
            boolean win4 = checkBevelReverse(x, y, points);
            if (win4) {
                return true;
            }
        }
        return false;
    }

    /* 检查水平方向是否够五个棋子
    *
    * @param x
    * @param y
    * @param points
    * @return
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        //当前相连的棋子数
        int count = 1;
        for (int i = 1; i <WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        for (int i = 1; i <WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        return false;
    }


    /**
     * 检查斜面
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkBevel(int x, int y, List<Point> points) {
        //当前相连的棋子数
        int count = 1;
        for (int i = 1; i < WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        for (int i = 1; i <WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        return false;
    }

    /**
     * 检查垂直方向
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkVertical(int x, int y, List<Point> points) {
        //当前相连的棋子数
        int count = 1;
        for (int i = 1; i < WIN_IN_LINE_NUM; i++) {

            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        for (int i = 1; i <WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        return false;
    }


    /**
     * 检查反斜向
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkBevelReverse(int x, int y, List<Point> points) {
        //当前相连的棋子数
        int count = 1;
        for (int i = 1; i < WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        for (int i = 1; i < WIN_IN_LINE_NUM; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                //如果不连续就退出
                break;
            }
        }
        if (count == WIN_IN_LINE_NUM) {
            return true;
        }
        return false;
    }

}
