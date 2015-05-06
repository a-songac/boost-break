package project.boostbreak;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {

	private Activity mActivity;
	// callback interface
	private TimePickerDialog.OnTimeSetListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;

		// This error will remind you to implement an OnTimeSetListener
		//   in your Activity if you forget
		try {
			mListener = (TimePickerDialog.OnTimeSetListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
		}
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		
		int hour = MainActivity.chosenTimeInSeconds/3600;
		int minute = (MainActivity.chosenTimeInSeconds/60) % 60;

		/* Create a new instance of TimePickerDialog and return it
		 * Set true for 24hour format*/
		return new TimePickerDialog(getActivity(), mListener, hour, minute,
				true);
	}
	
	
	public void onTimeSet(TimePicker view, int hour, int minute) {
		// todo properly send data to activity
		//when time is set, send it to the timer
//		MainActivity.chosenTimeInSeconds = 60*(hour*60 + minute);
//		MainActivity.timeInSeconds = MainActivity.chosenTimeInSeconds;
//		MainActivity.timerValue.setText(MainActivity.setTextTime(60*(hour*60 + minute)));
		
	}
	
}