package com.sfparks.activity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sfparks.BuildConfig;

import com.sfparks.TestNearbyParksApplication;
import com.sfparks.test_utils.RoboTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import edu.emory.mathcs.backport.java.util.Arrays;
import io.paperdb.Paper;

import static org.junit.Assert.assertTrue;


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
        assertTrue(Paper.book().getAllKeys().size() == 6);
        assertTrue(Paper.book().getAllKeys() == Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5}));
    }

    @After
    public void tearDown() throws Exception {
    }
}
