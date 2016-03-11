package com.sfparks.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.Log;

import com.sfparks.BR;
import com.sfparks.NearbyParksApplication;
import com.sfparks.R;
import com.sfparks.databinding.ListItemLayoutBinding;
import com.sfparks.databinding.ParksListActivityBinding;
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

    private final ObservableArrayList<Park> observableArrayList = new ObservableArrayList<>();
    @Inject Observable<List<Park>> parksObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParksListActivityBinding parksListActivityBinding = DataBindingUtil.setContentView(this, R.layout.parks_list_activity);
        parksListActivityBinding.setVariable(BR.park_list, observableArrayList);
        ((NearbyParksApplication) getApplication()).getParksComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("sfparks_onResume", "test_logging");
        parksObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Park>>() {
                    @Override
                    public void call(List<Park> parks) {
                        if(parks.size() != 0){
                            Log.d(
                                    "sfparks onNext: ",
                                    Integer.toString(parks.size()));
                            observableArrayList.addAll(parks);
                        } else Log.i("com.sfparks", "empty parks list not displayed");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(
                                "sfparks_onError",
                                "error in parks observable: " + throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        //unsubscribe
                    }
                });
    }
}