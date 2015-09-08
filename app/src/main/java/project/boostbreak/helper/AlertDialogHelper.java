package project.boostbreak.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.sql.SQLException;

import project.boostbreak.R;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.callback.DialogResponseCallBack;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.model.Exercise;
import project.boostbreak.UiUtils;
import project.boostbreak.ui.fragment.ExerciseAdditionCallBack;
import project.boostbreak.LogUtils;
import project.boostbreak.ui.view.binder.AddExerciseFormViewBinder;
import project.boostbreak.ui.view.holder.AddExerciseFormViewHolder;

/**
 * Created by Arnaud on 2015-09-03.
 */
public class AlertDialogHelper {

    /**
     * Alert dialog to add new exercise or modify an existing one
     * @param callBack
     */
    public static void addExerciseAlertDialog(final @Nullable Exercise exercise, final ExerciseAdditionCallBack callBack) {

        final boolean newExerciseFlag = exercise == null;

        final Context context = BoostBreakApplication.getGlobalContext();

        final View formView = LayoutInflater.from(context).inflate(R.layout.add_exercise_layout, null);
        final AddExerciseFormViewHolder viewHolder = new AddExerciseFormViewHolder(formView);
        final AddExerciseFormViewBinder viewBinder = new AddExerciseFormViewBinder(viewHolder);

        if (newExerciseFlag)
            viewBinder.bind();
        else
            viewBinder.bind(exercise);


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

                    ExerciseDAO exerciseDAO = ExerciseDAO.getInstance();
                    try{
                        exerciseDAO.open();

                        if (newExerciseFlag) {
                            Exercise newExercise = viewBinder.retrieveData();
                            exerciseDAO.addNewExercise(
                                    newExercise.getName(),
                                    newExercise.getDescription(),
                                    newExercise.getCategory());
                        } else {

                            viewBinder.updateExercise(exercise);
                            exerciseDAO.updateExercise(exercise);

                        }

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

    /**
     * Delete exercises
     * @param count : number of exercises to delete
     * @param callBack : callback
     */
    public static void deleteExerciseAlertDialog(int count, final DialogResponseCallBack callBack) {

        Context context = BoostBreakApplication.getGlobalContext();

        String msg = String.format(context.getResources().getQuantityString(R.plurals.exercises_delete, count),
                count);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.exercises_delete))
                .setMessage(msg)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onNegativeResponse();
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onPositiveResponse();
                    }
                })
                .create();
        dialog.show();

    }
}
