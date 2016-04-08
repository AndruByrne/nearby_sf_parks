package com.sfparks.modules;

import android.app.Application;
import android.util.Log;
import android.widget.PopupWindow;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/*
 * Created by Andrew Brin on 4/7/2016.
 */
@Module
public class PopupModule {
    public PopupModule(){
        System.out.println("made a popupmodule");
    }

    @Provides
    @Singleton
    PopupWindow providesPopupWindow(Application application){
        System.out.println("making new popupwindow");
        return new PopupWindow(application);
    }

}
