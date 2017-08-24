package uk.co.ashawijekoon.whatson.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import uk.co.ashawijekoon.whatson.models.Event;

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
        long date = getLong(getColumnIndex(EventDbSchema.EventTable.Cols.DATE));

        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(new Date(date));

        return event;

    }

}
