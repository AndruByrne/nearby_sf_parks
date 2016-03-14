package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/3/2016.
 */

import android.app.Application;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.paperdb.Book;
import io.paperdb.Paper;
import rx.Observable;

public class ParksStore {

    static public Observable<ArrayList<String>> getParkKeys(){
        return Observable.just(new ArrayList<>(Paper.book().getAllKeys()));
    }

    static public void updatePaperstore(ArrayList<JsonObject> updates){
        String holderString;
        int updatesLength = updates.size();
        int i;
        for(i=0; i < updatesLength; i++){
            holderString = updates.get(i).getAsJsonObject().toString();
            Paper.book().write(Integer.toString(i), holderString);
        }
    }

    public static void nuke(Application application) {
        Paper.book().destroy();
    }

    public static String getPark(String s) {
        return Paper.book().read(s);
    }

    public static void initialize(Application application) {
        Paper.init(application);
    }
}
