package uk.co.ashawijekoon.whatson.models;

import com.google.android.gms.location.places.Place;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Asha Wijekoon on 23/08/2017.
 */

public class Event {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Location mLoaction;

    public Event() {
        this(UUID.randomUUID());
    }

    public Event(UUID id) {
        mId = id;
    }

    public UUID getId(){
        return mId;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getDescription(){
        return mDescription;
    }

    public void setDescription(String description){
        mDescription = description;
    }

    public Date getDate(){
        return mDate;
    }

    public void setDate(Date date){
        mDate = date;
    }

    public void setLoaction(Location location) {
        mLoaction = location;
    }

    public Location getLoaction(){
        return mLoaction;
    }

}
