package project.boostbreak;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ExercisesListFragment extends ListFragment {


    // The list adapter for the list we are displaying
    ArrayAdapter<String> mListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] exercisesArr = {"push up", "pull up", "crunches", "squats", "jumping jacks", "jogging", "yoga"};

        mListAdapter = new ExerciseListAdapter(getActivity(), exercisesArr);
        setListAdapter(mListAdapter);

    }

}
