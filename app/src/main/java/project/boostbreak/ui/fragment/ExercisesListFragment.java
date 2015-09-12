package project.boostbreak.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import java.sql.SQLException;
import java.util.List;

import project.boostbreak.R;
import project.boostbreak.callback.IExerciseAdditionCallBack;
import project.boostbreak.callback.IDialogResponseCallBack;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.helper.ActionBarHelper;
import project.boostbreak.helper.AlertDialogHelper;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.adapter.ExerciseListAdapter;
import project.boostbreak.ui.core.BaseFragment;
import project.boostbreak.LogUtils;

public class ExercisesListFragment extends ListFragment implements BaseFragment{


    private ExerciseListAdapter listAdapter;
    private  List<Exercise> exerciseList;
    private ExerciseDAO exerciseDAO = ExerciseDAO.getInstance();

    private ActionMode actionMode = null;
    private int itemsSelected = 0;

    /**
     * Action mode call back for list items multi choice mode
     */
    private AbsListView.MultiChoiceModeListener actionModeCallBack = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.exercises_list_action_mode_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_exercises:
                    AlertDialogHelper.deleteExerciseAlertDialog(itemsSelected, new IDialogResponseCallBack() {
                        @Override
                        public void onPositiveResponse() {
                            deleteExercises(listAdapter.getSelectedItems());
                            actionMode.finish();
                        }

                        @Override
                        public void onNegativeResponse() {

                        }
                    });
                    break;
                case R.id.modify_exercises:
                    modifyExercise(listAdapter.getSelectedItems());
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            itemsSelected = 0;
            listAdapter.clearSelection();
            listAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.exercise_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        ActionBarHelper.getInstance().setExerciseListFragmentActionBar();

        try{
            exerciseDAO.open();
            exerciseList = exerciseDAO.getAllExercises();
            listAdapter = new ExerciseListAdapter(
                    getActivity(),
                    exerciseList);
            setListAdapter(listAdapter);

            exerciseDAO.close();

        }catch (SQLException e){
            LogUtils.error(this.getClass(), "onActivityCreated", "Unable to open exerciseDAO");
            e.printStackTrace();
        }

        // Set action mode
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        setListItemLongClickBehaviours();
        setListItemClickListener();
        getListView().setMultiChoiceModeListener(actionModeCallBack);

    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.exercises_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.add_exercise:

                AlertDialogHelper.addExerciseAlertDialog(null, new IExerciseAdditionCallBack() {
                    @Override
                    public void onNewExerciseAdded() {

                        try {
                            exerciseDAO.open();
                            exerciseList.clear();
                            exerciseList.addAll(exerciseDAO.getAllExercises());
                            listAdapter.notifyDataSetChanged();
                            exerciseDAO.close();
                        } catch (SQLException e) {
                            LogUtils.error(this.getClass(), "onActivityCreated", "Unable to open exerciseDAO");
                            e.printStackTrace();
                        }
                    }
                });

                return  true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Set on list item long click behaviour
     */
    private void setListItemLongClickBehaviours() {

        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (actionMode == null) {

                    actionMode = getActivity().startActionMode(actionModeCallBack);
                    listAdapter.setItemSelected(position, true);
                    itemsSelected++;

                } else {
                    manageSelectionInActionMode(position);
                }

                updateActionBar();
                return true;
            }
        });

    }

    /**
     * Set on list item click behaviour
     */
    private void setListItemClickListener() {
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                manageSelectionInActionMode(position);
                updateActionBar();

            }
        });
    }

    /**
     * Handle item selection in action mode
     * @param position : item position
     */
    private void manageSelectionInActionMode(int position) {

        if (actionMode != null) {
            if (listAdapter.isItemSelected(position)) {

                listAdapter.setItemSelected(position, false);
                itemsSelected--;

                if (itemsSelected == 0) {
                    actionMode.finish();
                }

            } else {

                listAdapter.setItemSelected(position, true);
                itemsSelected++;

            }
        }
    }

    /**
     * Update action bar after selection changes
     */
    private void updateActionBar() {
        if (actionMode != null) {
            actionMode.getMenu().getItem(0).setVisible(itemsSelected == 1);
            actionMode.setTitle(itemsSelected + " Selected");

        }
    }


    /**
     * Modify first exercise with value set to true
     * @param selectedItems : SparseBooleanArray of items
     */
    private void modifyExercise(SparseBooleanArray selectedItems) {

        int selectedItem = -1;
        for (int i = 0; i < exerciseList.size() ; i ++) {
            if (selectedItems.get(i)) {
                selectedItem = i;
                break;
            }
        }


        LogUtils.info(this.getClass(), "modifyExercises", "Selected item position: " + selectedItem);

        if (selectedItem >= 0) {

            final Exercise exercise = exerciseList.get(selectedItem);

            AlertDialogHelper.addExerciseAlertDialog(exercise, new IExerciseAdditionCallBack() {
                @Override
                public void onNewExerciseAdded() {
                    listAdapter.notifyDataSetChanged();
                    actionMode.finish();
                }
            });

        }

    }

    /**
     * Delete selected exercises
     * @param selectedItems : SparseBooleanArray of exercises to delete
     */
    private void deleteExercises(SparseBooleanArray selectedItems) {

        try {
            exerciseDAO.open();

            for (int i = 0 ; i < exerciseList.size() ; i++) {

                if (selectedItems.get(i)) {
                    exerciseDAO.deleteExercise(exerciseList.remove(i));
                }

            }

            listAdapter.notifyDataSetChanged();

            exerciseDAO.close();
        } catch (SQLException e) {
            LogUtils.error(this.getClass(), "onActivityCreated", "Unable to open exerciseDAO");
            e.printStackTrace();
        }



    }

}
