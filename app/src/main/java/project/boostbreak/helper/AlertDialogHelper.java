package project.boostbreak.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.sql.SQLException;

import project.boostbreak.R;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.UiUtils;
import project.boostbreak.ui.fragment.ExerciseAdditionCallBack;
import project.boostbreak.ui.view.LogUtils;
import project.boostbreak.ui.view.binder.AddExerciseFormViewBinder;
import project.boostbreak.ui.view.holder.AddExerciseFormViewHolder;

/**
 * Created by Arnaud on 2015-09-03.
 */
public class AlertDialogHelper {

    public static void addExerciseAlertDialog(final ExerciseAdditionCallBack callBack) {

        final Context context = BoostBreakApplication.getGlobalContext();

        final View formView = LayoutInflater.from(context).inflate(R.layout.add_exercise_layout, null);
        final AddExerciseFormViewHolder viewHolder = new AddExerciseFormViewHolder(formView);
        final AddExerciseFormViewBinder viewBinder = new AddExerciseFormViewBinder(viewHolder);
        viewBinder.bind();


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.exercise_list_add_dialog_titile)
                .setView(formView)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Override behaviour
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewBinder.validateForm()) {
                    Exercise exercise = viewBinder.retrieveData();
                    ExerciseDAO exerciseDAO = ExerciseDAO.getInstance();
                    try{
                        exerciseDAO.open();
                        exerciseDAO.addNewExercise(
                                exercise.getName(),
                                exercise.getDescription(),
                                exercise.getCategory());

                        exerciseDAO.close();
                        callBack.onNewExerciseAdded();

                    } catch (SQLException e) {
                        LogUtils.error(this.getClass(), "getView", "Unable to open exerciseDAO: " + Log.getStackTraceString(e));
                    }

                    dialog.dismiss();

                } else {

                    UiUtils.displayToast(context.getString(R.string.add_exercise_form_error));

                }

            }
        });

    }
}
