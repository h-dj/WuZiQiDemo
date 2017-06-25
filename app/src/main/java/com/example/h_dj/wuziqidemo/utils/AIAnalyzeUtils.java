package com.example.h_dj.wuziqidemo.utils;

import android.graphics.Point;
import android.util.Log;

import java.util.List;

/**
 * Created by H_DJ on 2017/6/13.
 */

public class AIAnalyzeUtils {
    private final String TAG = "AIAnalyzeUtils";
    private static AIAnalyzeUtils mAIAnalyzeUtils;
    private List<Point> mBlackPieces;
    private List<Point> mWhitePieces;
    private int MAX_LINE = 10;

    private AIAnalyzeUtils(List<Point> mBlackPieces, List<Point> mWhitePieces) {
        this.mBlackPieces = mBlackPieces;
        this.mWhitePieces = mWhitePieces;
    }

    /**
     * 实例化
     *
     * @return
     */
    public static AIAnalyzeUtils newInstance(List<Point> mBlackPieces, List<Point> mWhitePieces) {
        if (mAIAnalyzeUtils == null) {
            synchronized (AIAnalyzeUtils.class) {
                if (mAIAnalyzeUtils == null) {
                    mAIAnalyzeUtils = new AIAnalyzeUtils(mBlackPieces, mWhitePieces);
                }
            }
        }
        return mAIAnalyzeUtils;
    }

    public void destory() {
        if (mAIAnalyzeUtils != null) {
            mAIAnalyzeUtils = null;
        }
    }


    public boolean checkAIPlaceThePiece() {
        Log.i(TAG, "==================");
        boolean a = false;
        //对手为1 ；AI为2；空为0
        //检查AI出现22022
        a = checkBlackTwoTwo(mBlackPieces);
        Log.e("123", "checkBlackTwoTwo: " + a);
        if (a) {
            return a;
        }

        //检查黑色棋子四个相连
        a = checkBlackFourInLine(mBlackPieces);
        Log.e("123", "checkBlackFourInLine: " + a);
        if (a) {
            return a;
        }
        //检查白色棋子四个相连
        a = checkBlackFourInLine(mWhitePieces);
        Log.e("123", "checkWhiteFourInLine: " + a);
        if (a) {
            return a;
        }
        //白子快成5的时候（11011），电脑下在中间。
        a = checkWhiteTwoTwo(mWhitePieces);
        Log.e("123", "checkWhiteTwoTwo: " + a);
        if (a) {
            return a;
        }

        //检查AI出现2202
        a = checkBlackTwoOne(mBlackPieces);
        Log.e("123", "checkBlackTwoOne: " + a);
        if (a) {
            return a;
        }

        //检查白色棋子出现2202
        a = checkBlackTwoOne(mWhitePieces);
        Log.e("123", "checkWhiteTwoOne: " + a);
        if (a) {
            return a;
        }
        //检查黑色棋子三个相连
        a = checkWhiteThreeInline(mBlackPieces);
        Log.e("123", "checkBlackThreeInLine: " + a);
        if (a) {
            return a;
        }

        //检查白色棋子三个相连
        a = checkWhiteThreeInline(mWhitePieces);
        Log.e("123", "checkWhiteThreeInline: " + a);
        if (a) {
            return a;
        }

        //检查黑色不相连
        a = checkOneBlackLeft(mBlackPieces);
        Log.e("123", "checkOneBlackLeft: " + a);
        if (a) {
            return a;
        }
        a = checkOnWhitePiece(mWhitePieces);
        Log.e("123", "checkOnWhitePiece: " + a);
        if (a) {
            return a;
        }
        a = NoPieceConnect();
        Log.e("123", "NoPieceConnect: " + a);
        if (a) {
            return a;
        }
        return false;
    }

