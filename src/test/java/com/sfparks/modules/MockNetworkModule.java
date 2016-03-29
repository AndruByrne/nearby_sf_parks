package com.sfparks.modules;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sfparks.test_utils.StringConst;

import org.mockito.Mockito;

import retrofit2.Retrofit;
import rx.Observable;

/*
 * Created by Andrew Brin on 3/2/2016.
 */
public class MockNetworkModule extends NetworkModule {

    public MockNetworkModule(String baseUrl) { super(baseUrl); }

    JsonArray response = (new JsonParser().parse(new Gson().toJson(StringConst.SFAPI_LIST)).getAsJsonArray());

    @Override
    NetworkModule.SFParksInterface providesSFPI(Retrofit retrofit) {
        NetworkModule.SFParksInterface mock = Mockito.mock(NetworkModule.SFParksInterface.class);
        Mockito.when(mock.getParks()).thenReturn(Observable.just(response));
        return mock;
    }
}
