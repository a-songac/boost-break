package project.boostbreak;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	//tag to track behavior in the logcat
	public static final String TAG = "alarm-boostBreak";

    public static final String LAUNCH_EX_KEY = " launch exercise";
    public static final int LAUNCH_EX_VALUE = 3;

	//The receiver is responsible for launching the notification if the activity was on stop
	//or showing the exercise dialog if the activity is currently running.	
	// Notification Elements when the alarm ends while the main activity is in background
	public static final CharSequence tickerText = "Boost Break!";
	public static final CharSequence contentTitle = "Boost Break";
	public static final CharSequence contentText = "Time to take a break";

	// Notification ID to allow for future updates
	public static final int ALARM_NOTIFICATION_ID = 1;
	
	//Notification intent
	private Intent mNotificationIntent;
	private PendingIntent mNotificationPendingIntent;
	
	//vibrate pattern (permission required)
	private long[] mVibratePattern = { 0, 200, 200, 300 };
	//sound for the alarm
	private Uri soundURI = Uri.parse("android.resource://project.boostbreak/"+ R.raw.boxing_bell);	
	
		
	@Override
	public void onReceive(Context context, Intent intent) {		
		Log.i(TAG, "Alarm received ");
		
		/*
		 * Only vibrate and ring tone if the activity is on foreground.
		 * The dialog is launched by the activity
		 */
		if(MainActivity.activityActive){
			Log.i(TAG, "Alarm received when main activity is running");
			
			//Vibrate and play a ring tone
			//the vibration needs to have an SDK permission in the manifest file
			Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(500);
//			 try { IMMERSION VIBRATION
//
//				 mLauncher = new Launcher(context);
//		         mLauncher.play(Launcher.DOUBLE_STRONG_CLICK_100);
//
//		      } catch (Exception e) {
//
//		          Log.e("My App", "Exception!: " + e.getMessage());
//
//		      }
			//sound
			MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boxing_bell);
			mediaPlayer.start(); 
			
						
			
		/*
		 * if the activity is not on foreground, launch a notification
		 * that when clicked will launch the dialog for the exercise
		*/
		}else{
			Log.i(TAG, "Alarm received when main activity is not on foreground");
			
			//create intent and pending intent for the notification
			mNotificationIntent = new Intent(context, MainActivity.class);
			
			/* Add flags that handle the case that the activity already exists but is only on stop
			 * IOW the main activity is resumed and not started over
			*/
			mNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);

			//add extra to notify that the intent must launch the exercise dialog
			mNotificationIntent.putExtra(LAUNCH_EX_KEY, LAUNCH_EX_VALUE);
			mNotificationPendingIntent = PendingIntent.getActivity(context, 0,	mNotificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			
			// Define the Notification's expanded message and Intent:
			Notification.Builder notificationBuilder = new Notification.Builder(
						context)
						.setTicker(tickerText)
						.setSmallIcon(android.R.drawable.star_on)
						.setAutoCancel(true)
						.setContentTitle(contentTitle)
						.setContentText(contentText)
						.setContentIntent(mNotificationPendingIntent)
						.setSound(soundURI)
						.setVibrate(mVibratePattern);

			// Pass the Notification to the NotificationManager:
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			//cancel the notification for ongoing period (if present)
			mNotificationManager.cancel(MainActivity.ONGOING_NOTIFICATION_ID);
			//launch the notification
			mNotificationManager.notify(ALARM_NOTIFICATION_ID,notificationBuilder.build());
			
		}
		
	}
}
