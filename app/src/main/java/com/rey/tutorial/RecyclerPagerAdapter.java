package com.rey.tutorial;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Rey on 5/25/2015.
 */
public abstract class RecyclerPagerAdapter extends PagerAdapter{

    ArrayList<Integer> mInstantiatedPositions = new ArrayList<>();

    private volatile Stack<Reference<View>>[] mRecycler;

    public static final int TAG = R.id.action_bar;

    private Stack<Reference<View>> getRecycler(int type){
        if(mRecycler == null){
            synchronized (RecyclerPagerAdapter.class){
                if(mRecycler == null)
                    mRecycler = new Stack[getViewTypeCount()];
            }
        }

        if(mRecycler[type] == null){
            synchronized (RecyclerPagerAdapter.class){
                if(mRecycler[type] == null)
                    mRecycler[type] = new Stack<>();
            }
        }

        return mRecycler[type];
    }

    private void putViewToRecycler(View v, int type){
        synchronized (RecyclerPagerAdapter.class){
            getRecycler(type).push(new WeakReference<>(v));
        }
    }

    private View getViewFromRecycler(int type){
        View v = null;
        synchronized (RecyclerPagerAdapter.class) {
            Stack<Reference<View>> stack = getRecycler(type);
            while (v == null && !stack.isEmpty())
                v = stack.pop().get();
        }
        return v;
    }

    @Override
    public final void startUpdate(ViewGroup container) {
        mInstantiatedPositions.clear();
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        mInstantiatedPositions.add(position);
        return position;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        //remove view attached to this object and put it to recycler.
        for(int i = container.getChildCount() - 1; i >= 0; i --){
            View v = container.getChildAt(i);
            if(isViewFromObject(v, object)){
                container.removeView(v);
                putViewToRecycler(v, getViewType(position));
                break;
            }
        }
    }

    @Override
    public final void finishUpdate(ViewGroup container) {
        // Render views and attach them to the container. Page views are reused
        // whenever possible.
        for (Integer pos : mInstantiatedPositions) {
            View convertView = getViewFromRecycler(getViewType(pos));

            if (convertView != null) {
                // Re-add existing view before rendering so that we can make change inside getView()
                container.addView(convertView, null);
                convertView = getView(pos, convertView, container);
            } else {
                convertView = getView(pos, convertView, container);
                container.addView(convertView);
            }

            convertView.requestLayout();

            // Set another tag id to not break ViewHolder pattern
            convertView.setTag(TAG, pos);
        }

        mInstantiatedPositions.clear();
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view.getTag(TAG) != null && ((Integer)view.getTag(TAG)).intValue() == ((Integer)object).intValue();
    }

    public int getViewTypeCount(){
        return 1;
    }

    public int getViewType(int position){
        return 0;
    }

    protected abstract Object getItem(int position);

    protected abstract View getView(int position, View convertView, ViewGroup parent);

}
