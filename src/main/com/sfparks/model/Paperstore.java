package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/3/2016.
 */

import java.util.ArrayList;

import io.paperdb.Paper;
import rx.Observable;

public class Paperstore {

    static Observable<ArrayList<String>> getParkKeys(){
        return Observable.just(new ArrayList<>(Paper.book().getAllKeys()));
    }

    static public ArrayList<String> updatePaperstore(ArrayList<Object> updates){
        ArrayList<String> strings = new ArrayList<>();
        String holderString;
        int updatesLength = updates.size();
        int i;
        for(i=0; i < updatesLength; i++){
            holderString = updates.get(i).toString();
            strings.add(holderString);
            Paper.book().write(Integer.toString(i), holderString);
        }
        return strings;
    }
}
