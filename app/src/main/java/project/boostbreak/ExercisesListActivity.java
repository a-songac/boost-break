package project.boostbreak;

import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ExercisesListActivity extends FragmentActivity {

    static final String TAG = "ExerciseListActivity";
    public boolean mIsDualPane = false;
    // Fragment containing the list of the exercises
    ExercisesListFragment mExExercisesListFragment;

    // Fragment containing an exercise description (present if 2-pane)
    ExerciseDescriptionFragment mExerciseDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // detect if dual pane
        View descriptionView = findViewById(R.id.exercises_description_frag);
        mIsDualPane = descriptionView != null && descriptionView.getVisibility() == View.VISIBLE;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
