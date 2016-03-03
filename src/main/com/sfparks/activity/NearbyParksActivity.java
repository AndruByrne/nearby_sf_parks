package com.sfparks.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sfparks.NearbyParksApplication;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.ArrayList;

import javax.inject.Inject;

import io.paperdb.Paper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class NearbyParksActivity extends Activity {

    @Inject Observable<ArrayList<Object>> parksObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NearbyParksApplication) getApplication()).getParksComponent().inject(this);
        Paper.init(this);

        setContentView(R.layout.parks_list_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parksObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Object>>() {
                    @Override
                    public void call(ArrayList<Object> parks) {
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