package com.sfparks.model;


import android.app.Application;
import android.location.Location;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.paperdb.Paper;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
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

    public static final String LOCATION_1 = "location_1";
    public static final String PSAMANAGER = "psamanager";
    public static final String PARKNAME = "parkname";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String EMAIL = "email";
    public static final String NUMBER = "number";

    public ParksModule(){}

    @Provides
    @Singleton
    Observable<List<Park>> providesParksList (
            final NetworkModule.SFParksInterface sfParksInterface,
            final Application application,
            final ReactiveLocationProvider reactiveLocationProvider){
        return Observable
                .range(1,2)
                .switchMap(new Func1<Integer, Observable<? extends ArrayList<String>>>() {
                    @Override
                    public Observable<? extends ArrayList<String>> call(Integer integer) {
                        if (integer == 1) return Paperstore.getParkKeys();
                        else {
                            Paper.book().destroy();
                            Paper.init(application);
                            return sfParksInterface.getParks()
                                    .switchMap(new Func1<ArrayList<Object>, Observable<? extends ArrayList<String>>>() {
                                        @Override
                                        public Observable<? extends ArrayList<String>> call(ArrayList<Object> objects) {
                                            Paperstore.updatePaperstore(objects);
                                            return Paperstore.getParkKeys();
                                        }
                                    });
                        }
                    }
                })
                .switchMap(new Func1<ArrayList<String>, Observable<? extends List<Park>>>() {
                    @Override
                    public Observable<? extends List<Park>> call(final ArrayList<String> strings) {
                        return reactiveLocationProvider.getLastKnownLocation()
                                .switchMap(new Func1<Location, Observable<? extends List<Park>>>() {
                                    @Override
                                    public Observable<? extends List<Park>> call(Location location) {
                                        final JsonParser jsonParser = new JsonParser();
                                        return Observable.from(strings)
                                                .map(new Func1<String, String>() {
                                                    @Override
                                                    public String call(String s) {
                                                        return Paper.book().read(s);
                                                    }})
                                                .map(new Func1<String, Park>() {
                                            @Override
                                            public Park call(String s) {
                                                JsonObject object = jsonParser.parse(s).getAsJsonObject();
                                                JsonObject location = jsonParser.parse(
                                                        object.get(LOCATION_1)
                                                                .getAsString()).getAsJsonObject();
                                                float latitude = location.get(LATITUDE).getAsFloat();
                                                float longitude = location.get(LONGITUDE).getAsFloat();
                                                return new Park(
                                                        1, //placeholder
                                                        latitude,
                                                        longitude,
                                                        object.get(PARKNAME).getAsString(),
                                                        object.get(PSAMANAGER).getAsString(),
                                                        object.get(EMAIL).getAsString(),
                                                        object.get(NUMBER).getAsString());
                                            }
                                        }).toSortedList();
                                    }
                                });
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
