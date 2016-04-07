package com.sfparks.activity;

import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sfparks.BR;
import com.sfparks.R;
import com.sfparks.databinding.ParkPopupBinding;
import com.sfparks.model.Park;

import javax.inject.Inject;

/*
 * Created by Andrew Brin on 4/6/2016.
 */
public class ParkListHandlers {

    private Park park;

    public ParkListHandlers(Park park){
        this.park = park;
    }

    public void onParkItemClick(View view){
        Context context = view.getContext();
        PopupWindow popupWindow = new PopupWindow(context);
        View popupView = LayoutInflater.from(context).inflate(R.layout.park_popup, null);
        ViewDataBinding popupBinding = DataBindingUtil.bind(popupView);
        popupBinding.setVariable(BR.park, park);
        popupWindow.setContentView(popupView);
        popupWindow.showAsDropDown(view, 0, 0);
//        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(popupWindow, R.layout.park_popup);
//        view.getParent().
    }

}
