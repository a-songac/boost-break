package project.boostbreak;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.TimePicker;

//@SuppressLint("NewApi")
//@TargetApi(Build.VERSION_CODES.HONEYCOMB)//even though I changed the minSDK version
public class TimePickerFragment extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {

	
	
//	@SuppressLint("NewApi")
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		
		int hour = MainActivity.chosenTimeInSeconds/3600;
		int minute = (MainActivity.chosenTimeInSeconds/60) % 60;

		/* Create a new instance of TimePickerDialog and return it
		 * Set true for 24hour format*/
		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}
	
	
	public void onTimeSet(TimePicker view, int hour, int minute) {
		//when time is set, send it to the timer
		MainActivity.chosenTimeInSeconds = 60*(hour*60 + minute);
		MainActivity.timeInSeconds = MainActivity.chosenTimeInSeconds;
		MainActivity.timerValue.setText(MainActivity.setTextTime(60*(hour*60 + minute)));
		
	}
	
}