package uk.co.ashawijekoon.whatson.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.adapter.EventAdapter;
import uk.co.ashawijekoon.whatson.models.Event;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mAddEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.events_list_view);
        mAddEventButton = (FloatingActionButton) findViewById(R.id.addEventActionButton);

        final ArrayList<Event> eventList = getEvents();
        EventAdapter adapter = new EventAdapter(this, eventList);
        mListView.setAdapter(adapter);

        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                OpenAddEvent();
            }
        });

    }

    private void OpenAddEvent(){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
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
