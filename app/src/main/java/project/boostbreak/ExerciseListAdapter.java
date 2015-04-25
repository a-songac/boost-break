package project.boostbreak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ExerciseListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ExerciseListAdapter(Context context, String[] values) {
        super(context, R.layout.exercise_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.exercise_list_item, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.exercise_list_item);
        TextView descTextView = (TextView) rowView.findViewById(R.id.exercise_short_desc_textView);
        Switch activeSwitch = (Switch) rowView.findViewById(R.id.active_switch);
        titleTextView.setText(values[position]);
        descTextView.setText("Short description of the exercise");

        return rowView;
    }
}

