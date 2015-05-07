package project.boostbreak.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaud on 15-05-07.
 */
public class InitialExercises {
    public static List<ContentValues> getExercises(){
        List<ContentValues> valuesList = new ArrayList<ContentValues>();

        ContentValues pushUpValues = new ContentValues();
        pushUpValues.put(DBContract.ExerciseEntry.COLUMN_NAME, "Push Up");
        pushUpValues.put(DBContract.ExerciseEntry.COLUMN_DESCRIPTION, "Do 3 max series of push ups");
        pushUpValues.put(DBContract.ExerciseEntry.COLUMN_CATEGORY, DBContract.ExerciseEntry.UPPER_BODY);
        valuesList.add(pushUpValues);

        ContentValues pullUpValues = new ContentValues();
        pullUpValues.put(DBContract.ExerciseEntry.COLUMN_NAME, "Pull Up");
        pullUpValues.put(DBContract.ExerciseEntry.COLUMN_DESCRIPTION, "Do 3 max series of pull ups/chin ups");
        pullUpValues.put(DBContract.ExerciseEntry.COLUMN_CATEGORY, DBContract.ExerciseEntry.UPPER_BODY);
        valuesList.add(pullUpValues);

        ContentValues squatValues = new ContentValues();
        squatValues.put(DBContract.ExerciseEntry.COLUMN_NAME, "Squat");
        squatValues.put(DBContract.ExerciseEntry.COLUMN_DESCRIPTION, "Do 100 squats in 5 min");
        squatValues.put(DBContract.ExerciseEntry.COLUMN_CATEGORY, DBContract.ExerciseEntry.LOWER_BODY);
        valuesList.add(squatValues);

        return valuesList;

    }

    /**
     * Function used to load the clubs to database.
     * @param db
     */
    public static void addExercisesToDB(SQLiteDatabase db) {
        List<ContentValues> exerciseList = getExercises();

        for (ContentValues exercise : exerciseList) {
            db.insert(DBContract.ExerciseEntry.TABLE_EXERCISES, null, exercise);
        }
    }
}
