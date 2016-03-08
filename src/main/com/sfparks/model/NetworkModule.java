package com.sfparks.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Module
public class NetworkModule {
    private String baseUrl;

    public NetworkModule(String baseUrl){ this.baseUrl = baseUrl; }

    public interface SFParksInterface{
        @GET("z76i-7s65.json")
        Observable<ArrayList<JsonObject>> getParks();
    }

    @Provides
    @Singleton
    Gson providesGson(){
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

}
