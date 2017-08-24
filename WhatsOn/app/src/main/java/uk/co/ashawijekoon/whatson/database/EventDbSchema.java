package uk.co.ashawijekoon.whatson.database;

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
        }
    }
}
