package com.sfparks;

/*
 * Created by Andrew Brin on 3/2/2016.
 */

import com.sfparks.model.MockParksModule;
import com.sfparks.modules.ParksModule;

public class TestNearbyParksApplication extends NearbyParksApplication {

    MockParksModule mockParksModule;

    @Override
    public ParksModule getParksModule() {
        if (mockParksModule == null){ return super.getParksModule(); }
        return mockParksModule;
    }

    public void setParksModule(MockParksModule mockParksModule) {
        this.mockParksModule = mockParksModule;
    }
}
