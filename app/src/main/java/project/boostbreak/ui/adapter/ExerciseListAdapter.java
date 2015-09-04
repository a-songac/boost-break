package project.boostbreak.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import project.boostbreak.R;
import project.boostbreak.database.ExerciseDAO;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.core.BaseViewHolder;
import project.boostbreak.ui.view.LogUtils;

/**
 * Exercise List Adapter
 */
public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    private final Context context;
    private final List<Exercise> values;

    public ExerciseListAdapter(Context context, List<Exercise> items) {
        super(context, R.layout.exercise_list_item, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.exercise_list_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            // use the view holder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTextView.setText(values.get(position).getName());
        viewHolder.descTextView.setText(values.get(position).getDescription());
        viewHolder.activeSwitch.setChecked(values.get(position).isEnabled());
        viewHolder.activeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Exercise exercise = getItem(position);
                exercise.setEnabled(isChecked);
                ExerciseDAO exerciseDAO = ExerciseDAO.getInstance();
                try{

                    exerciseDAO.open();
                    exerciseDAO.updateExercise(exercise);
                    exerciseDAO.close();

                }catch (SQLException e){
                    LogUtils.error(this.getClass(), "getView", "Unable to open exerciseDAO: " + Log.getStackTraceString(e));
                }
            }
        });

        return convertView;
    }


    /**
     * List item view holder
     */
    private static class ViewHolder extends BaseViewHolder {
        TextView titleTextView;
        TextView descTextView;
        Switch activeSwitch;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.exercise_list_item);
            descTextView = (TextView) view.findViewById(R.id.exercise_short_desc_textView);
            activeSwitch = (Switch) view.findViewById(R.id.active_switch);

        }

    }


}

