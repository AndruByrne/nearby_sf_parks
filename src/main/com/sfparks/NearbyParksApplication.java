package com.sfparks;

import android.app.Application;

import com.sfparks.modules.AppModule;
import com.sfparks.modules.DaggerParksComponent;
import com.sfparks.modules.LocationModule;
import com.sfparks.modules.NetworkModule;
import com.sfparks.modules.ParksComponent;
import com.sfparks.modules.ParksModule;
import com.sfparks.modules.PopupModule;
import com.sfparks.modules.ThreadingModule;


public class NearbyParksApplication extends Application {
    public static String SF_CITY_API_BASE_URL = "https://data.sfgov.org/resource/";
    private ParksComponent parksComponent;
    protected static String PARKS_FLAG = "Parks App";

    @Override
    public void onCreate() {
        super.onCreate();
        parksComponent = DaggerParksComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(getNetworkModule(SF_CITY_API_BASE_URL))
                .locationModule(getLocationModule())
                .parksModule(new ParksModule())
                .popupModule(new PopupModule())
                .threadingModule(getThreadingModule())
                .build();
    }

    public LocationModule getLocationModule() {
        return new LocationModule();
    }

    public NetworkModule getNetworkModule(String sfCityApiBaseUrl) {
        return new NetworkModule(sfCityApiBaseUrl);
    }

    public ThreadingModule getThreadingModule(){ return new ThreadingModule(); }

    public ParksComponent getParksComponent(){
        return parksComponent;
    }
}
