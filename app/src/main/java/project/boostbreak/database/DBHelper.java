package project.boostbreak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by arnaud on 15-05-05.
 */
public class DBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exercise.db";



    // ExerciseOLD table creation sql statement
    // todo use string builder instead
    private static final String CREATE_EXERCISE_TABLE = "CREATE TABLE "
            + DBContract.ExerciseEntry.TABLE_EXERCISES
            + "("
            + DBContract.ExerciseEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBContract.ExerciseEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + DBContract.ExerciseEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
            + DBContract.ExerciseEntry.COLUMN_CATEGORY + " INT NOT NULL"
            + ");";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create exercise table
        db.execSQL(CREATE_EXERCISE_TABLE);

        // load initial exercises in db
        InitialExercises.addExercisesToDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ExerciseEntry.TABLE_EXERCISES);
        onCreate(db);
    }
}