    /**
     * 检查AI出现2202
     *
     * @param pieces
     * @return
     */
    private boolean checkBlackTwoOne(List<Point> pieces) {
        for (Point point : pieces) {
            int x = point.x;
            int y = point.y;
            boolean isTO = checkBlackTwoOneInHorizontalLine(x, y, pieces);
            if (isTO) {
                return true;
            }
            isTO = checkBlackTwoOneInVerticalLine(x, y, pieces);
            if (isTO) {
                return true;
            }
            isTO = checkBlackTwoOneInLeftLine(x, y, pieces);
            if (isTO) {
                return true;
            }
            isTO = checkBlackTwoOneInRightLine(x, y, pieces);
            if (isTO) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBlackTwoOneInRightLine(int x, int y, List<Point> pieces) {
        if (x - 3 >= 0 && y + 3 < MAX_LINE) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y + i)) || mWhitePieces.contains(new Point(x - i, y + i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x - i, y + i))) {
                        mBlackPieces.add(new Point(x - 2, y + 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x - i, y + i))) {
                        break;
                    }
                }
            }
        }
        if (x + 3 < MAX_LINE && y - 3 >= 0) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y - i)) || mWhitePieces.contains(new Point(x + i, y - i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x + i, y - i))) {
                        mBlackPieces.add(new Point(x + 2, y - 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x + i, y - i))) {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkBlackTwoOneInLeftLine(int x, int y, List<Point> pieces) {
        if (x - 3 >= 0 && y - 3 >= 0) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y - i)) || mWhitePieces.contains(new Point(x - i, y - i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x - i, y - i))) {
                        mBlackPieces.add(new Point(x - 2, y - 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x - i, y - i))) {
                        break;
                    }
                }
            }
        }
        if (x + 3 < MAX_LINE && y + 3 < MAX_LINE) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y + i)) || mWhitePieces.contains(new Point(x + i, y + i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x + i, y + i))) {
                        mBlackPieces.add(new Point(x + 2, y + 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x + i, y + i))) {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkBlackTwoOneInVerticalLine(int x, int y, List<Point> pieces) {
        if (y - 3 >= 0) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y - i)) || mWhitePieces.contains(new Point(x, y - i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x, y - i))) {
                        mBlackPieces.add(new Point(x, y - 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x, y - i))) {
                        break;
                    }
                }
            }
        }
        if (x + 3 < MAX_LINE) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y + i)) || mWhitePieces.contains(new Point(x, y + i))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x, y + i))) {
                        mBlackPieces.add(new Point(x, y + 2));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x, y + i))) {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkBlackTwoOneInHorizontalLine(int x, int y, List<Point> pieces) {
        Log.e(TAG, "checkBlackTwoOneInHorizontalLine: " + x + " : " + y);
        if (x - 3 >= 0) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y)) || mWhitePieces.contains(new Point(x - i, y))) {
                        return false;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x - i, y))) {
                        mBlackPieces.add(new Point(x - 2, y));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x - i, y))) {
                        break;
                    }
                }
            }
        }
        if (x + 3 < MAX_LINE) {
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y)) || mWhitePieces.contains(new Point(x + i, y))) {
                        break;
                    }
                } else if (i == 3) {
                    if (pieces.contains(new Point(x + i, y))) {
                        mBlackPieces.add(new Point(x + 2, y));
                        return true;
                    }
                } else {
                    if (!pieces.contains(new Point(x + i, y))) {
                        break;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 检查白色棋子三个相连
     *
     * @param whitePieces
     * @return
     */
    private boolean checkWhiteThreeInline(List<Point> whitePieces) {
        for (Point point : whitePieces) {
            int x = point.x;
            int y = point.y;
            //检查水平方向是否有白棋三个相连
            boolean isThreeInLine = checkWhiteThreeInLineHorizontal(x, y, whitePieces);
            if (isThreeInLine) {
                return true;
            }
            isThreeInLine = checkWhiteThreeInLineVertical(x, y, whitePieces);
            if (isThreeInLine) {
                return true;
            }
            isThreeInLine = checkWhiteThreeInLineLeftLine(x, y, whitePieces);
            if (isThreeInLine) {
                return true;
            }
            isThreeInLine = checkWhiteThreeInLineRightLine(x, y, whitePieces);
            if (isThreeInLine) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查右斜线三个
     *
     * @param x
     * @param y
     * @param whitePieces
     * @return
     */
    private boolean checkWhiteThreeInLineRightLine(int x, int y, List<Point> whitePieces) {
        int count = 1;
        for (int i = 1; i < 3; i++) {
            if (whitePieces.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 3) {
            if (x + 3 < MAX_LINE - 1 && y - 3 < 0) {
                if (!mBlackPieces.contains(new Point(x - 1, y + 1)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                }
            } else if (x - 1 < 1 && y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x + 3, y - 3)) && !mWhitePieces.contains(new Point(x + 3, y - 3))) {
                    mBlackPieces.add(new Point(x + 3, y - 3));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x + 3, y - 3)) && !mBlackPieces.contains(new Point(x - 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x + 3, y - 3)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x + 3, y - 3) : new Point(x - 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 3, y - 3)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 1, y + 1)) && !mWhitePieces.contains(new Point(x + 3, y - 3))) {
                    mBlackPieces.add(new Point(x + 3, y - 3));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查白色棋子左划线三个相连
     *
     * @param x
     * @param y
     * @param whitePieces
     * @return
     */
    private boolean checkWhiteThreeInLineLeftLine(int x, int y, List<Point> whitePieces) {
        int count = 1;
        for (int i = 1; i < 3; i++) {
            if (whitePieces.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 3) {
            if (x - 3 < 0 && y - 3 < 0) {
                if (!mBlackPieces.contains(new Point(x + 1, y + 1)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                }
            } else if (x + 1 > MAX_LINE - 1 && y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x - 3, y - 3)) && !mWhitePieces.contains(new Point(x - 3, y - 3))) {
                    mBlackPieces.add(new Point(x - 3, y - 3));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x - 3, y - 3)) && !mBlackPieces.contains(new Point(x + 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x - 3, y - 3)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x - 3, y - 3) : new Point(x + 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 3, y - 3)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 1, y + 1)) && !mWhitePieces.contains(new Point(x - 3, y - 3))) {
                    mBlackPieces.add(new Point(x - 3, y - 3));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查白棋垂直三个相连
     *
     * @param x
     * @param y
     * @param pieces
     * @return
     */
    private boolean checkWhiteThreeInLineVertical(int x, int y, List<Point> pieces) {
        int count = 1;
        for (int i = 1; i < 3; i++) {
            if (pieces.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 3) {
            if (y - 3 < 0) {
                if (!mBlackPieces.contains(new Point(x, y + 1)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                }
            } else if (y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x, y - 3)) && !mWhitePieces.contains(new Point(x, y - 3))) {
                    mBlackPieces.add(new Point(x, y - 3));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x, y - 3)) && !mBlackPieces.contains(new Point(x, y + 1))) {
                if (!mWhitePieces.contains(new Point(x, y - 3)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x, y - 3) : new Point(x, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x, y - 3)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x, y + 1)) && !mWhitePieces.contains(new Point(x, y - 3))) {
                    mBlackPieces.add(new Point(x, y - 3));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查水平方向是否有白棋三个相连
     *
     * @param x
     * @param y
     * @param whitePieces
     * @return
     */
    private boolean checkWhiteThreeInLineHorizontal(int x, int y, List<Point> whitePieces) {
        int count = 1;
        for (int i = 1; i < 3; i++) {
            if (whitePieces.contains(new Point(x - i, y))) {
                count++;
            }
        }
        if (count == 3) {
            if (x - 3 < 0) {
                if (!mBlackPieces.contains(new Point(x + 1, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(new Point(x + 1, y));
                    return true;
                }
            } else if (x + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x - 3, y)) && !mWhitePieces.contains(new Point(x - 3, y))) {
                    mBlackPieces.add(new Point(x - 3, y));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x - 3, y)) && !mBlackPieces.contains(new Point(x + 1, y))) {
                if (!mWhitePieces.contains(new Point(x - 3, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x - 3, y) : new Point(x + 1, y));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 3, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(new Point(x + 1, y));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 1, y)) && !mWhitePieces.contains(new Point(x - 3, y))) {
                    mBlackPieces.add(new Point(x - 3, y));
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 检查白棋 白子快成5的时候（11011）
     *
     * @param pieces
     * @return
     */
    private boolean checkWhiteTwoTwo(List<Point> pieces) {
        for (Point point : pieces) {
            int x = point.x;
            int y = point.y;
            boolean isTT = checkWhiteTwoTwoInHorizontal(x, y);
            if (isTT) {
                return true;
            }
            isTT = checkWhiteTwoTwoInVertical(x, y);
            if (isTT) {
                return true;
            }
            isTT = checkWhiteTwoTwoInLeftLine(x, y);
            if (isTT) {
                return true;
            }

            isTT = checkWhiteTwoTwoInRightLine(x, y);
            if (isTT) {
                return true;
            }
        }
        return false;

    }

    private boolean checkWhiteTwoTwoInRightLine(int x, int y) {
        if (x - 4 >= 0 && y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y + i)) || mWhitePieces.contains(new Point(x - i, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x - i, y + i))) {
                        mBlackPieces.add(new Point(x - 2, y + 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x - i, y + i))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE && y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y - i)) || mWhitePieces.contains(new Point(x + i, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x + i, y - i))) {
                        mBlackPieces.add(new Point(x + 2, y - 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x + i, y - i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWhiteTwoTwoInLeftLine(int x, int y) {
        if (x - 4 >= 0 && y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y - i)) || mWhitePieces.contains(new Point(x - i, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x - i, y - i))) {
                        mBlackPieces.add(new Point(x - 2, y - 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x - i, y - i))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE && y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y + i)) || mWhitePieces.contains(new Point(x + i, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x + i, y + i))) {
                        mBlackPieces.add(new Point(x + 2, y + 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x + i, y + i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWhiteTwoTwoInVertical(int x, int y) {
        if (y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y - i)) || mWhitePieces.contains(new Point(x, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x, y - i))) {
                        mBlackPieces.add(new Point(x, y - 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x, y - i))) {
                        return false;
                    }
                }
            }
        } else if (y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y + i)) || mWhitePieces.contains(new Point(x, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x, y + i))) {
                        mBlackPieces.add(new Point(x, y + 2));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x, y + i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWhiteTwoTwoInHorizontal(int x, int y) {
        if (x - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y)) || mWhitePieces.contains(new Point(x - i, y))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x - i, y))) {
                        mBlackPieces.add(new Point(x - 2, y));
                        Log.e(TAG, "x - 4 >= 0 i == 4: " + i + mBlackPieces.contains(new Point(x - 2, y)));
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x - i, y))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y)) || mWhitePieces.contains(new Point(x + i, y))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mWhitePieces.contains(new Point(x + i, y))) {
                        mBlackPieces.add(new Point(x + 2, y));
                        Log.e(TAG, "x + 4 < MAX_LINE i == 4: " + i);
                        return true;
                    }
                } else {
                    if (!mWhitePieces.contains(new Point(x + i, y))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查AI出现22022
     *
     * @param blackPieces
     * @return
     */
    private boolean checkBlackTwoTwo(List<Point> blackPieces) {
        for (Point point : blackPieces) {
            int x = point.x;
            int y = point.y;
            boolean isTT = checkBlackTwoTwoInHorizontal(x, y);
            if (isTT) {
                return true;
            }
            isTT = checkBlackTwoTwoInVertical(x, y);
            if (isTT) {
                return true;
            }
            isTT = checkBlackTwoTwoInLeftLine(x, y);
            if (isTT) {
                return true;
            }

            isTT = checkBlackTwoTwoInRightLine(x, y);
            if (isTT) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查右斜线22022
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkBlackTwoTwoInRightLine(int x, int y) {
        if (x - 4 >= 0 && y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y + i)) || mWhitePieces.contains(new Point(x - i, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x - i, y + i))) {
                        mBlackPieces.add(new Point(x - 2, y + 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x - i, y + i))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE && y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y - i)) || mWhitePieces.contains(new Point(x + i, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x + i, y - i))) {
                        mBlackPieces.add(new Point(x + 2, y - 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x + i, y - i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查左斜线22022
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkBlackTwoTwoInLeftLine(int x, int y) {
        if (x - 4 >= 0 && y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y - i)) || mWhitePieces.contains(new Point(x - i, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x - i, y - i))) {
                        mBlackPieces.add(new Point(x - 2, y - 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x - i, y - i))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE && y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y + i)) || mWhitePieces.contains(new Point(x + i, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x + i, y + i))) {
                        mBlackPieces.add(new Point(x + 2, y + 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x + i, y + i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * //检查垂直方向是否出现22022
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkBlackTwoTwoInVertical(int x, int y) {
        if (y - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y - i)) || mWhitePieces.contains(new Point(x, y - i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x, y - i))) {
                        mBlackPieces.add(new Point(x, y - 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x, y - i))) {
                        return false;
                    }
                }
            }
        } else if (y + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x, y + i)) || mWhitePieces.contains(new Point(x, y + i))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x, y + i))) {
                        mBlackPieces.add(new Point(x, y + 2));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x, y + i))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * //检查AI 水平方向是否出现22022
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkBlackTwoTwoInHorizontal(int x, int y) {
        if (x - 4 >= 0) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x - i, y)) || mWhitePieces.contains(new Point(x - i, y))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x - i, y))) {
                        mBlackPieces.add(new Point(x - 2, y));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x - i, y))) {
                        return false;
                    }
                }
            }
        } else if (x + 4 < MAX_LINE) {
            for (int i = 1; i <= 4; i++) {
                if (i == 2) {
                    if (mBlackPieces.contains(new Point(x + i, y)) || mWhitePieces.contains(new Point(x + i, y))) {
                        return false;
                    }
                } else if (i == 4) {
                    if (mBlackPieces.contains(new Point(x + i, y))) {
                        mBlackPieces.add(new Point(x + 2, y));
                        return true;
                    }
                } else {
                    if (!mBlackPieces.contains(new Point(x + i, y))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 当没有棋子相连时;有空就下
     *
     * @return
     */
    private boolean NoPieceConnect() {
        for (int x = 0; x < MAX_LINE; x++) {
            for (int y = 0; y < MAX_LINE; y++) {
                if (!mWhitePieces.contains(new Point(x, y)) && !mBlackPieces.contains(new Point(x, y))) {
                    mBlackPieces.add(new Point(x, y));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查只有一个白棋时
     *
     * @param whitePieces
     * @return
     */
    private boolean checkOnWhitePiece(List<Point> whitePieces) {
        if (whitePieces.size() == 1) {
            for (Point p : whitePieces) {
                int x = p.x;
                int y = p.y;
                if (x == 0 || y == 0 || x == MAX_LINE - 1 || y == MAX_LINE - 1) {
                    mBlackPieces.add(new Point(MAX_LINE / 2, MAX_LINE / 2));
                    return true;
                } else if (x == MAX_LINE - 1 && y == 0) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x - 1, y) : new Point(x, y + 1));
                    return true;
                } else if (x == 0 && y == MAX_LINE - 1) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(0, y - 1) : new Point(x + 1, y));
                    return true;
                } else if (Math.random() > 0.5) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x + 1, y) : new Point(x - 1, y));
                    return true;
                } else {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x, y + 1) : new Point(x, y - 1));
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkOneBlackLeft(List<Point> point) {
        for (Point p : point) {
            int x = p.x;
            int y = p.y;
            if (x - 1 >= 0) {
                if (!mWhitePieces.contains(new Point(x - 1, y)) && !mBlackPieces.contains(new Point(x - 1, y))) {
                    mBlackPieces.add(new Point(x - 1, y));
                    return true;
                }
            }
            if (x + 1 < MAX_LINE) {
                if (!mWhitePieces.contains(new Point(x + 1, y)) && !mBlackPieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(new Point(x + 1, y));
                    return true;
                }
            }
            if (y - 1 >= 0) {
                if (!mWhitePieces.contains(new Point(x, y - 1)) && !mBlackPieces.contains(new Point(x, y - 1))) {
                    mBlackPieces.add(new Point(x, y - 1));
                    return true;
                }
            }

            if (y + 1 < MAX_LINE) {
                if (!mWhitePieces.contains(new Point(x, y + 1)) && !mBlackPieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                }
            }

            if (x + 1 < MAX_LINE && y + 1 < MAX_LINE) {
                if (!mWhitePieces.contains(new Point(x + 1, y + 1)) && !mBlackPieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                }
            }
            if (x - 1 >= 0 && y - 1 >= 0) {
                if (!mWhitePieces.contains(new Point(x - 1, y - 1)) && !mBlackPieces.contains(new Point(x - 1, y - 1))) {
                    mBlackPieces.add(new Point(x - 1, y - 1));
                    return true;
                }
            }
            if (x + 1 < MAX_LINE && y - 1 >= 0) {
                if (!mWhitePieces.contains(new Point(x + 1, y - 1)) && !mBlackPieces.contains(new Point(x + 1, y - 1))) {
                    mBlackPieces.add(new Point(x + 1, y - 1));
                    return true;
                }
            }
            if (x - 1 >= 0 && y + 1 < MAX_LINE) {
                if (!mWhitePieces.contains(new Point(x - 1, y + 1)) && !mBlackPieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查AI棋子有四个相连的情况
     *
     * @param point
     * @return
     */
    private boolean checkBlackFourInLine(List<Point> point) {
        for (Point p : point) {
            int x = p.x;
            int y = p.y;
            boolean checkHorizontal = checkBlackFourHorizontalInLine(x, y, point);
            if (checkHorizontal) {
                return true;
            }
            boolean checkVertical = checkBlackFourVerticalInLine(x, y, point);
            if (checkVertical) {
                return true;
            }
            boolean checkLeftDiagonal = checkBlackFourLeftInLine(x, y, point);
            if (checkLeftDiagonal) {
                return true;
            }
            boolean checkRightDiagonal = checkBlackFourRightInLine(x, y, point);
            if (checkRightDiagonal) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBlackFourRightInLine(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < 4; i++) {
            if (point.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 4) {
            if (x + 4 > MAX_LINE - 1 && y - 4 < 0) {
                if (!mBlackPieces.contains(new Point(x - 1, y + 1)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                }
            } else if (x - 1 < 0 && y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x + 4, y - 4)) && !mWhitePieces.contains(new Point(x + 4, y - 4))) {
                    mBlackPieces.add(new Point(x + 4, y - 4));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x + 4, y - 4)) && !mBlackPieces.contains(new Point(x - 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x + 4, y - 4)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x + 4, y - 4) : new Point(x - 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 4, y - 4)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 1, y + 1)) && !mWhitePieces.contains(new Point(x + 4, y - 4))) {
                    mBlackPieces.add(new Point(x + 4, y - 4));
                    return true;
                }
            } else if (mBlackPieces.contains(new Point(x + 4, y - 4)) && !mBlackPieces.contains(new Point(x - 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x + 4, y - 4)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 1, y + 1));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x + 4, y - 4)) && mBlackPieces.contains(new Point(x - 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x + 4, y - 4)) && !mWhitePieces.contains(new Point(x - 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 4, y - 4));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBlackFourLeftInLine(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < 4; i++) {
            if (point.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 4) {
            if (x - 4 < 0 && y - 4 < 0) {
                if (!mBlackPieces.contains(new Point(x + 1, y + 1)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                }
            } else if (x + 1 > MAX_LINE - 1 && y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x - 4, y - 4)) && !mWhitePieces.contains(new Point(x - 4, y - 4))) {
                    mBlackPieces.add(new Point(x - 4, y - 4));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x - 4, y - 4)) && !mBlackPieces.contains(new Point(x + 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x - 4, y - 4)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x - 4, y - 4) : new Point(x + 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 4, y - 4)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 1, y + 1)) && !mWhitePieces.contains(new Point(x - 4, y - 4))) {
                    mBlackPieces.add(new Point(x - 4, y - 4));
                    return true;
                }
            } else if (mBlackPieces.contains(new Point(x - 4, y - 4)) && !mBlackPieces.contains(new Point(x + 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x - 4, y - 4)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x + 1, y + 1));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x - 4, y - 4)) && mBlackPieces.contains(new Point(x + 1, y + 1))) {
                if (!mWhitePieces.contains(new Point(x - 4, y - 4)) && !mWhitePieces.contains(new Point(x + 1, y + 1))) {
                    mBlackPieces.add(new Point(x - 4, y - 4));
                    return true;
                }
            }
        }
        return false;

    }

    private boolean checkBlackFourVerticalInLine(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < 4; i++) {
            if (point.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 4) {
            if (y - 4 < 0) {
                if (!mBlackPieces.contains(new Point(x, y + 1)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                }
            } else if (y + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x, y - 4)) && !mWhitePieces.contains(new Point(x, y - 4))) {
                    mBlackPieces.add(new Point(x, y - 4));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x, y - 4)) && !mBlackPieces.contains(new Point(x, y + 1))) {
                if (!mWhitePieces.contains(new Point(x, y - 4)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x, y - 4) : new Point(x, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x, y - 4)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                } else if (mWhitePieces.contains(new Point(x, y + 1)) && !mWhitePieces.contains(new Point(x, y - 4))) {
                    mBlackPieces.add(new Point(x, y - 4));
                    return true;
                }
            } else if (mBlackPieces.contains(new Point(x, y - 4)) && !mBlackPieces.contains(new Point(x, y + 1))) {
                if (!mWhitePieces.contains(new Point(x, y - 4)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y + 1));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x, y - 4)) && mBlackPieces.contains(new Point(x, y + 1))) {
                if (!mWhitePieces.contains(new Point(x, y - 4)) && !mWhitePieces.contains(new Point(x, y + 1))) {
                    mBlackPieces.add(new Point(x, y - 4));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBlackFourHorizontalInLine(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < 4; i++) {
            if (point.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == 4) {
            if (x - 4 < 0) {
                if (!mBlackPieces.contains(new Point(x + 1, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(new Point(x + 1, y));
                    return true;
                }
            } else if (x + 1 > MAX_LINE - 1) {
                if (!mBlackPieces.contains(new Point(x - 4, y)) && !mWhitePieces.contains(new Point(x - 4, y))) {
                    mBlackPieces.add(new Point(x - 4, y));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x - 4, y)) && !mBlackPieces.contains(new Point(x + 1, y))) {
                if (!mWhitePieces.contains(new Point(x - 4, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(Math.random() > 0.5 ? new Point(x - 4, y) : new Point(x + 1, y));
                    return true;
                } else if (mWhitePieces.contains(new Point(x - 4, y)) && !mWhitePieces.contains(new Point(x + 1, y))) {
                    mBlackPieces.add(new Point(x + 1, y));
                    return true;
                } else if (mWhitePieces.contains(new Point(x + 1, y)) && !mWhitePieces.contains(new Point(x - 4, y))) {
                    mBlackPieces.add(new Point(x - 4, y));
                    return true;
                }
            } else if (mBlackPieces.contains(new Point(x + 4, y)) && !mBlackPieces.contains(new Point(x - 1, y))) {
                if (!mWhitePieces.contains(new Point(x + 4, y)) && !mWhitePieces.contains(new Point(x - 1, y))) {
                    mBlackPieces.add(new Point(x - 1, y));
                    return true;
                }
            } else if (!mBlackPieces.contains(new Point(x + 4, y)) && mBlackPieces.contains(new Point(x - 1, y))) {
                if (!mWhitePieces.contains(new Point(x + 4, y)) && !mWhitePieces.contains(new Point(x - 1, y))) {
                    mBlackPieces.add(new Point(x + 4, y));
                    return true;
                }
            }
        }
        return false;
    }

}
