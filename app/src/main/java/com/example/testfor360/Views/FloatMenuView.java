package com.example.testfor360.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 蔡大爷 on 2016/8/17.
 */
public class FloatMenuView extends View {

    int width = 200;
    int height = 200;
    //100进制的progress;
    int progress;

    int currentProgress = 100;
    int realProgress = 100;
    int allProgress = width;

    private static double PI = 3.14;

    int counts = 0;

    Paint backPaint;
    Paint pathPaint;
    Paint textPaint;

    Path path;

    GestureDetector detector;

    private SingleTapRunnable singleRunnable;
    private DoubleTapRunnable doubleRunnable;


    Handler MyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public FloatMenuView(Context context) {
        super(context);
        init();
    }


    public FloatMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        path = new Path();

        backPaint = new Paint();
        pathPaint = new Paint();
        textPaint = new Paint();

        detector = new GestureDetector(new MyGestureDetectorListener());

        singleRunnable = new SingleTapRunnable();
        doubleRunnable = new DoubleTapRunnable();


        backPaint.setAntiAlias(true);
        backPaint.setColor(Color.BLUE);

        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.GREEN);
        //设置绘画重叠区域
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25);

        setClickable(true);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                return detector.onTouchEvent(event);
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawRect(0, 0, width, height, backPaint);

        canvas.drawCircle(width / 2, height / 2, width / 2, backPaint);
        /*
        *   path 路径下的变量Y： pathY
        *   振动水波的峰值 ： peakY
        * */

        float pathY = allProgress - currentProgress;
        int peakY = counts / 4;


        progress = (int) (currentProgress * 100.0 / allProgress);

        int angle = (int) (progress / 100.0 * 180);
        float startAngle = 0;
        float sweeplAngle = 180;

        RectF oval = new RectF(0, 0, width, height);

        if (angle < 90) {
            startAngle = 90 - angle;
            sweeplAngle = angle * 2;

        } else {

        }

        int w = (int) (Math.sin(angle * Math.PI / 180.0) * 2 * width / 2);
        w = Math.abs(w);
        int c = w / 40 ;

        Log.e("TestFor360", "sin30: " + Math.sin(30.0) + "ref :" + (Math.sin(30.0 * Math.PI / 180.0)));

        Log.e("TestFor360", "###startAngle### : " + startAngle + "###sweeplAngle### : " + sweeplAngle + "###w### : " + w + "###c### : " + c);
        path.reset();
        path.arcTo(oval, startAngle, sweeplAngle);

//        path.moveTo(0, pathY);
//        path.moveTo(width, pathY);
//        path.lineTo(width, height);
//        path.lineTo(0, height);
//        path.lineTo(0, pathY);

        if (counts % 2 == 0) {
            for (int i = 0; i < c; i++) {
                path.rQuadTo(10, peakY, 20, 0);
                path.rQuadTo(10, -peakY, 20, 0);

            }
        } else {
            for (int i = 0; i < c; i++) {
                path.rQuadTo(10, -peakY, 20, 0);
                path.rQuadTo(10, peakY, 20, 0);

            }
        }
        path.close();
        canvas.drawPath(path, pathPaint);

        String text = progress + "%";
        Paint.FontMetrics metrics = textPaint.getFontMetrics();

        float dy = -(metrics.descent + metrics.ascent);
        float textX = width / 2 - textPaint.measureText(text) / 2;
        float textY = height / 2 + dy;

        canvas.drawText(text, textX, textY, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(width, height);
    }

    private void startSingleTapAnim() {

        counts = 40;
        MyHandler.postDelayed(singleRunnable, 150);
    }

    private void startDoubleTapAnim() {

        startSingleTapAnim();

        currentProgress = 0;
        MyHandler.postDelayed(doubleRunnable, 50);
    }


    class SingleTapRunnable implements Runnable {
        @Override
        public void run() {

            counts--;
            if (counts > 0) {
                Log.e("TestFor360", "###counts### : " + counts);
                invalidate();
                MyHandler.postDelayed(singleRunnable, 150);
            } else {
                MyHandler.removeCallbacks(singleRunnable);
            }

        }
    }

    class DoubleTapRunnable implements Runnable {
        @Override
        public void run() {

            currentProgress++;

            if (currentProgress != realProgress) {

                invalidate();
                postDelayed(doubleRunnable, 50);

            } else {

                removeCallbacks(doubleRunnable);
            }


        }
    }

    class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            startDoubleTapAnim();
            Log.e("TestFor360", "###onDoubleTap###");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            startSingleTapAnim();
            Log.e("TestFor360", "###onSingleTapConfirmed###");
            return super.onSingleTapConfirmed(e);
        }
    }


}
