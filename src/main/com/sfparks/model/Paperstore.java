package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/3/2016.
 */

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.paperdb.Book;
import io.paperdb.Paper;
import rx.Observable;

public class Paperstore {

    static Observable<ArrayList<String>> getParkKeys(){
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
}
