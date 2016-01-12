package com.rey.tutorial;

import android.graphics.Canvas;

/**
 * Created by Rey on 1/12/2016.
 */
public abstract class HoleDrawer{
    protected int mBackgroundColor;
    protected int mHoleRadius;

    public HoleDrawer(int backgroundColor, int holeRadius){
        mBackgroundColor = backgroundColor;
        mHoleRadius = holeRadius;
    }

    abstract void draw(Canvas c, int width, int height, int x, int y);
}
