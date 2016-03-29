package com.sfparks.modules;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.cglib.proxy.UndeclaredThrowableException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Timer;
import java.util.TimerTask;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

/*
 * Created by Andrew Brin on 3/23/2016.
 */
public class MockLocationModule extends LocationModule {

    private final Timer timer = new Timer();

    public MockLocationModule() {
    }

    @Override
    public GoogleApiClient providesAPIClient(Application application) {

        Answer<Object> answer = new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                ((GoogleApiClient.ConnectionCallbacks) invocation.getArguments()[0]).onConnected(new Bundle());
                return null;
            }
        };

        GoogleApiClient mock = Mockito.mock(GoogleApiClient.class);
        doAnswer(answer)
                .when(mock).registerConnectionCallbacks(any(GoogleApiClient.ConnectionCallbacks.class));

        return mock;

    }

    @Override
    public FusedLocationProviderApi providesFusedLocationAPI(){
        FusedLocationProviderApi mock = Mockito.mock(LocationServices.FusedLocationApi.getClass());
        return mock;
    }
}
