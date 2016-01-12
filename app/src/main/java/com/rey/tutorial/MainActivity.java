package com.rey.tutorial;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by Rey on 5/24/2015.
 */
public class MainActivity extends FragmentActivity{

    private HoleView mHoleView;
    private int mBackgroundColor;
    private int mHoleRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mHoleView = (HoleView)findViewById(R.id.hv);
        mHoleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHoleView.setDrawer(null);
                mHoleView.setVisibility(View.GONE);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.bt_bitmap:
                        onBitmapClick();
                        break;
                    case R.id.bt_path:
                        onPathClick();
                        break;
                    case R.id.bt_gradient:
                        onGradientClick();
                        break;
                }
            }
        };

        findViewById(R.id.bt_bitmap).setOnClickListener(listener);
        findViewById(R.id.bt_path).setOnClickListener(listener);
        findViewById(R.id.bt_gradient).setOnClickListener(listener);

        mBackgroundColor = 0x80000000;

        final LinearLayout ll_container = (LinearLayout)findViewById(R.id.ll_container);

        ll_container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ll_container.getViewTreeObserver().removeOnPreDrawListener(this);

                int width = 0;
                int height = 0;
                for (int i = 0, count = ll_container.getChildCount(); i < count; i++) {
                    View child = ll_container.getChildAt(i);
                    width = Math.max(width, child.getWidth());
                    height += child.getHeight();
                }

                mHoleRadius = (int)(Math.sqrt(width * width + height * height) / 2 + 16);

                return true;
            }
        });
    }

    void onBitmapClick(){
        mHoleView.setDrawer(new BitmapHoleDrawer(mBackgroundColor, mHoleRadius));
        mHoleView.setVisibility(View.VISIBLE);
    }

    void onPathClick(){
        mHoleView.setDrawer(new PathHoleDrawer(mBackgroundColor, mHoleRadius));
        mHoleView.setVisibility(View.VISIBLE);
    }

    void onGradientClick(){
        mHoleView.setDrawer(new GradientHoleDrawer(mBackgroundColor, mHoleRadius));
        mHoleView.setVisibility(View.VISIBLE);
    }
}
