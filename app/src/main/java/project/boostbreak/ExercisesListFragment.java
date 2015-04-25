package project.boostbreak;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
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

        String[] exercises = {"push up", "pull up"};

        mListAdapter = new ExerciseListAdapter(getActivity(), exercises);
        setListAdapter(mListAdapter);

    }


}
