package com.sfparks.model;


import android.app.Application;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.paperdb.Paper;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.lang.Math.*;

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
    public static final String DUMMY_PARKNAME = "ParkName";

    public ParksModule() {
    }

    @Provides
    @Singleton
    Observable<List<Park>> providesParksList(
            final NetworkModule.SFParksInterface sfParksInterface,
            final Application application,
            final Observable<LatLng> reactiveLocationProvider) {
        Paper.init(application);
        return Observable
                .range(1, 2)
                .switchMap(new Func1<Integer, Observable<? extends ArrayList<String>>>() {
                    @Override
                    public Observable<? extends ArrayList<String>> call(Integer integer) {
                        if (integer == 1) return Paperstore.getParkKeys();
                        else {
                            Paper.book().destroy();
                            Paper.init(application);
                            return sfParksInterface.getParks()
                                    .switchMap(new Func1<ArrayList<JsonObject>, Observable<? extends ArrayList<String>>>() {
                                        @Override
                                        public Observable<? extends ArrayList<String>> call(ArrayList<JsonObject> objects) {
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
                        return reactiveLocationProvider
                                .switchMap(new Func1<LatLng, Observable<? extends List<Park>>>() {
                                    @Override
                                    public Observable<? extends List<Park>> call(final LatLng latLng) {
                                        final JsonParser jsonParser = new JsonParser();
                                        return Observable.from(strings)
                                                .map(new Func1<String, String>() {
                                                    @Override
                                                    public String call(String s) {
                                                        return Paper.book().read(s);
                                                    }
                                                })
                                                .filter(new Func1<String, Boolean>() {
                                                    @Override
                                                    public Boolean call(String s) {
                                                        return (s != null);
                                                    }
                                                })
                                                .map(new Func1<String, JsonObject>() {
                                                    @Override
                                                    public JsonObject call(String s) {
                                                        try {
                                                            return jsonParser.parse(s.trim()).getAsJsonObject();
                                                        } catch (JsonSyntaxException e) {
                                                            Log.d("sfparks parksModule", "poorly formed json: " + s + " Error: " + e);
                                                            return null;
                                                        }
                                                    }
                                                })
                                                .filter(new Func1<JsonObject, Boolean>() {
                                                    @Override
                                                    public Boolean call(JsonObject jsonObject) {
                                                        // its malforming up here in the filters
                                                        return !(jsonObject == null || jsonObject.get(LOCATION_1) == null);
                                                    }
                                                })
                                                .filter(new Func1<JsonObject, Boolean>() {
                                                    @Override
                                                    public Boolean call(JsonObject jsonObject) {
                                                        JsonElement jsonElement = jsonObject.get(LOCATION_1).getAsJsonObject().get(LATITUDE);
                                                        return !(jsonElement == null || jsonElement.getAsString().equals("999"));
                                                    }
                                                })
                                                .map(new Func1<JsonObject, Park>() {
                                                    @Override
                                                    public Park call(JsonObject object) {
                                                        return getParkFromRecord(object, jsonParser, latLng);
                                                    }
                                                })
                                                .toSortedList();
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }

    @NonNull
    private static Park getParkFromRecord(JsonObject object, JsonParser jsonParser, LatLng currentLatLng) {
        JsonObject location = object.get(LOCATION_1).getAsJsonObject();
        float latitude = location.get(LATITUDE).getAsFloat();
        float longitude = location.get(LONGITUDE).getAsFloat();
        return new Park(
                // using model described here to find difference:
                // http://stackoverflow.com/questions/389211/geospatial-coordinates-and-distance-in-kilometers
                getDistance(currentLatLng, latitude, longitude),
                latitude,
                longitude,
                object.get(PARKNAME).getAsString(),
                object.get(PSAMANAGER).getAsString(),
                object.get(EMAIL).getAsString(),
                object.get(NUMBER).getAsString());
    }

    private static int getDistance(LatLng currentLatLng, float latitude, float longitude) {
        if (currentLatLng == null) {
            Log.w("sfparks parkModule","current location is null!");
            return 0; // user may not have location turned on?
        }
        return ((Double) ((
                acos(
                        sin(latitude * PI / 180) * sin(currentLatLng.latitude * PI / 180)
                                + cos(latitude * PI / 180) * cos(currentLatLng.latitude * PI / 180)
                                * cos((longitude - currentLatLng.longitude) * PI / 180))
                        * 180 / PI) * 60 * 1.1515
                // get decameter to avoid collision through int rounding
                // TODO: add decameter -> kilometer conversion in UI
                * 100)).intValue();
    }

    @Provides
    @Singleton
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit) {
        return retrofit.create(NetworkModule.SFParksInterface.class);
    }

}
