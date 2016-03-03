package com.sfparks.model;

import com.sfparks.activity.NearbyParksActivity;

import javax.inject.Singleton;

import dagger.Component;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Singleton
@Component(modules = {
        NetworkModule.class,
        ParksModule.class})

public interface ParksComponent {
    void inject(NearbyParksActivity activity);
}
