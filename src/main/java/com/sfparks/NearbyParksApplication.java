package com.sfparks;

import android.app.Application;

import com.sfparks.model.DaggerParksComponent;
import com.sfparks.model.NetworkModule;
import com.sfparks.model.ParksComponent;
import com.sfparks.model.ParksModule;


public class NearbyParksApplication extends Application {
    static String SF_CITY_API_BASE_URL = "https://data.sfgov.org/resource/";
    private ParksComponent parksComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        parksComponent = DaggerParksComponent.builder()
                .parksModule(new ParksModule())
                .networkModule(new NetworkModule(SF_CITY_API_BASE_URL))
                .build();
    }

    public ParksComponent getParksComponent(){
        return parksComponent;
    }
}
