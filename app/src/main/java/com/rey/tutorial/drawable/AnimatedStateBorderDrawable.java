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
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/**
 * Created by Rey on 5/24/2015.
 */
public class AnimatedStateBorderDrawable extends Drawable implements Animatable{

    private boolean mRunning = false;
    private long mStartTime;
    private int mAnimDuration;

    Paint mPaint;
    ColorStateList mColorStateList;
    int mPrevColor;
    int mMiddleColor;
    int mCurColor;
    int mBorderWidth;
    int mBorderRadius;

    RectF mRect;
    Path mPath;

    private static final long FRAME_DURATION = 1000 / 60;

    public AnimatedStateBorderDrawable(ColorStateList colorStateList, int borderWidth, int borderRadius, int duration){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mRect = new RectF();

        mColorStateList = colorStateList;
        mCurColor = mColorStateList.getDefaultColor();
        mPrevColor = mCurColor;
        mBorderWidth = borderWidth;
        mBorderRadius = borderRadius;
        mAnimDuration = duration;
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int color = mColorStateList.getColorForState(state, mCurColor);

        if(mCurColor != color){
            if(mAnimDuration > 0){
                mPrevColor = isRunning() ? mMiddleColor : mCurColor;
                mCurColor = color;
                start();
            }
            else{
                mPrevColor = color;
                mCurColor = color;
                invalidateSelf();
            }
            return true;
        }

        return false;
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        stop();
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
        mPaint.setColor(isRunning() ? mMiddleColor : mCurColor);
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

    private void resetAnimation(){
        mStartTime = SystemClock.uptimeMillis();
        mMiddleColor = mPrevColor;
    }

    @Override
    public void start() {
        resetAnimation();
        scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
        invalidateSelf();
    }

    @Override
    public void stop() {
        mRunning = false;
        unscheduleSelf(mUpdater);
        invalidateSelf();
    }

    @Override
    public void scheduleSelf(Runnable what, long when) {
        mRunning = true;
        super.scheduleSelf(what, when);
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {
            update();
        }

    };

    private void update(){
        long curTime = SystemClock.uptimeMillis();
        float progress = Math.min(1f, (float) (curTime - mStartTime) / mAnimDuration);
        mMiddleColor = getMiddleColor(mPrevColor, mCurColor, progress);

        if(progress == 1f)
            mRunning = false;

        if(isRunning())
            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);

        invalidateSelf();
    }

    private int getMiddleValue(int prev, int next, float factor){
        return Math.round(prev + (next - prev) * factor);
    }

    public int getMiddleColor(int prevColor, int curColor, float factor){
        if(prevColor == curColor)
            return curColor;

        if(factor == 0f)
            return prevColor;
        else if(factor == 1f)
            return curColor;

        int a = getMiddleValue(Color.alpha(prevColor), Color.alpha(curColor), factor);
        int r = getMiddleValue(Color.red(prevColor), Color.red(curColor), factor);
        int g = getMiddleValue(Color.green(prevColor), Color.green(curColor), factor);
        int b = getMiddleValue(Color.blue(prevColor), Color.blue(curColor), factor);

        return Color.argb(a, r, g, b);
    }
}
