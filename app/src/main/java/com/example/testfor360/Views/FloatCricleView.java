package com.example.testfor360.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 蔡大爷 on 2016/8/17.
 */
public class FloatCricleView extends View {

    public int width = 150;
    public int height = 150;

    private Paint criclePaint;
    private Paint textPaint;
    private String textContent = "50%";



    public FloatCricleView(Context context) {
        super(context);
        initPaints();
    }


    public FloatCricleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public FloatCricleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint.FontMetrics metrics = textPaint.getFontMetrics();

        float textLeft = (width / 2 - textPaint.measureText(textContent) / 2);
        float textTop = height / 2 - (metrics.ascent + metrics.descent) / 2;

        canvas.drawCircle(width / 2, height / 2, width / 2, criclePaint);
        canvas.drawText(textContent, textLeft, textTop, textPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int temp = MeasureSpec.getSize(widthMeasureSpec);
//
//        heightMeasureSpec = widthMeasureSpec;

//        width = temp;
//        height = temp;

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width,height);
    }


    private void initPaints() {

        criclePaint = new Paint();
        criclePaint.setAntiAlias(true);
        criclePaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);


    }

}
