package com.example.testfor360.Managers;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.testfor360.R;
import com.example.testfor360.Views.FloatCricleView;
import com.example.testfor360.Views.FloatMenuLayout;

import java.lang.reflect.Field;

/**
 * Created by 蔡大爷 on 2016/8/17.
 */
public class FloatViewManager {

    private static FloatViewManager instance;

    private FloatCricleView floatCricleView;
    private FloatMenuLayout floatMenuLayout;

    private WindowManager.LayoutParams params;
    private WindowManager wm;

    Context context;

    float startX;
    float startY;
    float currentX;
    float currentY;

    float sX, sY;

    private View.OnTouchListener floatCricleViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startX = event.getRawX();
                    startY = event.getRawY();

                    sX = startX;
                    sY = startY;
                    break;

                case MotionEvent.ACTION_MOVE:

                    currentX = event.getRawX();
                    currentY = event.getRawY();
                    float dx = currentX - startX;
                    float dy = currentY - startY;

                    params.x += dx;
                    params.y += dy;

                    wm.updateViewLayout(floatCricleView, params);

                    startX = currentX;
                    startY = currentY;

                    break;
                case MotionEvent.ACTION_UP:

                    if (currentX > getScreenWidth() / 2) {

                        params.x = (int) getScreenWidth();
                    } else {
                        params.x = 0;
                    }
                    wm.updateViewLayout(floatCricleView, params);

                    if (Math.abs(currentX - sX) > 8 || Math.abs(currentY - sY) > 8) {
                        return true;
                    }
                    break;
            }
            return false;
        }
    };

    private int getScreenWidth() {
        return wm.getDefaultDisplay().getWidth();
    }

    private int getScreenHeight() {
        return wm.getDefaultDisplay().getHeight();
    }

    //状态栏的高
    private int getStateHeight() {

        Class<?> c = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }


    private FloatViewManager(final Context context) {

        this.context = context;
        Log.e("TestFor360", "###FloatViewManager_construct_start###");
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        floatCricleView = new FloatCricleView(context);
        floatMenuLayout = new FloatMenuLayout(context);

        floatCricleView.setOnTouchListener(floatCricleViewOnTouchListener);
        floatCricleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wm.removeView(floatCricleView);
                showFloatMenuView();
                floatMenuLayout.openAnimation();
                Log.e("TestFor3601", "###onClick###");
            }
        });

        floatMenuLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                floatMenuLayout.closeAnimation();
                wm.removeView(floatMenuLayout);
                showFloatCricleView();
                Log.e("TestFor3601", "###closeAnimation###");

                return false;
            }
        });

        LinearLayout view = (LinearLayout) floatMenuLayout.findViewById(R.id.menuLayout);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public static FloatViewManager getInstace(Context context) {

        if (instance == null) {
            synchronized (FloatViewManager.class) {
                if (instance == null) {
                    Log.e("TestFor360", "###getInstace_FloatViewManager###");
                    instance = new FloatViewManager(context);
                }
            }
        }
        return instance;

    }

    public void showFloatCricleView() {

        if (params == null) {
            Log.e("TestFor360", "###showFloatCricleView11###");
            params = new WindowManager.LayoutParams();
            params.width = getScreenWidth();
            params.height = getScreenHeight();
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 0;
            params.y = 0;
            //打电话的类型
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            params.format = PixelFormat.RGBA_8888;
            wm.addView(floatCricleView, params);
            Log.e("TestFor360", "###showFloatCricleView21###");
        } else {
            Log.e("TestFor360", "###showFloatCricleView22###");
            wm.addView(floatCricleView, params);
        }

    }

    public void showFloatMenuView() {


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = getScreenWidth();
        layoutParams.height = getScreenHeight() - getStateHeight();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        layoutParams.x = 0;
        layoutParams.y = 0;
        //打电话的类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.RGBA_8888;
        wm.addView(floatMenuLayout, params);
        Log.e("TestFor360", "###showFloatMenuView###1");


    }

}
