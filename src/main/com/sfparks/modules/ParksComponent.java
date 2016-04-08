package com.sfparks.modules;

import android.widget.PopupWindow;

import com.sfparks.activity.NearbyParksActivity;

import javax.inject.Singleton;

import dagger.Component;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        LocationModule.class,
        NetworkModule.class,
        ParksModule.class,
        PopupModule.class,
        ThreadingModule.class})

public interface ParksComponent {
    void inject(NearbyParksActivity activity);

    PopupWindow popupWindow();
}
