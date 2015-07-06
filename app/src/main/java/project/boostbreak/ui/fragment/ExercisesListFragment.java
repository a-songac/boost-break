package project.boostbreak.ui.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;

import project.boostbreak.R;
import project.boostbreak.model.Exercise;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.ui.adapter.ExerciseListAdapter;

public class ExercisesListFragment extends ListFragment {


    // The list adapter for the list we are displaying
    ArrayAdapter<Exercise> mListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ExerciseDAO mExerciseDAO = new ExerciseDAO(getActivity());
        try{
            mExerciseDAO.open();
        }catch (SQLException e){
            e.printStackTrace();
            // todo handle properly the exception
            System.exit(0);
        }

        List<Exercise> exerciseList = mExerciseDAO.getAllExercises();

        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.activity_list_item, exerciseList);
        setListAdapter(adapter);

        mListAdapter = new ExerciseListAdapter(getActivity(), exerciseList.toArray(new Exercise[exerciseList.size()]));
        setListAdapter(mListAdapter);



    }

}
