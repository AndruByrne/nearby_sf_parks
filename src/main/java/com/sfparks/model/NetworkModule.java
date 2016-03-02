package com.sfparks.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

@Module
public class NetworkModule {
    private String baseUrl;

    public NetworkModule(String baseUrl){
        this.baseUrl = baseUrl;
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
                .baseUrl(baseUrl)
                .build();
    }
}
