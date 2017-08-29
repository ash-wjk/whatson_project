package uk.co.ashawijekoon.whatson.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.android.gms.location.places.Place;

import java.util.Date;
import java.util.UUID;

import uk.co.ashawijekoon.whatson.models.Event;
import uk.co.ashawijekoon.whatson.models.Location;

import static uk.co.ashawijekoon.whatson.R.drawable.event;

/**
 * Created by Asha Wijekoon on 24/08/2017.
 */

public class EventCursorWrapper extends CursorWrapper {

    public EventCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Event getEvent(){
        String uuidString = getString(getColumnIndex(EventDbSchema.EventTable.Cols.UUID));
        String title = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TITLE));
        String description = getString(getColumnIndex(EventDbSchema.EventTable.Cols.DESCRIPTION));
        String date = getString(getColumnIndex(EventDbSchema.EventTable.Cols.DATE));
        String time = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TIME));
        String location_name = getString(getColumnIndex(EventDbSchema.EventTable.Cols.LOCATION_NAME));
        String category = getString(getColumnIndex(EventDbSchema.EventTable.Cols.CATEGORY));
//        byte[] image = getBlob(getColumnIndex(EventDbSchema.EventTable.Cols.IMAGE));

        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(date);
        event.setTime(time);
        event.setLoaction(location_name);
        event.setCategory(category);
//        event.setImage(image);

        return event;
    }

}
