package project.boostbreak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class ExerciseListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    // holder view
    static class ViewHolder {
        TextView titleTextView;
        TextView descTextView;
        Switch activeSwitch;
    }

    public ExerciseListAdapter(Context context, String[] values) {
        super(context, R.layout.exercise_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if(convertView == null){
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.exercise_list_item, parent, false);

            // set up the view holder
            mViewHolder = new ViewHolder();
            mViewHolder.titleTextView = (TextView) convertView.findViewById(
                    R.id.exercise_list_item);
            mViewHolder.descTextView = (TextView) convertView.findViewById(
                    R.id.exercise_short_desc_textView);
            mViewHolder.activeSwitch = (Switch) convertView.findViewById(R.id.active_switch);

            // store the holder with the view
            convertView.setTag(mViewHolder);

        }else{
            // use the view holder
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.titleTextView.setText(values[position]);
        mViewHolder.descTextView.setText("Short description of the exercise");

        return convertView;
    }


}

