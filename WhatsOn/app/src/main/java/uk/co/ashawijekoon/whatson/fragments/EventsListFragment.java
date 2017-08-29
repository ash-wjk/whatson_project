package uk.co.ashawijekoon.whatson.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import uk.co.ashawijekoon.whatson.activities.ViewEventActivity;
import uk.co.ashawijekoon.whatson.adapter.EventBaseAdapter;
import uk.co.ashawijekoon.whatson.adapter.EventCursorAdapter;
import uk.co.ashawijekoon.whatson.database.EventCursorWrapper;
import uk.co.ashawijekoon.whatson.database.EventDbSchema;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;

/**
 * Created by Asha Wijekoon on 28/08/2017.
 */

public class EventsListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private EventCursorAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No event, please add using + button.");
        fillEventList();
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Cursor c =(Cursor) getListView().getItemAtPosition(position);
        EventCursorWrapper cursor= EventLab.get(getActivity()).queryEvents(EventDbSchema.EventTable.Cols.UUID + "=?",
                new String[] {c.getString(c.getColumnIndex(EventDbSchema.EventTable.Cols.UUID))});

        cursor.moveToFirst();
        Event e = cursor.getEvent();

        Intent intent = new Intent(getActivity() , ViewEventActivity.class);
        intent.putExtra("eventDataKey",e);
        startActivity(intent);
    }

    private void fillEventList(String whereClause, String[] whereArgs){
        Cursor cursor = EventLab.get(getContext()).queryEvents(whereClause,whereArgs);
        mAdapter = new EventCursorAdapter(getActivity(),cursor);
        setListAdapter(mAdapter);
    }

    private void fillEventList(){
        Cursor cursor = EventLab.get(getContext()).getEventsCursor();
        mAdapter = new EventCursorAdapter(getActivity(),cursor);
        setListAdapter(mAdapter);
    }

    public void updateList(String whereClause, String[] whereArgs){
        if((whereClause == null) || (whereArgs == null)){
            fillEventList();
        }else{
            fillEventList(whereClause,whereArgs);
        }
    }

}
