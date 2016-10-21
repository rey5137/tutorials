package com.rey.tutorial;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Rey on 5/24/2015.
 */
public class MainActivity extends FragmentActivity{

    AtomicInteger mValue;
    AtomicInteger mCount;
    List<Integer> mEmitValues;
    List<Integer> mReceiveValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    void compare(List<Integer> l1, List<Integer> l2){
        if(l1.size() != l2.size()) {
            Log.d("test", "not same size");
        }
        else {
            for (int i = 0; i < l1.size(); i++)
                if (!l1.get(i).equals(l2.get(i))) {
                    Log.d("test", "not same at index=" + i + " emit = " + l1.get(i) + " receive = " + l2.get(i));
                    return;
                }
            Log.d("test", "same!");
        }
    }

    void test(){
        final int total = 1000;
        mValue = new AtomicInteger(0);
        mCount = new AtomicInteger(0);
        mEmitValues = new ArrayList<>();
        mReceiveValues = new ArrayList<>();

        Scheduler subscribeScheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        Scheduler mapScheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        Scheduler observeScheduler = Schedulers.from(Executors.newSingleThreadExecutor());

        for(int i = 0; i < total; i++){
            Observable.just(null)
                    .subscribeOn(subscribeScheduler)
                    .observeOn(mapScheduler)
                    .map(new Func1<Object, Integer>() {
                        @Override
                        public Integer call(Object obj) {
                            int newValue = mValue.incrementAndGet();
                            mEmitValues.add(newValue);
                            return newValue;
                        }
                    })
                    .observeOn(observeScheduler)
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer value) {
                            mReceiveValues.add(value);
                            if(mCount.incrementAndGet() == total) {
                                Log.d("test", "run complete");
                                compare(mEmitValues, mReceiveValues);
                            }
                        }
                    });
        }

    }
}
