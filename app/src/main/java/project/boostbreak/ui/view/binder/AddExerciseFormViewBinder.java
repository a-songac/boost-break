package project.boostbreak.ui.view.binder;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import project.boostbreak.R;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.core.BaseViewBinder;
import project.boostbreak.ui.core.BaseViewHolder;
import project.boostbreak.ui.view.holder.AddExerciseFormViewHolder;

/**
 * Class to implement add exercise form view binder
 */
public class AddExerciseFormViewBinder extends BaseViewBinder {

    private AddExerciseFormViewHolder viewHolder;

    public AddExerciseFormViewBinder(BaseViewHolder viewHolder) {
        super(viewHolder);
        this.viewHolder = AddExerciseFormViewHolder.class.cast(viewHolder);
    }

    /**
     * Bind data to view
     */
    public void bind() {

        Context context = BoostBreakApplication.getGlobalContext();

        Spinner categorySpinner = viewHolder.getExerciseCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                context.getResources().getStringArray(R.array.exercise_category));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


    }

    /**
     * Retrieve form data and create an exercise item
     * @return Exercise: Newly created exercise
     */
    public Exercise retrieveData() {
        Exercise newExercise = new Exercise();
        newExercise.setName(viewHolder.getExerciseName().getText().toString());
        newExercise.setDescription(viewHolder.getExerciseDescription().getText().toString());
        newExercise.setCategory(viewHolder.getExerciseCategory().getSelectedItemPosition());
        return newExercise;
    }

    /**
     * Validate form
     * @return boolean: Returns true if form is valid
     */
    public boolean validateForm() {
        //TODO Further validation
        return !viewHolder.getExerciseDescription().getText().toString().isEmpty() &&
                !viewHolder.getExerciseName().getText().toString().isEmpty();

    }
}
