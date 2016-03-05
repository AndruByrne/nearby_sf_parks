package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/4/2016.
 */


import android.app.Application;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscriber;

@Module
public class LocationModule {

    enum LocationServiceHealth{
        GOOD,
        INTERRUPTED,
        DISABLED}

    public LocationModule(){}

    @Provides
    @Singleton
    GoogleApiClient providesAPIClient(Application application){
        return new GoogleApiClient.Builder(application)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides
    @Singleton
    ReactiveLocationProvider providesRxLocationProvider(Application application){
        return new ReactiveLocationProvider(application);
    }

    // Unused but also not included in the ReactiveLocationProvider; would use a second client for UI
    @Provides
    @Singleton
    Observable<LocationServiceHealth> providesLocationServiceHealth(
            final GoogleApiClient googleApiClient){
        return Observable.create(new Observable.OnSubscribe<LocationServiceHealth>() {
            @Override
            public void call(final Subscriber<? super LocationServiceHealth> subscriber) {
                final GoogleApiClient.OnConnectionFailedListener failedListener = new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        if (subscriber.isUnsubscribed()) {
                            googleApiClient.unregisterConnectionFailedListener(this);
                        } else {
                            subscriber.onNext(LocationServiceHealth.DISABLED);
                        }
                    }
                };
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        if (subscriber.isUnsubscribed()){
                            googleApiClient.unregisterConnectionCallbacks(this);
                        } else{
                            subscriber.onNext(LocationServiceHealth.GOOD);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        if (subscriber.isUnsubscribed()){
                            googleApiClient.unregisterConnectionCallbacks(this);
                        } else {
                            subscriber.onNext(LocationServiceHealth.INTERRUPTED);
                        }
                    }
                };
                googleApiClient.registerConnectionFailedListener(failedListener);
                googleApiClient.registerConnectionCallbacks(connectionCallbacks);
            }
        });
    }
}
