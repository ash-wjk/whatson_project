package uk.co.ashawijekoon.whatson.database;

import com.google.android.gms.location.places.Place;

/**
 * Created by Asha Wijekoon on 23/08/2017.
 */

public class EventDbSchema {

    public static final class EventTable {
        public static final String NAME = "event";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String TIME = "time";
//            public static final String LOCATION_LAT = "location_lat";
//            public static final String LOCATION_LNG = "location_lng";
            public static final String LOCATION_NAME = "location_name";
            public static final String CATEGORY = "category";
            public static final String IMAGE = "image";
        }
    }
}
