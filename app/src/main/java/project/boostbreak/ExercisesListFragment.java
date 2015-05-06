package project.boostbreak;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ExercisesListFragment extends ListFragment {

    // The list of headlines that we are displaying
    ArrayList<String> mExerciseList = new ArrayList<String>();

    // The list adapter for the list we are displaying
    ArrayAdapter<String> mListAdapter;

    // the ListView
    ListView mListView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] exercisesArr = {"push up", "pull up", "crunches", "squats", "jumping jacks", "jogging", "yoga"};

        mListAdapter = new ExerciseListAdapter(getActivity(), exercisesArr);
        setListAdapter(mListAdapter);

    }


}
