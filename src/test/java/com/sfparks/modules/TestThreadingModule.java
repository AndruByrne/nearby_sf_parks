package com.sfparks.modules;

import com.sfparks.modules.ThreadingModule;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 * Created by Andrew Brin on 3/21/2016.
 */
public class TestThreadingModule extends ThreadingModule {
    @Override
    public Scheduler providesScheduler(){
        System.out.println("providing test threading module");
        return AndroidSchedulers.mainThread();
    }
}
