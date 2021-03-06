package project.boostbreak.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.boostbreak.activity.MainActivity;
import project.boostbreak.R;


public class ExerciseDialogFragment extends DialogFragment {
	
	private TextView duration, name, description;
    private static final String DIALOG_TITLE = "Let's take a break!";
	
	public void exerciseDialog() {
	        // Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.exercise, container);
	    duration = (TextView) view.findViewById(R.id.exerciseDurationTextView);
	    name = (TextView) view.findViewById(R.id.exerciseNameTextView);
	    description = (TextView) view.findViewById(R.id.exerciseDescriptionTextView);
	    getDialog().setTitle(DIALOG_TITLE);
	    
	    name.setText("Upper Body");
	    duration.setText("5 min");
	    description.setText("Do series of pushups, pull ups,  handstands...\n");

	    return view;
	}
	

}
