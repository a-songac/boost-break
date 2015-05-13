package project.boostbreak.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {

	// callback interface for host activity
	public interface NoticeTimePickerDialogListener {
		void onTimeSet(DialogFragment dialog, int timeSeconds, int hour, int minute);
	}

	private Activity mActivity;
	private NoticeTimePickerDialogListener mListener;
	private static final String TIME_SECONDS = "timeInSeconds";



	public static TimePickerFragment newInstance(int timeInSeconds){
		Bundle args = new Bundle();
		args.putInt(TIME_SECONDS, timeInSeconds);
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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);

		int timeInSeconds = getArguments().getInt(TIME_SECONDS);

		int hour = timeInSeconds / 3600;
		int minute = (timeInSeconds / 60) % 60;

		/* Create a new instance of TimePickerDialog and return it
		 * Set true for 24hour format */
		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		//when time is set, send it to the timer through the callback interface
		int timeSeconds = 60 * (hour * 60 + minute);
		mListener.onTimeSet(TimePickerFragment.this, timeSeconds, hour, minute);


	}
	
}