package com.sfparks.modules;

import com.sfparks.activity.NearbyParksActivity;

import javax.inject.Singleton;

import dagger.Component;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        ParksModule.class,
        LocationModule.class})

public interface ParksComponent {
    void inject(NearbyParksActivity activity);
}
