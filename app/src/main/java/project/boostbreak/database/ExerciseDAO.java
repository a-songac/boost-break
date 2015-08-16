package project.boostbreak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.boostbreak.model.Exercise;
import project.boostbreak.ui.view.LogUtils;

/**
 * Class to implement exercise DAO
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
     * Retrieve all exercises from the db
     * @return List<Exercise> : List of all exercises
     */
    public List<Exercise> getAllExercises() {

        List<Exercise> exercises = new ArrayList<>();

        Cursor cursor = db.query(
                DBContract.ExerciseEntry.TABLE_EXERCISES,
                exerciseTableColumns,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            Log.i("DB", "ExerciseDAO::getAllExercises():: Added " + exercise.getName() + " to exercise list");
            LogUtils.info(this.getClass(), "getAllExercises", "Added " + exercise.getName() + " to exercise list");
            cursor.moveToNext();
        }

        cursor.close();
        return exercises;
    }

    /**
     * Extract the db columns to create exercise object
     * @param cursor
     * @return Exercise
     */
    private Exercise cursorToExercise(Cursor cursor){
        Exercise exercise  = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setDescription(cursor.getString(2));
        exercise.setCategory(cursor.getInt(3));

        return exercise;
    }

    /**
     * Create and add new exercise to db
     * @param name : Exercise name
     * @param description : Exercise description
     * @param category : Exercise category
     */
    public Exercise addNewExercise(String name, String description, int category) {

        ContentValues newExercise = new ContentValues();
        newExercise.put(DBContract.ExerciseEntry.COLUMN_NAME, name);
        newExercise.put(DBContract.ExerciseEntry.COLUMN_DESCRIPTION, description);
        newExercise.put(DBContract.ExerciseEntry.COLUMN_CATEGORY, category);

        long insertId = db.insert(DBContract.ExerciseEntry.TABLE_EXERCISES, null, newExercise);

        Cursor cursor = db.query(
                DBContract.ExerciseEntry.TABLE_EXERCISES,
                exerciseTableColumns,
                DBContract.ExerciseEntry.COLUMN_ID + "=" + insertId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Exercise exercise = this.cursorToExercise(cursor);

        return exercise;
    }

    /**
     * Delet exercise from db
     * @param exercise : exercise to delete
     */
    public void deleteExercise(Exercise exercise) {
        long id = exercise.getId();

        db.delete(
                DBContract.ExerciseEntry.TABLE_EXERCISES,
                DBContract.ExerciseEntry.COLUMN_ID + "=" + id,
                null
        );

    }

}
