package com.sfparks.model;

import org.mockito.Mockito;

import java.util.ArrayList;

import retrofit2.Retrofit;
import rx.Observable;

/*
 * Created by Andrew Brin on 3/2/2016.
 */
public class MockParksModule extends ParksModule{

    public void setResponse(ArrayList<Object> response) {
        this.response = response;
    }

    ArrayList<Object> response;

    @Override
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit) {
        NetworkModule.SFParksInterface mock = Mockito.mock(NetworkModule.SFParksInterface.class);
        Mockito.when(mock.getParks()).thenReturn(Observable.just(response));
        return mock;
    }
}