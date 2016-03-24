package com.sfparks.modules;

/*
 * Created by Andrew Brin on 3/21/2016.
 */

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class ThreadingModule {
    public ThreadingModule(){}

    @Provides
    public Scheduler providesScheduler(){
        return Schedulers.newThread();
    }
}
