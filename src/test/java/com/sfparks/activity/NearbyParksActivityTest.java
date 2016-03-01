package com.sfparks.activity;

import com.sfparks.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class NearbyParksActivityTest {

    @Test
    public void testSomething() throws Exception {
        assertTrue(Robolectric.setupActivity(com.sfparks.activity.NearbyParksActivity.class) != null);
    }
}
