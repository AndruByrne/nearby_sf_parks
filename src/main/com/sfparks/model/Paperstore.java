package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/3/2016.
 */

import java.util.ArrayList;

import io.paperdb.Paper;
import rx.Observable;

public class Paperstore {

    static Observable<ArrayList<String>> getParks(){
        return Observable.just(new ArrayList<>(Paper.book().getAllKeys()));
    }

    static public ArrayList<String> updatePaperstore(ArrayList<Object> updates){
        ArrayList<String> strings = new ArrayList<String>();
        String holder;
        for(Object o: updates){
            holder = o.toString();
            strings.add(holder);
            Paper.book().write(holder, 1);
        }
        return strings;
    }
}
