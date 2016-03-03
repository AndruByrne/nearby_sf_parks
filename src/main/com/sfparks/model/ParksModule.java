package com.sfparks.model;


import android.app.Application;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.paperdb.Paper;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Module
public class ParksModule {

    public ParksModule(){}

    @Provides
    @Singleton
    Observable<ArrayList<Park>> providesParksList (final NetworkModule.SFParksInterface sfParksInterface, final Application application){
        return Observable
                .range(1,2)
                .switchMap(new Func1<Integer, Observable<? extends ArrayList<String>>>() {
                    @Override
                    public Observable<? extends ArrayList<String>> call(Integer integer) {
                        if (integer == 1) return Paperstore.getParks();
                        else {
                            Paper.book().destroy();
                            Paper.init(application);
                            return sfParksInterface.getParks()
                                    .map(new Func1<ArrayList<Object>, ArrayList<String>>() {
                                        @Override
                                        public ArrayList<String> call(ArrayList<Object> objects) {
                                            return Paperstore.updatePaperstore(objects);
                                        }
                                    });
                        }
                    }
                })
                .switchMap(new Func1<ArrayList<String>, Observable<? extends ArrayList<Park>>>() {
                    @Override
                    public Observable<? extends ArrayList<Park>> call(ArrayList<String> strings) {
                        // here I will create an observable from the arraylist, and return a arraylist of
                        // ordered parks by calculating distance to points and using toSortedList
                        return null;
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }

    @Provides
    @Singleton
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit){
        return retrofit.create(NetworkModule.SFParksInterface.class);
    }

}
