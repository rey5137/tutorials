package com.rey.tutorial.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
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
public class StateBorderDrawable extends Drawable {

    Paint mPaint;
    ColorStateList mColorStateList;
    int mColor;
    int mBorderWidth;
    int mBorderRadius;

    RectF mRect;
    Path mPath;

    public StateBorderDrawable(ColorStateList colorStateList, int borderWidth, int borderRadius){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mRect = new RectF();

        mColorStateList = colorStateList;
        mColor = mColorStateList.getDefaultColor();
        mBorderWidth = borderWidth;
        mBorderRadius = borderRadius;
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int color = mColorStateList.getColorForState(state, mColor);
        if(mColor != color){
            mColor = color;
            invalidateSelf();
            return true;
        }

        return false;
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
