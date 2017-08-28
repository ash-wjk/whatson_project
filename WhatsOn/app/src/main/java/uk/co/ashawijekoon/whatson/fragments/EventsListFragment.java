package uk.co.ashawijekoon.whatson.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import uk.co.ashawijekoon.whatson.R;
import uk.co.ashawijekoon.whatson.activities.MainActivity;
import uk.co.ashawijekoon.whatson.adapter.EventAdapter;
import uk.co.ashawijekoon.whatson.database.EventLab;
import uk.co.ashawijekoon.whatson.models.Event;

import static android.R.id.list;

/**
 * Created by Asha Wijekoon on 28/08/2017.
 */

public class EventsListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillEventList();
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    private void fillEventList(){
        final List<Event> eventList = getAllEvents();
        EventAdapter adapter = new EventAdapter(getContext(), eventList);
        setListAdapter(adapter);
    }

    private List<Event> getAllEvents(){
        return EventLab.get(getContext()).getEvents();
    }
}
