package com.sfparks;

/*
 * Created by Andrew Brin on 3/2/2016.
 */

import android.support.annotation.NonNull;

import com.sfparks.modules.LocationModule;
import com.sfparks.modules.MockLocationModule;
import com.sfparks.modules.MockNetworkModule;

import com.sfparks.modules.NetworkModule;
import com.sfparks.modules.ParksModule;
import com.sfparks.modules.TestThreadingModule;
import com.sfparks.modules.ThreadingModule;

public class TestNearbyParksApplication extends NearbyParksApplication {

    @Override
    public NetworkModule getNetworkModule(String apiBaseUrl) {
        return new MockNetworkModule(apiBaseUrl);
    }

    @Override
    public LocationModule getLocationModule(){
        return new MockLocationModule();
    }

    @Override
    public ThreadingModule getThreadingModule(){
        return new TestThreadingModule();
    }
}
