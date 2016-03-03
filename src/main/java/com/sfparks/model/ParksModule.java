package com.sfparks.model;

import android.content.SharedPreferences;

import com.sfparks.model.Park;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.schedulers.Schedulers;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Module
public class ParksModule {

    public ParksModule(){}

    @Provides
    @Singleton
    Observable<ArrayList<Object>> providesParksList (NetworkModule.SFParksInterface sfParksInterface){
        return sfParksInterface.getParks()
                .subscribeOn(Schedulers.newThread());
    }
}
