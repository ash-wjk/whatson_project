package uk.co.ashawijekoon.whatson.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.adapter.EventAdapter;
import uk.co.ashawijekoon.whatson.models.Event;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.events_list_view);

        final ArrayList<Event> eventList = getEvents();
        EventAdapter adapter = new EventAdapter(this, eventList);
        mListView.setAdapter(adapter);

    }

    public ArrayList<Event> getEvents(){
        ArrayList<Event> eventList = new ArrayList<Event>();

        for(int i = 0; i < 40; i++){
            String eventName = new String("Event " + Integer.toString(i));
            Event event = new Event(eventName,"Description" + Integer.toString(i),new Date());
            eventList.add(event);
        }
        return eventList;
    }
}
