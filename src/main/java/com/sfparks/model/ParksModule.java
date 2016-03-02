package com.sfparks.model;

import android.content.SharedPreferences;

import com.sfparks.model.Park;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Module
public class ParksModule {

    public ParksModule(){}

    @Provides
    @Singleton
    Observable<Collection<Park>> providesParksList (Retrofit retrofit){
        retrofit.baseUrl();
        return Observable.just((Collection<Park>) Collections.singletonList(new Park()))
                .subscribeOn(Schedulers.newThread());
    }
}
