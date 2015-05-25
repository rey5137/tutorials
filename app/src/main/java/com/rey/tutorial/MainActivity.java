package com.rey.tutorial;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by Rey on 5/24/2015.
 */
public class MainActivity extends FragmentActivity{

    VerticalViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        vp = (VerticalViewPager)findViewById(R.id.main_vp);

        Item[] items = new Item[]{
            new Item(R.drawable.img1, "Title 1", "Sub title 1"),
                new Item(R.drawable.img2, "Title 2", "Sub title 2"),
                new Item(R.drawable.img3, "Title 3", "Sub title 3"),
                new Item(R.drawable.img4, "Title 4", "Sub title 4"),
                new Item(R.drawable.img5, "Title 5", "Sub title 5"),
                new Item(R.drawable.img1, "Title 6", "Sub title 6"),
                new Item(R.drawable.img2, "Title 7", "Sub title 7"),
                new Item(R.drawable.img3, "Title 8", "Sub title 8"),
                new Item(R.drawable.img4, "Title 9", "Sub title 9"),
                new Item(R.drawable.img5, "Title 10", "Sub title 10"),
        };

        ImageAdapter imageAdapter = new ImageAdapter();
        imageAdapter.addItems(items);
        vp.setAdapter(imageAdapter);
        vp.setPageTransformer(true, new DepthPageTransformer());
    }

    class ImageAdapter extends RecyclerPagerAdapter{
        Item[] mItems;

        public void addItems(Item[] items){
            mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        protected Object getItem(int position) {
            return mItems[position];
        }

        @Override
        protected View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Holder h;
            if(v != null)
                h = (Holder)v.getTag(R.id.main_vp);
            else{
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_img, parent, false);
                h = new Holder();
                v.setTag(R.id.main_vp, h);
                h.iv = (ImageView)v.findViewById(R.id.img_iv);
                h.tv_title = (TextView)v.findViewById(R.id.img_tv_title);
                h.tv_sub = (TextView)v.findViewById(R.id.img_tv_sub);
            }

            Item item = (Item)getItem(position);
            h.iv.setImageResource(item.imgId);
            h.tv_title.setText(item.title);
            h.tv_sub.setText(item.sub);

            return v;
        }

    }

    class Holder{
        ImageView iv;
        TextView tv_title;
        TextView tv_sub;
    }

    class Item{
        int imgId;
        String title;
        String sub;

        public Item(int imgId, String title, String sub){
            this.imgId = imgId;
            this.title = title;
            this.sub = sub;
        }
    }

    class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the top.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the top page
                view.setAlpha(1);
                view.setTranslationY(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationY(pageHeight * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the bottom.
                view.setAlpha(0);
            }
        }
    }
}
