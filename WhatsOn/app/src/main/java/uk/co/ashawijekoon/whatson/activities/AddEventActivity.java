package uk.co.ashawijekoon.whatson.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;
import uk.co.ashawijekoon.whatson.models.Location;

public class AddEventActivity extends AppCompatActivity {

    Button event_add;
    Button event_image_upload;
    ImageButton event_date;
    ImageButton event_time;
    Spinner event_category;
    EditText event_title;
    EditText event_location;
    EditText event_description;
    TextView event_date_label;
    TextView event_time_label;

    Location loaction;

    private static String TAG = "AddEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        event_add = (Button) findViewById(R.id.event_add);
        event_image_upload = (Button) findViewById(R.id.event_image_upload);
        event_date = (ImageButton) findViewById(R.id.event_date);
        event_time = (ImageButton) findViewById(R.id.event_time);
        event_category = (Spinner) findViewById(R.id.event_category);
        event_title =  (EditText) findViewById(R.id.event_title);
        event_description = (EditText) findViewById(R.id.event_description);
        event_date_label = (TextView) findViewById(R.id.event_date_label);
        event_time_label = (TextView) findViewById(R.id.event_time_label);

        event_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
                finish();
            }
        });

        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddEventActivity.this, new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
        });

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(AddEventActivity.this, new mTimeSetListener(),1,1,true);
                dialog.show();
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                loaction = new Location(0,0,"DDDD");
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private void addEvent(){
        Event e = new Event();
        e.setTitle(event_title.getText().toString());
        e.setDescription(event_description.getText().toString());
        e.setLoaction(loaction);
        SimpleDateFormat form = new SimpleDateFormat("dd/mm/yyyy");
        Date d = null;
        try {
            d = form.parse(event_date_label.getText().toString());
        } catch (Exception ex) { }
        e.setDate(d);
        EventLab.get(AddEventActivity.this).addEvent(e);
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            event_date_label.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/").append(mMonth + 1).append("/")
                    .append(mYear).append(" "));

        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            int mHourOfDay = hourOfDay;
            int mMinute = minute;
            event_time_label.setText(new StringBuffer()
                    .append(mHourOfDay).append(":").append(mMinute));

        }
    }
}
