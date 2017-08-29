package uk.co.ashawijekoon.whatson.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.database.EventDbSchema;

/**
 * Created by Asha Wijekoon on 29/08/2017.
 */

public class EventCursorAdapter extends CursorAdapter {

    public EventCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView dateTextView = (TextView) view.findViewById(R.id.event_list_date);
        TextView timeTextView = (TextView) view.findViewById(R.id.event_list_time);
        TextView titleTextView = (TextView) view.findViewById(R.id.event_list_title);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.event_list_description);

        // Extract properties from cursor
        String date = cursor.getString(cursor.getColumnIndexOrThrow(EventDbSchema.EventTable.Cols.DATE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(EventDbSchema.EventTable.Cols.TIME));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(EventDbSchema.EventTable.Cols.TITLE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(EventDbSchema.EventTable.Cols.DESCRIPTION));

        // Populate fields with extracted properties
        dateTextView.setText(date);
        timeTextView.setText(time);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }
}
