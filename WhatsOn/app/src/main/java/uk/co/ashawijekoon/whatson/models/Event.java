package uk.co.ashawijekoon.whatson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Asha Wijekoon on 23/08/2017.
 */

public class Event implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mDate);
        dest.writeString(this.mTime);
        dest.writeString(this.mLoaction);
        dest.writeString(this.mCategory);
        dest.writeByteArray(this.mImage);
    }

    protected Event(Parcel in) {
        this.mId = (UUID) in.readSerializable();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mDate = in.readString();
        this.mTime = in.readString();
        this.mLoaction = in.readString();
        this.mCategory = in.readString();
        this.mImage = in.createByteArray();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
