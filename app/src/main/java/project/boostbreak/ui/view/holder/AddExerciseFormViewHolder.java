package project.boostbreak.ui.view.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import project.boostbreak.R;
import project.boostbreak.ui.core.BaseViewHolder;

/**
 * Created by Arnaud on 2015-09-03.
 */
public class AddExerciseFormViewHolder extends BaseViewHolder {

    private EditText exerciseName;
    private EditText exerciseDescription;
    private Spinner exerciseCategory;

    public AddExerciseFormViewHolder(View viewHolder) {
        super(viewHolder);

        this.exerciseName = EditText.class.cast(viewHolder.findViewById(R.id.exercise_name_edit_text));
        this.exerciseDescription = EditText.class.cast(viewHolder.findViewById(R.id.exercise_description_edit_text));
        this.exerciseCategory = Spinner.class.cast(viewHolder.findViewById(R.id.exercise_category_spinner));
    }

    public EditText getExerciseName() {
        return exerciseName;
    }

    public EditText getExerciseDescription() {
        return exerciseDescription;
    }

    public Spinner getExerciseCategory() {
        return exerciseCategory;
    }
}
