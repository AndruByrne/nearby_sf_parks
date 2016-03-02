package com.sfparks.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sfparks.NearbyParksApplication;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.Collection;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class NearbyParksActivity extends Activity {

    @Inject Observable<Collection<Park>> parksObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NearbyParksApplication) getApplication()).getParksComponent().inject(this);
        setContentView(R.layout.parks_list_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parksObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Collection<Park>>() {
                    @Override
                    public void call(Collection<Park> parks) {

                    }
        });
    }
}
