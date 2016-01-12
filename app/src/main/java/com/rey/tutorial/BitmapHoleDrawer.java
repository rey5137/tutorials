package com.rey.tutorial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by Rey on 1/12/2016.
 */
public class BitmapHoleDrawer extends HoleDrawer{

    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private int mX;
    private int mY;

    public BitmapHoleDrawer(int backgroundColor, int holeRadius) {
        super(backgroundColor, holeRadius);
    }

    private void prepareBitmap(int width, int height, int x, int y){
        if(mWidth != width || mHeight != height || mX != x || mY != y){
            mWidth = width;
            mHeight = height;
            mX = x;
            mY = y;

            mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            mBitmap.eraseColor(Color.TRANSPARENT);

            Canvas c = new Canvas(mBitmap);
            c.drawColor(mBackgroundColor);

            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStyle(Paint.Style.FILL);
            p.setColor(0xFFFFFFFF);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            c.drawCircle(mX, mY, mHoleRadius, p);
        }
    }

    @Override
    void draw(Canvas c, int width, int height, int x, int y) {
        prepareBitmap(width, height, x, y);
        if(mBitmap != null)
            c.drawBitmap(mBitmap, 0, 0, null);
    }

}
