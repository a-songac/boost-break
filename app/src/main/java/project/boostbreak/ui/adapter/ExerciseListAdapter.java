package project.boostbreak.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import project.boostbreak.R;
import project.boostbreak.model.Exercise;
import project.boostbreak.ui.core.BaseViewHolder;

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
    public View getView(int position, View convertView, ViewGroup parent) {

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

