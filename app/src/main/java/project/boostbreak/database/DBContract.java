package project.boostbreak.database;

import android.provider.BaseColumns;

/**
 * Class to implement db contract
 */
public class DBContract {


    public DBContract(){}


    /* Inner class that defines the exercises table contents */
    public static abstract class ExerciseEntry implements BaseColumns {

        public static final String TABLE_EXERCISES = "exercise";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY = "category";

        public static final Integer UPPER_BODY = 0;
        public static final Integer LOWER_BODY = 1;
        public static final Integer STAMINA = 2;
        public static final Integer STRETCHING = 3;


    }

}
