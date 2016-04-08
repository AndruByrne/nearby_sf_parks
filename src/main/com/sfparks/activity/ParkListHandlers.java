package com.sfparks.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.sfparks.BR;
import com.sfparks.NearbyParksApplication;
import com.sfparks.R;
import com.sfparks.model.Park;

/*
 * Created by Andrew Brin on 4/6/2016.
 */
public class ParkListHandlers {

    private Park park;

    public ParkListHandlers(Park park) {
        this.park = park;
    }

    public void onParkItemClick(View view) {
        Context context = view.getContext();
        View popupView = LayoutInflater.from(context).inflate(R.layout.park_popup, null);
        ViewDataBinding popupBinding = DataBindingUtil.bind(popupView);
        popupBinding.setVariable(BR.park, park);
        PopupWindow popupWindow = ((NearbyParksApplication)((Activity) view.getContext()).getApplication())
                .getParksComponent().popupWindow();
        if(popupWindow.isShowing()) popupWindow.dismiss();
        popupWindow.setContentView(popupView);
        popupWindow.showAtLocation((View) view.getParent(), Gravity.NO_GRAVITY, 100, 500);
    }

    public void onPopupClicked(View view) {
        System.out.println("dismissing popup window");
        ((NearbyParksApplication) view.getContext()).getParksComponent().popupWindow().dismiss();
    }
}
