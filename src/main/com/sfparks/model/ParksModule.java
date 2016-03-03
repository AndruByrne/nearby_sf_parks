package com.sfparks.model;


import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
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

    @Provides
    @Singleton
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit){
        return retrofit.create(NetworkModule.SFParksInterface.class);
    }

}
