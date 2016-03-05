package com.sfparks.model;

/*
 * Created by Andrew Brin on 3/1/2016.
 */

public class Park implements Comparable<Park>{
    public Park(int distance, float latitude, float longitude, String parkname, String managerName, String email, String phoneNumber){
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkname = parkname;
        this.managerName = managerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private int distance;
    private float latitude;
    private float longitude;
    private String parkname;
    private String managerName;
    private String email;
    private String phoneNumber;

    @Override
    public int compareTo(Park another) {
        return this.distance - another.distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Park park = (Park) o;

        if (Float.compare(park.latitude, latitude) != 0) return false;
        if (Float.compare(park.longitude, longitude) != 0) return false;
        if (parkname != null ? !parkname.equals(park.parkname) : park.parkname != null)
            return false;
        if (managerName != null ? !managerName.equals(park.managerName) : park.managerName != null)
            return false;
        if (email != null ? !email.equals(park.email) : park.email != null) return false;
        return !(phoneNumber != null ? !phoneNumber.equals(park.phoneNumber) : park.phoneNumber != null);

    }

    @Override
    public int hashCode() {
        int result = (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        result = 31 * result + (parkname != null ? parkname.hashCode() : 0);
        result = 31 * result + (managerName != null ? managerName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
