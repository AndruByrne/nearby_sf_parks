package com.sfparks.modules;

/*
 * Created by Andrew Brin on 3/4/2016.
 */


import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

@Module
public class LocationModule {

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;

    enum LocationServiceHealth {
        GOOD,
        INTERRUPTED,
        DISABLED
    }

    public LocationModule() {
    }

    @Provides
    @Singleton
    GoogleApiClient providesAPIClient(Application application) {
        return new GoogleApiClient.Builder(application)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides
    @Singleton
    FusedLocationProviderApi providesFusedLocationAPI(){
        return LocationServices.FusedLocationApi;
    }

    @Provides
    @Singleton
    Observable<LatLng> providesRxLocation(
            final GoogleApiClient googleApiClient,
            final FusedLocationProviderApi fusedLocationApi) {
        return Observable
                .create(new Observable.OnSubscribe<GoogleApiClient>() {
                            @Override
                            public void call(final Subscriber<? super GoogleApiClient> subscriber) {
                                googleApiClient.connect();
                                System.out.println("requested connection to api client");
                                // created as field to avoid gc
                                connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
                                    @Override
                                    public void onConnected(Bundle bundle) { // this is called if already connected
                                        if (subscriber.isUnsubscribed()) {
                                            googleApiClient.unregisterConnectionCallbacks(this);
                                        } else {
                                            Log.d("sfparks locationModule", "got location");
                                            subscriber.onNext(googleApiClient);
                                        }
                                    }

                                    @Override
                                    public void onConnectionSuspended(int i) {
                                        // do nothing, connection will retry
                                    }
                                };
                                googleApiClient.registerConnectionCallbacks(connectionCallbacks);
                            }
//                            }
                        }
                )
                .first()
                .switchMap(new Func1<GoogleApiClient, Observable<? extends LatLng>>() {
                    @Override
                    public Observable<? extends LatLng> call(
                            final GoogleApiClient googleApiClient) {
                        return Observable.create(new Observable.OnSubscribe<LatLng>() {
                            @Override
                            public void call(Subscriber<? super LatLng> subscriber) {
//                                if (ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                    System.out.println("Didn't have permissions");
//                                    System.out.println("permission is: "+ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION));
//                                    subscriber.onError(new Throwable("Coarse location permissions not granted"));
//                                } else {
                                    System.out.println("getting last location");

                                Location lastLocation = fusedLocationApi.getLastLocation(googleApiClient);
                                    if (lastLocation == null) {
                                        System.out.println("null loc");
                                        subscriber.onNext(new LatLng(999, 999));
                                    } else {
                                        System.out.println("non null loc");
                                        subscriber.onNext(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
                                    }
                                }
//                            }
                        });
                    }
                });
    }

    // Unused; would use a second client for UI connection feedback (prompt to enable location sharing)
    @Provides
    @Singleton
    Observable<LocationServiceHealth> providesLocationServiceHealth(
            final GoogleApiClient googleApiClient) {
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
                        if (subscriber.isUnsubscribed()) {
                            googleApiClient.unregisterConnectionCallbacks(this);
                        } else {
                            subscriber.onNext(LocationServiceHealth.GOOD);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        if (subscriber.isUnsubscribed()) {
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
