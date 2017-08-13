package uk.co.ashawijekoon.whatson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.models.Event;

/**
 * Created by nuwan on 13/08/2017.
 */

public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Event> mDataSource;

    public EventAdapter(Context context, ArrayList<Event> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_event, viewGroup, false);

        TextView timeTextView = (TextView) rowView.findViewById(R.id.event_list_time);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.event_list_title);
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.event_list_description);

        Event event = (Event) getItem(i);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        timeTextView.setText(timeFormat.format(event.date));
        titleTextView.setText(event.title);
        descriptionTextView.setText(event.description);

        return rowView;
    }
}
