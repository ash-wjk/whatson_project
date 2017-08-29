package uk.co.ashawijekoon.whatson.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.ashawijekoon.whatson.models.Event;

/**
 * Created by Asha Wijekoon on 23/08/2017.
 */

public class EventLab {
    private static EventLab sEventLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EventLab get(Context context){
        if(sEventLab == null){
            sEventLab = new EventLab(context);
        }
        return sEventLab;
    }

    private EventLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Event event){
        ContentValues values = new ContentValues();
        values.put(EventDbSchema.EventTable.Cols.UUID, event.getId().toString());
        values.put(EventDbSchema.EventTable.Cols.TITLE,event.getTitle());
        values.put(EventDbSchema.EventTable.Cols.DESCRIPTION,event.getDescription());
        values.put(EventDbSchema.EventTable.Cols.DATE,event.getDate().toString());
        values.put(EventDbSchema.EventTable.Cols.TIME,event.getTime().toString());
        values.put(EventDbSchema.EventTable.Cols.LOCATION_LAT,event.getLoaction().getLat());
        values.put(EventDbSchema.EventTable.Cols.LOCATION_LNG,event.getLoaction().getLng());
        values.put(EventDbSchema.EventTable.Cols.LOCATION_NAME,event.getLoaction().getName());
        values.put(EventDbSchema.EventTable.Cols.IMAGE, event.getImage());

        return values;
    }

    public SQLiteDatabase getDatabase(){
        return mDatabase;
    }


    public void addEvent(Event event){

        ContentValues values = getContentValues(event);
        mDatabase.insert(EventDbSchema.EventTable.NAME,null,values);

    }

    public void updateEvent(Event event){
        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);

        mDatabase.update(EventDbSchema.EventTable.NAME, values,
                EventDbSchema.EventTable.Cols.UUID + " = ? ",
                new String[] { uuidString } );

    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EventDbSchema.EventTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new EventCursorWrapper(cursor);
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(
                EventDbSchema.EventTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                groupBy, // groupBy
                having, // having
                orderBy // orderBy
        );

        return new EventCursorWrapper(cursor);
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();

        EventCursorWrapper cursor = queryEvents(null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            events.add(cursor.getEvent());
            cursor.moveToNext();
        }
        cursor.close();

        return events;

    }

    public List<Event> getEvents(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy){
        List<Event> events = new ArrayList<>();

        EventCursorWrapper cursor = queryEvents(
                whereClause,
                whereArgs,
                groupBy,
                having,
                orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            events.add(cursor.getEvent());
            cursor.moveToNext();
        }
        cursor.close();

        return events;

    }

    public Cursor getEventsCursor(){
        Cursor cursor = mDatabase.query(
                EventDbSchema.EventTable.NAME,
                null, // Columns - null selects all columns
                null,
                null,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return cursor;
    }

    public Event getEvent(String name) {
        EventCursorWrapper cursor = queryEvents(
                EventDbSchema.EventTable.NAME + " = ? ",
                new String[] { name }
        );

        try {
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return  cursor.getEvent();
        } finally {
            cursor.close();
        }
    }


}