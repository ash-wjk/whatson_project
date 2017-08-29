package uk.co.ashawijekoon.whatson.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Calendar;
import java.util.List;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.fragments.EventsListFragment;
import uk.co.ashawijekoon.whatson.models.Event;

import static uk.co.ashawijekoon.whatson.activities.AddEventActivity.pad;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

    Spinner event_category;
    Spinner event_location;
    ImageButton event_date;
    TextView event_date_label;

    private String location;
    private String category;

    private MapView mMapView;
    private FloatingActionButton mAddEventButton;

    private String[] mDrawerItems;

    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        event_category = (Spinner) findViewById(R.id.event_category);
        event_location = (Spinner) findViewById(R.id.event_location);
        event_date = (ImageButton) findViewById(R.id.event_date);
        event_date_label = (TextView) findViewById(R.id.event_date_label);

        // Set categorise spinner
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_categorise_array_with_all, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event_category.setAdapter(catAdapter);
        event_category.setOnItemSelectedListener(this);

        // Set location spinner
        ArrayAdapter<CharSequence> locAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_location_array_with_all, android.R.layout.simple_spinner_item);
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event_location.setAdapter(locAdapter);
        event_location.setOnItemSelectedListener(this);

        // Show date picker
        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAddEventButton = (FloatingActionButton) findViewById(R.id.addEventActionButton);


        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openAddEvent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mFragmentPagerAdapter);

        // This is required to avoid a black flash when the map is loaded.  The flash is due
        // to the use of a SurfaceView as the underlying view of the map.
        mPager.requestTransparentRegion(mPager);

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

    @Override
    protected void onResume() {
        super.onResume();
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        if (mPager.getCurrentItem() == 0 && page != null) {
            ((EventsListFragment)page).updateList();
        }
    }

    private void openAddEvent(){
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private List<Event> getAllEvents(){
        return EventLab.get(MainActivity.this).getEvents();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    }

    /** A simple FragmentPagerAdapter that returns List view and a SupportMapFragment. */
    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new EventsListFragment();
                case 1:
                    return SupportMapFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Events";
                case 1:
                    return "See in Map";
                default:
                    return null;
            }
        }


    }

}
