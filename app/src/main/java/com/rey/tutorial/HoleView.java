package com.rey.tutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Rey on 1/12/2016.
 */
public class HoleView extends View{

    private HoleDrawer mDrawer;

    public HoleView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public HoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public HoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HoleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setWillNotDraw(false);
    }

    public void setDrawer(HoleDrawer drawer){
        mDrawer = drawer;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mDrawer != null)
            mDrawer.draw(canvas, getWidth(), getHeight(), getWidth() / 2, getHeight() / 2);
    }

}
