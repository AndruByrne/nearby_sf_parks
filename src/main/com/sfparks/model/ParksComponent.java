package com.sfparks.model;

import android.app.Application;

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
        ParksModule.class})

public interface ParksComponent {
    void inject(NearbyParksActivity activity);
}
