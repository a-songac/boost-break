package project.boostbreak.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaud on 15-05-07.
 */
public class ExerciseDAO {

    // Database fields
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private String[] exerciseTableColumns = {
            DBContract.ExerciseEntry.COLUMN_ID,
            DBContract.ExerciseEntry.COLUMN_NAME,
            DBContract.ExerciseEntry.COLUMN_DESCRIPTION,
            DBContract.ExerciseEntry.COLUMN_CATEGORY
            };

    public ExerciseDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    /**
     * Retrieve all exercies from the db
     * @return
     */
    public List<Exercise> getAllExercises() {

        List<Exercise> exercises = new ArrayList<>();

        Cursor cursor = db.query(DBContract.ExerciseEntry.TABLE_EXERCISES, exerciseTableColumns, null, null, null, null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            Log.i("DB", "ExerciseDAO::getAllExercises():: Added " + exercise.getName() + " to exercise list");
            cursor.moveToNext();
        }

        cursor.close();
        return exercises;
    }

    /**
     * Extract the db columns to create exercise object
     * @param cursor
     * @return
     */
    private Exercise cursorToExercise(Cursor cursor){
        Exercise exercise  = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setDescription(cursor.getString(2));
        exercise.setCategory(cursor.getInt(3));

        return exercise;
    }
}
