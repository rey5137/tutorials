package com.rey.tutorial;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

/**
 * Created by Rey on 5/24/2015.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String[] URLs = new String[]{
            "http://lorempixel.com/512/512/nature/test1/",
            "http://lorempixel.com/512/512/nature/test2/",
            "http://lorempixel.com/512/512/nature/test3/",
            "http://lorempixel.com/512/512/nature/test4/",
            "http://lorempixel.com/512/512/nature/test5/",
            "http://lorempixel.com/512/512/nature/test6/",
            "http://lorempixel.com/512/512/nature/test7/",
            "http://lorempixel.com/512/512/nature/test8/",
            "http://lorempixel.com/512/512/nature/test9/",
            "http://lorempixel.com/512/512/nature/test10/",
    };

    private LinearLayout mPicassoLayout;
    private LinearLayout mFrescoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPicassoLayout = (LinearLayout)findViewById(R.id.ll_picasso);
        mFrescoLayout = (LinearLayout)findViewById(R.id.ll_fresco);

        Button bt = (Button)findViewById(R.id.bt_picasso);
        bt.setOnClickListener(this);
        bt = (Button)findViewById(R.id.bt_fresco);
        bt.setOnClickListener(this);

        init();
    }

    private void init(){
        for(int i = 0; i < URLs.length; i++){
            View v = LayoutInflater.from(this).inflate(R.layout.item_picasso, null);
            mPicassoLayout.addView(v, new LinearLayout.LayoutParams(512, 512));
        }

        for(int i = 0; i < URLs.length; i++){
            View v = LayoutInflater.from(this).inflate(R.layout.item_fresco, null);
            mFrescoLayout.addView(v, new LinearLayout.LayoutParams(512, 512));
        }

        mPicassoLayout.setVisibility(View.INVISIBLE);
        mFrescoLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_picasso:
                testPicasso();
                break;
            case R.id.bt_fresco:
                testFresco();
                break;
        }
    }

    private void testPicasso(){
        mPicassoLayout.setVisibility(View.VISIBLE);
        mFrescoLayout.setVisibility(View.INVISIBLE);

        for(int i = 0; i < URLs.length; i++){
            ImageView iv = (ImageView)mPicassoLayout.getChildAt(i);
            Picasso.with(getApplicationContext())
                    .load(URLs[i])
                    .into(iv);
        }
    }

    private void testFresco(){
        mFrescoLayout.setVisibility(View.VISIBLE);
        mPicassoLayout.setVisibility(View.INVISIBLE);

        for(int i = 0; i < URLs.length; i++){
            SimpleDraweeView sdv = (SimpleDraweeView)mFrescoLayout.getChildAt(i);
            sdv.setImageURI(Uri.parse(URLs[i]));
        }
    }
}
