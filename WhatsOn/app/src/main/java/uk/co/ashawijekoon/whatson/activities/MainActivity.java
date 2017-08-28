package uk.co.ashawijekoon.whatson.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.List;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.adapter.EventAdapter;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ListView mListView;
    private FloatingActionButton mAddEventButton;

    private String[] mDrawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddEventButton = (FloatingActionButton) findViewById(R.id.addEventActionButton);
        mListView = (ListView) findViewById(R.id.mListView);

        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openAddEvent();
            }
        });

        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        //List
        TabHost.TabSpec listTab = tabHost.newTabSpec("list");
        listTab.setContent(R.id.mListView);
        listTab.setIndicator("Events");
        tabHost.addTab(listTab);

        //Map
//        TabHost.TabSpec mapTab = tabHost.newTabSpec("map");
//        mapTab.setContent(R.layout.activity_maps);
//        mapTab.setIndicator("See Map");
//        tabHost.addTab(mapTab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

}
