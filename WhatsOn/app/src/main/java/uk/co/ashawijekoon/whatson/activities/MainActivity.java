package uk.co.ashawijekoon.whatson.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.adapter.EventAdapter;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mAddEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddEventButton = (FloatingActionButton) findViewById(R.id.addEventActionButton);
        mListView = (ListView) findViewById(R.id.mListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillEventList();
    }

    private void fillEventList(){
        final List<Event> eventList = getAllEvents();
        EventAdapter adapter = new EventAdapter(this, eventList);
        mListView.setAdapter(adapter);

        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openAddEvent();
            }
        });
    }

    private void openAddEvent(){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private List<Event> getAllEvents(){
        return EventLab.get(MainActivity.this).getEvents();
    }

}
