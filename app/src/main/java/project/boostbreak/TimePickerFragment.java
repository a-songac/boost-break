package project.boostbreak;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {

	private Activity mActivity;

	private NoticeTimePickerDialogListener mListener;

	// callback interface for host activity
	public interface NoticeTimePickerDialogListener {

		void onTimeSet(DialogFragment dialog, int timeSeconds, int hour, int minute);

	}

	static TimePickerFragment newInstance(int timeInSeconds){
		Bundle args = new Bundle();
		args.putInt("timeInSeconds", timeInSeconds);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;

		// This error will remind you to implement an OnTimeSetListener
		//   in your Activity if you forget
		try {
			mListener = (NoticeTimePickerDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
		}
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		int seconds = this.getArguments().getInt("timeInSeconds");

		int hour = seconds/3600;
		int minute = (seconds/60) % 60;

		/* Create a new instance of TimePickerDialog and return it
		 * Set true for 24hour format*/
		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		//when time is set, send it to the timer through the callback interface
		int timeSeconds = 60*(hour*60 + minute);
		mListener.onTimeSet(TimePickerFragment.this, timeSeconds, hour, minute);


	}
	
}