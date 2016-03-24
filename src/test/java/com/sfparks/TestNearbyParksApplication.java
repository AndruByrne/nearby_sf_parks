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

    MockNetworkModule mockNetworkModule;
    MockLocationModule mockLocationModule;

    @Override
    public NetworkModule getNetworkModule(String apiBaseUrl) {
        if (mockNetworkModule == null){ return super.getNetworkModule(apiBaseUrl); }
        return mockNetworkModule;
    }

    public void setMockNetworkModule(MockNetworkModule mockNetworkModule) {
        this.mockNetworkModule = mockNetworkModule;
    }

    @Override
    public LocationModule getLocationModule(){
        if (mockLocationModule == null){ return super.getLocationModule(); }
        return mockLocationModule;
    }

    public void setMockLocationModule(MockLocationModule mockLocationModule){
        this.mockLocationModule = mockLocationModule;
    }

    @Override
    public ThreadingModule getThreadingModule(){
        return new TestThreadingModule();
    }
}
