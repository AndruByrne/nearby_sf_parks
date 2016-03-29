package com.sfparks.activity;

import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sfparks.BuildConfig;

import com.sfparks.TestNearbyParksApplication;
import com.sfparks.modules.MockLocationModule;
import com.sfparks.modules.MockNetworkModule;
import com.sfparks.test_utils.RoboTestRunner;
import com.sfparks.test_utils.StringConst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.proxy.UndeclaredThrowableException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.gms.ShadowGooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.Timer;

import io.paperdb.Paper;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(RoboTestRunner.class)
@Config(constants = BuildConfig.class,
        application = TestNearbyParksApplication.class,
        sdk = 21)
public class NearbyParksActivityTest {

    private GoogleApiClient gAPImock;

    @Before
    public void setUp() throws Exception {

//        MockitoAnnotations.initMocks(this);

        // Force Play services success
//        ShadowGooglePlayServicesUtil.setIsGooglePlayServicesAvailable(ConnectionResult.SUCCESS);
        System.out.println("set up tests");
    }

    @Test
    public void testOnResumeCallsSFAPI() throws Exception {
        System.out.println("setting activity");
        NearbyParksActivity activity = Robolectric.setupActivity(NearbyParksActivity.class);
        System.out.println("activity set");
        assertTrue(activity != null);

//        Robolectric.flushBackgroundThreadScheduler();
//        Robolectric.getBackgroundThreadScheduler().idleConstantly(true);
//        final CountDownLatch latch = new CountDownLatch(1);
//        try{
//            latch.await(1, TimeUnit.SECONDS);
//        }catch (InterruptedException e){
//            latch.notifyAll();
//        }
//        Robolectric.flushForegroundThreadScheduler();
//        Robolectric.getForegroundThreadScheduler().idleConstantly(true);


        //  RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertTrue(Paper.book().getAllKeys() == StringConst.SFAPI_LIST);
    }

    @After
    public void tearDown() throws Exception {
    }
}
