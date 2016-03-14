package com.sfparks;

import android.app.Application;

import com.sfparks.modules.AppModule;
import com.sfparks.modules.DaggerParksComponent;
import com.sfparks.modules.LocationModule;
import com.sfparks.modules.NetworkModule;
import com.sfparks.modules.ParksComponent;
import com.sfparks.modules.ParksModule;


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
