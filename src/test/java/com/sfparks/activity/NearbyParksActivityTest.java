package com.sfparks.activity;

import com.sfparks.BuildConfig;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class NearbyParksActivityTest {

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception{
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @Test
    public void testOnResumeCallsSFAPI() throws Exception {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(""));
        assertTrue(Robolectric.setupActivity(com.sfparks.activity.NearbyParksActivity.class) != null);
        assertTrue(mockWebServer.getRequestCount() == 1);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
