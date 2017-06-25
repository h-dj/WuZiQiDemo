package com.example.h_dj.wuziqidemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.h_dj.wuziqidemo.R;
import com.example.h_dj.wuziqidemo.utils.AIAnalyzeUtils;
import com.example.h_dj.wuziqidemo.utils.CheckGameOver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H_DJ on 2017/6/8.
 */

public class WuZiQiPanle extends View {


    private static final int THINKING_AI = 1;
    /**
     * 画棋盘的宽
     */
    private int mPanleWidth;
    /**
     * 棋盘线的高
     */
    private float mLineHeight;

    /**
     * 最大棋盘线
     */
    private final int MAX_LINE = 10;
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 棋子的宽高比列
     */
    private float ratoPieceLineHeight = 3 * 1.0f / 4;
    /**
     * 白棋
     */
    private Bitmap mWhiteBitmap;
    private ArrayList<Point> mWhitePieces;
    /**
     * 黑棋
     */
    private Bitmap mBlackBitmap;
    private ArrayList<Point> mBlackPieces;

    /**
     * 判断是否到白棋
     */
    private static boolean isWhitePiece = true;
    /**
     * 是否结束了游戏
     */
    private boolean isGameOver;
    /**
     * 是否是白棋胜
     */
    private boolean isWhiteWin;
    /**
     * 多少个棋子可以胜利
     */
    private final int WIN_IN_LINE_NUM = 5;

    /**
     * 是否停止游戏
     */
    private boolean isPause = false;

    private int role = 1;//0--人人对战，1--人机对战
    private boolean showWinMsg;//以显示过
    private int pieceType = 0;//棋子类型--0白；1黑

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case THINKING_AI:
                    boolean isPlaceTheBlackPiece = mAiAnalyzeUtils.checkAIPlaceThePiece();
                    isWhitePiece = isPlaceTheBlackPiece;
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private CheckGameOver mCheckGameOver;
    private AIAnalyzeUtils mAiAnalyzeUtils;

    /**
     * @param context
     * @param attrs
     */
    public WuZiQiPanle(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置背景
        //  setBackgroundColor(0x44ff0000);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        /**
         * 初始化画笔
         */
        mPaint = new Paint();
        mPaint.setColor(0xff000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        /**
         * 初始化黑白棋图片
         */
        mBlackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhiteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mWhitePieces = new ArrayList<>();
        mBlackPieces = new ArrayList<>();
        /**
         * 检查gameover的工具类
         */
        mCheckGameOver = CheckGameOver.newInstance();
        /**
         * 分析AI下棋算法工具
         */
        mAiAnalyzeUtils = AIAnalyzeUtils.newInstance(mBlackPieces, mWhitePieces);
    }

    /**
     * 保存super.onSaveInstanceState()的键
     */
    private final static String INSTANCE = "instance";
    /**
     * 保存游戏结束
     */
    private final static String INSTANCE_GAMEOVER = "instance_gameOver";
    /**
     * 保存白棋数组
     */
    private final static String INSTANCE_WHITE_ARRAYS = "instance_white_arrays";
    /**
     * 保存黑棋数组
     */
    private final static String INSTANCE_BLACK_ARRAYS = "instance_black_arrays";
    /**
     * 保存轮到谁下棋
     */
    private final static String INSTANCE_IS_WHITE_PIECE = "instance_is_white_piece";

    /**
     * 暂停游戏
     */
    private final static String INSTANCE_IS_PAUSE = "instance_pause";

    /**
     * 保存数据
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INSTANCE_IS_PAUSE, isPause);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAYS, mWhitePieces);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAYS, mBlackPieces);
        bundle.putBoolean(INSTANCE_GAMEOVER, isGameOver);
        bundle.putBoolean(INSTANCE_IS_WHITE_PIECE, isWhitePiece);
        return bundle;
    }

    /**
     * 重新游戏
     */
    public void restart() {
        showWinMsg = false;
        isPause = false;
        isGameOver = false;
        isWhiteWin = true;
        mBlackPieces.clear();
        mWhitePieces.clear();
        invalidate();
    }

    /**
     * 悔棋
     */
    public void Undo() {
        if (isWhitePiece) {
            int size = mWhitePieces.size();
            if (size < 1) {
                return;
            }
            mWhitePieces.remove(size - 1);
        } else {
            int size = mBlackPieces.size();
            if (size < 1) {
                return;
            }
            mBlackPieces.remove(mBlackPieces.size() - 1);
        }
        isWhitePiece = !isWhitePiece;
        invalidate();
    }

    /**
     * 暂停游戏
     */
    public void pauseGame(boolean isPause) {
        this.isPause = isPause;
        if (mOnGameStateListener != null) {
            mOnGameStateListener.isPause(isPause);
        }
    }

    public void setPieces(boolean isWhite, List<Point> piece) {
        if (isWhite && piece != null) {
            mWhitePieces.clear();
            mWhitePieces.addAll(piece);
        } else if (!isWhite && piece != null) {
            mBlackPieces.clear();
            mBlackPieces.addAll(piece);
        }
    }

    public void setPieceType(int type) {
        pieceType = type;
    }

    /**
     * 设置轮到谁
     *
     * @param isWhitePiece
     */
    public void setTureToWhite(boolean isWhitePiece) {
        this.isWhitePiece = isWhitePiece;
        Log.e(this.getClass().getSimpleName(), "setTureToWhite: " + this.isWhitePiece);
    }

    /**
     * 设置对战模式
     *
     * @param role //0--人人对战，1--人机对战
     */
    public void setPlayMode(int role) {
        this.role = role;
    }

    /**
     * 恢复数据
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mWhitePieces = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAYS);
            mBlackPieces = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAYS);
            isGameOver = bundle.getBoolean(INSTANCE_GAMEOVER);
            isWhitePiece = bundle.getBoolean(INSTANCE_IS_WHITE_PIECE);
            isPause = bundle.getBoolean(INSTANCE_IS_PAUSE);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthPanle = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heigthPanle = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        /**
         * 如果宽不确定,就以高测量;反之亦然
         */
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthPanle = heigthPanle;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            heigthPanle = widthPanle;
        }

