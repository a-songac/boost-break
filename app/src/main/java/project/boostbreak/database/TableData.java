package project.boostbreak.database;

import android.provider.BaseColumns;

/**
 * Created by arnaud on 15-05-05.
 */
public class TableData {

    public TableData(){}

    public static abstract class TableInfo implements BaseColumns
    {

        /* Inner class that defines the table contents */
        public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

        }
    }
}
