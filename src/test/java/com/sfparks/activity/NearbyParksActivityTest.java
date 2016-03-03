package com.sfparks.activity;

import android.app.Application;

import com.sfparks.BuildConfig;
import com.sfparks.NearbyParksApplication;
import com.sfparks.TestNearbyParksApplication;
import com.sfparks.model.MockParksModule;
import com.sfparks.model.NetworkModule;
import com.sfparks.test_utils.StringConst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        application = TestNearbyParksApplication.class,
        sdk = 21)
public class NearbyParksActivityTest {

    @Captor
    private ArgumentCaptor<ArrayList<Object>> SFAPIcaptor;

    @Before
    public void setUp() throws Exception{
        Application application = RuntimeEnvironment.application;
        MockParksModule mockParksModule = new MockParksModule();
        mockParksModule.setResponse(new ArrayList<>(StringConst.SFAPI_LIST)); // unchecked assignment OK
        ((TestNearbyParksApplication) application).setParksModule(mockParksModule);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnResumeCallsSFAPI() throws Exception {
        NearbyParksActivity activity = Robolectric.setupActivity(NearbyParksActivity.class);
        assertTrue(activity != null);

        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.getBackgroundThreadScheduler().idleConstantly(true);
        final CountDownLatch latch = new CountDownLatch(1);
        try{
            latch.await(1, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            latch.notifyAll();
        }
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.getForegroundThreadScheduler().idleConstantly(true);


        //  RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertTrue(activity.track == 1);

        assertTrue(Paper.book().getAllKeys() == StringConst.SFAPI_LIST);
    }

    @After
    public void tearDown() throws Exception {
    }
}
