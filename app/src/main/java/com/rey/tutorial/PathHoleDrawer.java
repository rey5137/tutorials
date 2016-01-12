package com.rey.tutorial;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Rey on 1/12/2016.
 */
public class PathHoleDrawer extends HoleDrawer{

    private Path mPath;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;
    private int mX;
    private int mY;

    public PathHoleDrawer(int backgroundColor, int holeRadius) {
        super(backgroundColor, holeRadius);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(backgroundColor);
    }

    private void preparePath(int width, int height, int x, int y){
        if(mWidth != width || mHeight != height || mX != x || mY != y){
            mWidth = width;
            mHeight = height;
            mX = x;
            mY = y;

            mPath.reset();
            mPath.setFillType(Path.FillType.WINDING);
            mPath.addRect(0, 0, mWidth, mHeight, Path.Direction.CW);
            mPath.addCircle(mX, mY, mHoleRadius, Path.Direction.CCW);
        }
    }

    @Override
    void draw(Canvas c, int width, int height, int x, int y) {
        preparePath(width, height, x, y);
        c.drawPath(mPath, mPaint);
    }

}
