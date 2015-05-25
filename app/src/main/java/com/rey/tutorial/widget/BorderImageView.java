package com.rey.tutorial.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.rey.tutorial.R;
import com.rey.tutorial.drawable.BorderDrawable;

/**
 * Created by Rey on 5/24/2015.
 */
public class BorderImageView extends ImageView{

    BorderDrawable mBorder;

    public BorderImageView(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0, 0);
    }

    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setWillNotDraw(false);
        mBorder = new BorderDrawable(context.getResources().getColor(R.color.primary), getPaddingLeft(), getPaddingLeft() / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorder.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBorder.draw(canvas);
    }
}
