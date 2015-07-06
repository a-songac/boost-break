package project.boostbreak.ui.fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import project.boostbreak.helper.NavigationHelper;

public class TimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {



	private static final String TIME_SECONDS = "timeInSeconds";



	public static TimePickerFragment newInstance(int timeInSeconds){
		Bundle args = new Bundle();
		args.putInt(TIME_SECONDS, timeInSeconds);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);

		int timeInSeconds = getArguments().getInt(TIME_SECONDS);

		int hour = timeInSeconds / 3600;
		int minute = (timeInSeconds / 60) % 60;

		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		int timeSeconds = 60 * (hour * 60 + minute);

		try {

			TimerFragment fragment = TimerFragment.class.cast(
                    NavigationHelper.getInstance().getContainerFragment());
            fragment.onTimeSet(timeSeconds);


		} catch (ClassCastException e) {
			Log.e("ERROR", "Timer Fragment does not implement TimePickerDialogCallBack interface");
		}


	}
	
}