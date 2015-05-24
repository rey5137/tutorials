package com.rey.tutorial.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by Rey on 5/24/2015.
 */
public class BorderDrawable extends Drawable {

    Paint mPaint;
    int mColor;
    int mBorderWidth;
    int mBorderRadius;

    RectF mRect;
    Path mPath;

    public BorderDrawable(int color, int borderWidth, int borderRadius){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mRect = new RectF();

        mColor = color;
        mBorderWidth = borderWidth;
        mBorderRadius = borderRadius;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mPath.reset();

        mPath.addRect(bounds.left, bounds.top, bounds.right, bounds.bottom, Path.Direction.CW);
        mRect.set(bounds.left + mBorderWidth, bounds.top + mBorderWidth, bounds.right - mBorderWidth, bounds.bottom - mBorderWidth);
        mPath.addRoundRect(mRect, mBorderRadius, mBorderRadius, Path.Direction.CW);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(mColor);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
