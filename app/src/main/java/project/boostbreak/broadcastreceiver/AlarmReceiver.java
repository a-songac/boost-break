package project.boostbreak.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import project.boostbreak.R;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.helper.NavigationHelper;
import project.boostbreak.helper.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {

		
	@Override
	public void onReceive(Context context, Intent intent) {

        Vibrator.class.cast(context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
        MediaPlayer.create(context, R.raw.boxing_bell).start();

		if (BoostBreakApplication.isMainActivityVisible()){

            NavigationHelper.getInstance().showExerciseDialogFragment(null);

		} else {

            NotificationHelper.getInstance().showAlarmNotification();
			
		}
		
	}
}
