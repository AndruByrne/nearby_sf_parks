package com.sfparks.modules;

import com.google.gson.JsonArray;

import org.mockito.Mockito;

import retrofit2.Retrofit;
import rx.Observable;

/*
 * Created by Andrew Brin on 3/2/2016.
 */
public class MockNetworkModule extends NetworkModule {

    public MockNetworkModule(String baseUrl) { super(baseUrl); }

    public void setResponse(JsonArray response) {
        this.response = response;
    }

    JsonArray response;

    @Override
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit) {
        NetworkModule.SFParksInterface mock = Mockito.mock(NetworkModule.SFParksInterface.class);
        Mockito.when(mock.getParks()).thenReturn(Observable.just(response));
        return mock;
    }
}
