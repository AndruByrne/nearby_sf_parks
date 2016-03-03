package com.sfparks.test_utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Created by Andrew Brin on 3/2/2016.
 */
public class StringConst {

    public static String SFAPI_OBJECT = "{parktype=Mini Park, parkname=10TH AVE/CLEMENT MINI PARK, email=steven.elder@sfgov.org, zipcode=94118, parkid=156, supdist=1, number=(415) 601-6501, parkservicearea=PSA 1, location_1={needs_recoding=false, longitude=-122.46809908, latitude=37.78184397, human_address={\"address\":\"351 9th Ave\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.66, psamanager=Elder, Steve}";

    public static String SFAPI1 = "{parktype=ParkType, parkname=ParkName, email=email, number=Number, parkservicearea=ParkServiceArea, psamanager=PSAManager}";

    public static String SFAPI2 = "{parktype=Mini Park, parkname=10TH AVE/CLEMENT MINI PARK, email=steven.elder@sfgov.org, zipcode=94118, parkid=156, supdist=1, number=(415) 601-6501, parkservicearea=PSA 1, location_1={needs_recoding=false, longitude=-122.46809908, latitude=37.78184397, human_address={\"address\":\"351 9th Ave\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.66, psamanager=Elder, Steve}";

    public static String SFAPI3 = "{parktype=Mini Park, parkname=15TH AVENUE STEPS, email=charles.sheehy@sfgov.org, zipcode=94122, parkid=185, supdist=7, number=(415) 218-2226, parkservicearea=PSA 4, location_1={needs_recoding=false, longitude=-122.47226783, latitude=37.75956493, human_address={\"address\":\"15th Ave b w Kirkham\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.26, psamanager=Sheehy, Chuck}";

    public static String SFAPI4 = "{parktype=Mini Park, parkname=24TH/YORK MINI PARK, email=adrian.field@sfgov.org, zipcode=94110, parkid=51, supdist=9, number=(415) 717-2872, parkservicearea=PSA 6, location_1={needs_recoding=false, longitude=-122.40857543, latitude=37.75306042, human_address={\"address\":\"24th\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.12, psamanager=Field, Adrian}";

    public static String SFAPI5 = "{parktype=Neighborhood Park or Playground, parkname=29TH/DIAMOND OPEN SPACE, email=teresa.o'brien@sfgov.org, zipcode=94131, parkid=194, supdist=8, number=(415) 819-2699, parkservicearea=PSA 5, location_1={needs_recoding=false, longitude=-122.43523589, latitude=37.74360211, human_address={\"address\":\"Diamond\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.82, psamanager=O'Brien, Teresa}";

    public static String SFAPI6 = "{parktype=Neighborhood Park or Playground, parkname=ADAM ROGERS PARK, email=robert.watkins@sfgov.org, zipcode=94124, parkid=46, supdist=10, number=(415) 819-6138, parkservicearea=PSA 3, location_1={needs_recoding=false, longitude=-122.38385466, latitude=37.73101645, human_address={\"address\":\"Ingalls\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=2.74, psamanager=Watkins, Robert}";

    public static String SFAPI_RESPONSE = "["+SFAPI1+", {parktype=Neighborhood Park or Playground, parkname=ALAMO SQUARE, email=tom.o'connor@sfgov.org, zipcode=94117, parkid=117, supdist=5, number=(415) 218-0259, parkservicearea=PSA 2, location_1={needs_recoding=false, longitude=-122.43467396, latitude=37.77634875, human_address={\"address\":\"Hayes\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=12.7, psamanager=O'Connor, Tom}, {parktype=Neighborhood Park or Playground, parkname=ALICE CHALMERS PLAYGROUND, email=zack.taylor@sfgov.org, zipcode=94112, parkid=25, supdist=11, number=(415) 601-7277, parkservicearea=PSA 3, location_1={needs_recoding=false, longitude=-122.44665065, latitude=37.7098271, human_address={\"address\":\"670 Brunswick\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=1.68, psamanager=Taylor, Zack}, {parktype=Neighborhood Park or Playground, parkname=ALICE MARBLE TENNIS COURTS, email=joseph.figone@sfgov.org, zipcode=94109, parkid=151, supdist=2, number=(415) 713-4997, parkservicearea=PSA 1, location_1={needs_recoding=false, longitude=-122.42034327, latitude=37.80142776, human_address={\"address\":\"Greenwich\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.84, psamanager=Figone, Joe}, {parktype=Mini Park, parkname=ALIOTO MINI PARK, email=adrian.field@sfgov.org, zipcode=94110, parkid=62, supdist=9, number=(415) 717-2872, parkservicearea=PSA 6, location_1={needs_recoding=false, longitude=-122.41821702, latitude=37.75890196, human_address={\"address\":\"20th\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.16, psamanager=Field, Adrian}, {parktype=Neighborhood Park or Playground, parkname=ALLYNE PARK, email=joseph.figone@sfgov.org, zipcode=94123, parkid=131, supdist=2, number=(415) 713-4997, parkservicearea=PSA 1, location_1={needs_recoding=false, longitude=-122.42759992, latitude=37.79746066, human_address={\"address\":\"2609 Gough St\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}}, acreage=0.75, psamanager=Figone, Joe}]";

    public static List SFAPI_LIST = Arrays.asList(
            SFAPI1,
            SFAPI2,
            SFAPI3,
            SFAPI4,
            SFAPI5,
            SFAPI6);

}
