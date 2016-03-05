package com.sfparks;

import android.app.Application;

import com.sfparks.model.AppModule;
import com.sfparks.model.DaggerParksComponent;
import com.sfparks.model.LocationModule;
import com.sfparks.model.NetworkModule;
import com.sfparks.model.ParksComponent;
import com.sfparks.model.ParksModule;

import io.paperdb.Paper;


public class NearbyParksApplication extends Application {
    public static String SF_CITY_API_BASE_URL = "https://data.sfgov.org/resource/";
    private ParksComponent parksComponent;
    protected static String PARKS_FLAG = "Parks App";

    @Override
    public void onCreate() {
        super.onCreate();
        parksComponent = DaggerParksComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(SF_CITY_API_BASE_URL))
                .parksModule(getParksModule())
                .locationModule(new LocationModule())
                .build();
    }

    public ParksModule getParksModule(){ return new ParksModule(); }

    public ParksComponent getParksComponent(){
        return parksComponent;
    }
}
