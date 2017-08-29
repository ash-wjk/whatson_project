package uk.co.ashawijekoon.whatson.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;
import uk.co.ashawijekoon.whatson.models.Location;

import static uk.co.ashawijekoon.whatson.R.id.event_image;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button event_add;
    ImageButton event_date;
    Button event_image_upload;
    ImageButton event_time;
    Spinner event_category;
    Spinner event_location;
    EditText event_title;
    EditText event_description;
    TextView event_date_label;
    TextView event_time_label;
    ImageView event_image;
    private TimePicker timePicker;

    private static final int PICK_IMAGE = 1;

    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9;

    String location;
    String category;

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
        event_location = (Spinner) findViewById(R.id.event_location);
        event_title =  (EditText) findViewById(R.id.event_title);
        event_description = (EditText) findViewById(R.id.event_description);
        event_date_label = (TextView) findViewById(R.id.event_date_label);
        event_time_label = (TextView) findViewById(R.id.event_time_label);
        event_image = (ImageView) findViewById(R.id.event_image);

        timePicker = new TimePicker(this);

        event_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
                finish();
            }
        });

        event_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , PICK_IMAGE);
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
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.setHour(hour);
                    timePicker.setMinute(minute);
                }

                TimePickerDialog timeDialog = new TimePickerDialog(AddEventActivity.this, new mTimeSetListener(), hour, minute, false);
                timeDialog.show();
            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_categorise_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        event_category.setAdapter(catAdapter);
        event_category.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_location_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        event_location.setAdapter(locAdapter);
        event_location.setOnItemSelectedListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                event_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEvent(){
        Event e = new Event();

        e.setTitle(event_title.getText().toString());
        e.setDescription(event_description.getText().toString());
        e.setLoaction(location);
        e.setCategory(category);
        e.setDate(event_date_label.getText().toString());
        e.setTime(event_time_label.getText().toString());

        // Crompress and save image
        Bitmap bitmap = ((BitmapDrawable) event_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        e.setImage(imageInByte);

        EventLab.get(AddEventActivity.this).addEvent(e);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.event_category)
        {
            category =  adapterView.getItemAtPosition(i).toString();
        }
        else if(spinner.getId() == R.id.event_location)
        {
            String locationName =  adapterView.getItemAtPosition(i).toString();
            location = locationName;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.event_category)
        {
            //do this
        }
        else if(spinner.getId() == R.id.event_location)
        {
            //do this
        }

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
                    .append(mYear).append("/").append(pad(mMonth + 1)).append("/")
                    .append(pad(mDay)).append(" "));

        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            // set current time into textview
            event_time_label.setText(new StringBuilder().append(pad(hourOfDay))
                    .append(":").append(pad(minute)));

        }
    }


    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


}
