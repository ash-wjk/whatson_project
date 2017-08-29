package uk.co.ashawijekoon.whatson.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.models.Event;

public class ViewEventActivity extends AppCompatActivity {

    TextView event_title;
    TextView event_datetime;
    TextView event_category;
    TextView event_description;
    TextView event_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Event event = (Event) getIntent().getParcelableExtra("eventDataKey");

        event_title = (TextView) findViewById(R.id.event_title);
        event_datetime = (TextView) findViewById(R.id.event_datetime);
        event_category = (TextView) findViewById(R.id.event_category);
        event_description = (TextView) findViewById(R.id.event_description);
        event_location = (TextView) findViewById(R.id.event_location);

        event_title.setText(event.getTitle());
        event_category.setText(event.getLoaction());
        event_datetime.setText(event.getCategory());
        event_description.setText(event.getDescription());
        event_location.setText(event.getDate() + " at " + event.getTime());


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
