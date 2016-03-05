package com.sfparks.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sfparks.NearbyParksApplication;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.paperdb.Paper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class NearbyParksActivity extends Activity {

    public int track;
    @Inject Observable<List<Park>> parksObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        ((NearbyParksApplication) getApplication()).getParksComponent().inject(this);
        track = 0;
        setContentView(R.layout.parks_list_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parksObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Park>>() {
                    @Override
                    public void call(List<Park> parks) {
                        track = 1;
                        System.out.println("sfparks onNext");
                        Log.d(
                                "sfparks_onNext",
                                parks.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(
                                "sfparks_onError",
                                "error in parks observable: ",
                                throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        //unsubscribe
                    }
                });
    }
}