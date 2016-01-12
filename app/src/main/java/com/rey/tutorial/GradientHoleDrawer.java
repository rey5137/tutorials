package com.rey.tutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/**
 * Created by Rey on 1/12/2016.
 */
public class GradientHoleDrawer extends HoleDrawer{

    private static final float[] GRADIENT_STOPS = new float[]{0f, 0.99f, 1f};
    private RadialGradient mShader;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;
    private int mX;
    private int mY;

    public GradientHoleDrawer(int backgroundColor, int holeRadius) {
        super(backgroundColor, holeRadius);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void prepareShader(int width, int height, int x, int y){
        if(mWidth != width || mHeight != height || mX != x || mY != y){
            mWidth = width;
            mHeight = height;
            mX = x;
            mY = y;

            mShader = new RadialGradient(mX, mY, mHoleRadius,new int[]{Color.TRANSPARENT, Color.TRANSPARENT, mBackgroundColor}, GRADIENT_STOPS, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    @Override
    void draw(Canvas c, int width, int height, int x, int y) {
        prepareShader(width, height, x, y);
        c.drawRect(0, 0, width, height, mPaint);
    }

}
