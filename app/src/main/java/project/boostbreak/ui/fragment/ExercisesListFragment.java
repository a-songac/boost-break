package project.boostbreak.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.List;

import project.boostbreak.R;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.helper.ActionBarHelper;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.adapter.ExerciseListAdapter;
import project.boostbreak.ui.core.BaseFragment;
import project.boostbreak.ui.view.LogUtils;

public class ExercisesListFragment extends ListFragment implements BaseFragment{


    // The list adapter for the list we are displaying
    ExerciseListAdapter mListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        ActionBarHelper.getInstance().setExerciseFragmentActionBar();

        ExerciseDAO exerciseDAO = new ExerciseDAO(getActivity());
        try{
            exerciseDAO.open();

            List<Exercise> exerciseList = exerciseDAO.getAllExercises();

            mListAdapter = new ExerciseListAdapter(
                    getActivity(),
                    exerciseList);
            setListAdapter(mListAdapter);

            exerciseDAO.close();

        }catch (SQLException e){
            LogUtils.error(this.getClass(), "onActivityCreated", "Unable to open exerciseDAO");
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
