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
    private String mDate;
    private String mTime;
    private String mLoaction;

    private String mCategory;
    private byte[] mImage;

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

    public String getDate(){
        return mDate;
    }

    public void setDate(String date){
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public void setLoaction(String location) {
        mLoaction = location;
    }

    public String getLoaction(){
        return mLoaction;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

}
