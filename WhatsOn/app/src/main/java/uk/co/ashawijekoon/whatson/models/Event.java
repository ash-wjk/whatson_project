package uk.co.ashawijekoon.whatson.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nuwan on 06/08/2017.
 */

public class Event {
    public String title;
    public String description;
    public Date date;

    public Event(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

}
