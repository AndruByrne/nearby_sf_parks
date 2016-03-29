package com.sfparks.activity;

import android.app.Application;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Range;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sfparks.BuildConfig;

import com.sfparks.TestNearbyParksApplication;
import com.sfparks.model.Park;
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
import java.util.Collections;
import java.util.Timer;

import edu.emory.mathcs.backport.java.util.Arrays;
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
    }

    @Test
    public void testOnResumeCallsSFAPI() throws Exception {
        NearbyParksActivity activity = Robolectric.setupActivity(NearbyParksActivity.class);
        assertTrue(activity != null);

        System.out.println("Keys: " + Paper.book().getAllKeys());
        // There are 6 entries in the mocked response, there should be 6 here.
        assertTrue(Paper.book().getAllKeys() == Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5}));
    }

    @After
    public void tearDown() throws Exception {
    }
}
