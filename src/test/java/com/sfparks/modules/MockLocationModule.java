package com.sfparks.modules;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

/*
 * Created by Andrew Brin on 3/23/2016.
 */
public class MockLocationModule extends LocationModule {

    private final GoogleApiClient mock;

    public MockLocationModule(GoogleApiClient mock){ this.mock = mock; }

    @Override
    public GoogleApiClient providesAPIClient(Application application){
        return mock;

    }
}