        int width = Math.min(widthPanle, heigthPanle);
        setMeasuredDimension(width, width);
    }

    /**
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanleWidth = w;
        mLineHeight = mPanleWidth * 1.0f / MAX_LINE;

        /**
         * 设置棋子的大小
         */
        int pieceWH = (int) (ratoPieceLineHeight * mLineHeight);
        mBlackBitmap = Bitmap.createScaledBitmap(mBlackBitmap, pieceWH, pieceWH, false);
        mWhiteBitmap = Bitmap.createScaledBitmap(mWhiteBitmap, pieceWH, pieceWH, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画棋盘
         */
        drawPanle(canvas);
        /**
         * 画棋子
         */
        drawPieces(canvas);

        /**
         * 判断是否结束
         */
        checkGameOver();
    }

    private void checkGameOver() {
        if (mOnGameStateListener != null) {
            mOnGameStateListener.isGameOver(isGameOver);
        }
        if (isGameOver) {
            return;
        }
        //判断白棋
        boolean whiteWin = mCheckGameOver.checkWhiteWin(mWhitePieces);
        boolean blackWin = mCheckGameOver.checkWhiteWin(mBlackPieces);
        String msg = null;
        if (whiteWin || blackWin) {
            isGameOver = true;
            isWhiteWin = whiteWin;
            msg = isWhiteWin ? "白棋胜利" : "黑棋胜利";
            Log.e("tsg", msg);
        } else if (mCheckGameOver.checkDeuce(mBlackPieces, mWhitePieces)) {
            //判断平手
            isGameOver = true;
            msg = "平手";
        }
        if (mOnGameStateListener != null && isGameOver && !showWinMsg) {
            showWinMsg = true;
            mOnGameStateListener.gameOverResult(msg);
        }
    }

    /**
     * 画棋子
     *
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mBlackPieces.size(); i < n; i++) {
            Point point = mBlackPieces.get(i);
            canvas.drawBitmap(mBlackBitmap, (point.x + (1 - ratoPieceLineHeight) / 2) * mLineHeight, (point.y + (1 - ratoPieceLineHeight) / 2) * mLineHeight, null);
            //  Log.e("drawBitmap", point.x + (1 - ratoPieceLineHeight / 2) * mLineHeight + " : " + point.y + (1 - ratoPieceLineHeight / 2) * mLineHeight);

        }
        for (int i = 0, n = mWhitePieces.size(); i < n; i++) {
            Point point = mWhitePieces.get(i);
            canvas.drawBitmap(mWhiteBitmap, (point.x + (1 - ratoPieceLineHeight) / 2) * mLineHeight, (point.y + (1 - ratoPieceLineHeight) / 2) * mLineHeight, null);
            // Log.e("drawBitmap", point.x + (1 - ratoPieceLineHeight / 2) * mLineHeight + " : " + point.y + (1 - ratoPieceLineHeight / 2) * mLineHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //如果游戏结束就不能再下棋了
        if (isGameOver) {
            return false;
        }
        if (action == MotionEvent.ACTION_UP) {
            int pieceX = (int) event.getX();
            int pieceY = (int) event.getY();
            Log.e("onTouchEvent", pieceX + " : " + pieceY + "mLineHeight " + mLineHeight);
            Point point = getPoint(pieceX, pieceY);
            Log.e("Point", point.x + " : " + point.y);
            /**
             * 判断该位置是否已经有棋子;有则不放棋子
             */
            if (mBlackPieces.contains(point) || mWhitePieces.contains(point)) {
                return false;
            }
            Log.e("isWhitePiece", ":" + isWhitePiece);

            Log.e(this.getClass().getSimpleName(), "onTouchEvent: " + pieceType);
            //判断是否轮到白棋下
            if (isWhitePiece && pieceType == 0) {
                mWhitePieces.add(point);
                isWhitePiece = false;
                if (role == 1 && !isGameOver) {
                    //AI判断下棋子的位置
                    mHandler.sendEmptyMessageDelayed(THINKING_AI, 1000);
                }
            } else if (!isWhitePiece && pieceType == 1) {
                if (role == 0) {
                    Log.e(this.getClass().getSimpleName(), "onTouchEvent: 黑棋" + isWhitePiece);
                    mBlackPieces.add(point);
                    isWhitePiece = true;
                }
            }
            if (mOnGameStateListener != null) {
                mOnGameStateListener.gameState(point, isWhitePiece);
            }
            //最后调用重新绘制
            invalidate();
        }
        return true;
    }

    /**
     * 返回点击的位置
     *
     * @param pieceX
     * @param pieceY
     * @return
     */
    private Point getPoint(int pieceX, int pieceY) {
        return new Point((int) (pieceX / mLineHeight), (int) (pieceY / mLineHeight));
    }

    /**
     * 绘制棋盘
     *
     * @param canvas
     */
    private void drawPanle(Canvas canvas) {
        int w = mPanleWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            //开始位置
            int startX = (int) (mLineHeight / 2);
            //结束位置
            int endX = (int) (w - lineHeight / 2);
            //y开始位置
            int y = (int) ((0.5 + i) * lineHeight);
//            Log.e("drawPanle:", startX + " : " + endX + "");
            //画竖线
            canvas.drawLine(startX, y, endX, y, mPaint);
            //画横线
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
        invalidate();
    }

    private OnGameStateListener mOnGameStateListener;

    public interface OnGameStateListener {

        void isPause(boolean isPause);//暂停

        void isGameOver(boolean isGameOver);//停止

        void gameOverResult(String winer);//胜利结果

        void gameState(Point point, boolean isWhitePiece);
    }

    /**
     * 设置监听接口
     *
     * @param listener
     */
    public void setOnGameStateListener(OnGameStateListener listener) {
        mOnGameStateListener = listener;
    }

    public void clearCache() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mCheckGameOver != null) {
            mCheckGameOver.destory();
            mCheckGameOver = null;
        }
        if (mAiAnalyzeUtils != null) {
            mAiAnalyzeUtils.destory();
            mAiAnalyzeUtils = null;
        }
    }
}
