package project.boostbreak;
/*
 * TODO drawer to point to exercise list, complete with http://www.mkyong.com/android/android-listview-example/
 * TODO implement list view to see the exercises (fragment or activity?)
 * TODO implement database for all exercises
 * TODO implement the possibility to add/delete and enable/disable items
 * TODO highlight the enabled exercises
 * TODO sort list in alphabetical order
 */

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends FragmentActivity implements TimePickerDialog.OnTimeSetListener{
	private static final String TAG = "pause-resume";

    // Drawer Elements
    private String[] drawerListViewElements = {"Exercises","Statistics"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mDrawerRelativeLayout;
    private boolean drawerOpened = false;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;

	// visual elements
	private Button  setTimeButton, resetButton;
	private ToggleButton startButton;
	protected static TextView timerValue;
	
	 // fragment for the exercise
	protected ExerciseDialogFragment exerciseDialog = new ExerciseDialogFragment();
	
	// handler to manage the time flow
	private Handler customHandler = new Handler();
	
	// variables
	public static int chosenTimeInSeconds = 5;
	public static long momentAlarmTriggeredMillis = 0L;
	public static long timeRemaining= 0L;
	public static int timeInSeconds = chosenTimeInSeconds;
	public static boolean activityActive = true;
	public static boolean inPeriod = false;
		
	// intents and pending intents for the alarm
	private Intent alarmTriggeredIntent; 
	private PendingIntent alarmTriggeredPendingIntent;
	private AlarmManager alarmManager;
	
	// intent and pending intent for ongoing period notification
	private Intent ongoingPeriodIntent;
	private PendingIntent ongoingPeriodPendingIntent;


	// notification texts
	private static final CharSequence contentTitle = "Boost Break";
	private static final CharSequence contentText = "In Work Period";
	protected static final int ONGOING_NOTIFICATION_ID = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		

		// initialize the views
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.drawer_rel_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
		timerValue = (TextView) findViewById(R.id.timerTextview);
		startButton = (ToggleButton) findViewById(R.id.startButton);
		resetButton = (Button) findViewById(R.id.resetButton);
		setTimeButton = (Button) findViewById(R.id.setTimeButton);


        // Set the navigation drawer
        mTitle = getTitle();
        mDrawerTitle = "Slider Menu";
        // intents for the menu items
        final Intent exerciseListIntent =  new Intent(this, ExercisesListActivity.class);
        final Intent statsIntent =  new Intent(this, StatisticsActivity.class);


        // list adapter for drawer
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,
                drawerListViewElements ));
        mDrawerLayout.setScrimColor(0xE0000000);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Complete click listener
                switch (position){
                    case 0:
                        startActivity(exerciseListIntent);
                        break;
                    case 1:
                        startActivity(statsIntent);
                }
            }
        });

        // enabling action bar app icon and behaving it as toggle button for the drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

		
		// display the initial time on the clock
		timerValue.setText(setTextTime(chosenTimeInSeconds));

        // Announce the presence of the drawer on first time (2 sec following opening)
        if(savedInstanceState == null)
            customHandler.postDelayed(announceDrawerPresence, 2000);

		// create an alarm manager and the needed intents/pending intents
		alarmManager =  (AlarmManager) getSystemService(ALARM_SERVICE);
		
	    
		// Start the timer
		startButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.i(TAG, "Pressed start timer");
				
				
				if(isChecked){
					
					// create the pending intent for the alarm
					alarmTriggeredIntent = new Intent(MainActivity.this, AlarmReceiver.class);
					alarmTriggeredPendingIntent =  PendingIntent.getBroadcast(MainActivity.this, 0,
                            alarmTriggeredIntent, 0);

					// start the alarm
					momentAlarmTriggeredMillis = System.currentTimeMillis() + timeInSeconds*1000L;
					alarmManager.set(AlarmManager.RTC_WAKEUP,momentAlarmTriggeredMillis,
                            alarmTriggeredPendingIntent);
					Log.i(AlarmReceiver.TAG, "Alarm has been started");


					// Start the handler for timer countdown
					customHandler.post(updateTimerThread);
					// inform of the beginning of a working period is started or resumed
					if(!inPeriod){
						Toast.makeText(getApplicationContext(), "A work period has started.",
                                Toast.LENGTH_LONG).show();
						inPeriod = true;
					}else
						Toast.makeText(getApplicationContext(), "The work period has resumed.",
                                Toast.LENGTH_LONG).show();
					
				}else{// Pause the timer
					
					// stop the handler to send message to the queue
					customHandler.removeCallbacks(updateTimerThread);
					// stop the alarm
					alarmManager.cancel(alarmTriggeredPendingIntent);
					timeRemaining = momentAlarmTriggeredMillis - System.currentTimeMillis();
					//timeInSeconds = (int)timeRemaining/1000;
					Log.i(AlarmReceiver.TAG, "Alarm canceled and here is the time left: " +
                            (int)timeRemaining/1000);
					
					
				}
			}
		});
		
		// reset to original startTime
		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// stop the timer
				customHandler.removeCallbacks(updateTimerThread);
				startButton.setChecked(false);
				// initialize the timer
				timeInSeconds = chosenTimeInSeconds;
				timerValue.setText(setTextTime(chosenTimeInSeconds));
				// cancel the alarm
				alarmManager.cancel(alarmTriggeredPendingIntent);
				if(inPeriod){
					//cancel the ongoing notification when we resume the activity
					NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			        mNotificationManager.cancel(ONGOING_NOTIFICATION_ID);
			        inPeriod = false;
				 }
			}
		});
		
		// Set Time of a Work period
		setTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// stop the timer and cancel the alarm
				customHandler.removeCallbacks(updateTimerThread);
				startButton.setChecked(false);
				alarmManager.cancel(alarmTriggeredPendingIntent);
				
				DialogFragment timePickerFragment = new TimePickerFragment();
				timePickerFragment.show(getFragmentManager(), "timePicker");
			}
		});
	}
	
	 
	 
	 @Override
	 protected void onPause(){
		 super.onPause();
		 Log.i(TAG, "Main Activity went on pause");
		 //set active flag to false
		 activityActive = false;
		 
		// if a work period is ongoing, launch a notification that allows to directly go back to the main activity
		if(inPeriod){
			
			// stop the timer clock thread (not the alarm that continues in background) when the activity goes on pause
			 if(startButton.isChecked())				 
				 customHandler.removeCallbacks(updateTimerThread);
			
			// intent that to the notification to redirect the user toward the main activity
	        ongoingPeriodIntent = new Intent(MainActivity.this, MainActivity.class);
	        //set flags to resume the main activity (and not start a new one)
	       	ongoingPeriodIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        ongoingPeriodPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                    ongoingPeriodIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			
	        // content of the notification
	        NotificationCompat.Builder mBuilder = 
	                new NotificationCompat.Builder(this) 
	                .setSmallIcon(R.drawable.running_pictogram)    
	                .setContentTitle(contentTitle) 
	                .setContentText(contentText)
	                .setContentIntent(ongoingPeriodPendingIntent);
	        
	        // launch the notification
	        NotificationManager mNotificationManager = 
	        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 
	        mNotificationManager.notify(ONGOING_NOTIFICATION_ID, mBuilder.build());
	        
		}
		 
	 }
	 
	 @Override
	 protected void onResume(){
		 super.onResume();
		 Log.i(TAG, "Main activity went on resume");
		 
		 if(inPeriod){
			// cancel the ongoing notification when we resume the activity
			NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        mNotificationManager.cancel(ONGOING_NOTIFICATION_ID);
		 }
		 
		 // enable the active flag
		 activityActive = true;
		 
		// Receive intent to launch exercise dialog from notification bar.
		/* The exercise dialog is launched only if the proper intent is sent
		 * (intent when time is up on the alarm)
		 */
		 
		onNewIntent(getIntent());
		
		// when resuming the activity while the timer is still running, refresh the time shown on the timer
		if(startButton.isChecked()  && inPeriod)
        {
			// get the new value of the time remaining before the alarm triggers
			timeInSeconds = (int)(momentAlarmTriggeredMillis - System.currentTimeMillis()) / 1000;
			// re-launch the timer clock thread
			customHandler.post(updateTimerThread);
			
		}else if (!inPeriod){// when resuming and the activity is not in a period, initialize the timer
			timeInSeconds = chosenTimeInSeconds;
			timerValue.setText(setTextTime(chosenTimeInSeconds));
		}
	 }

    /**
     * For resuming the main activity from the notification bar
     *
     * */
	 @Override
	 public void onNewIntent(Intent intent){
		 Bundle extras = intent.getExtras();
		 
		 if(extras != null)
         {
			 Log.i(AlarmReceiver.TAG, "Extras received");

			 // only the extra with the key "lauchEx" and the content "1" can launch the exercise dialog
			 if (extras.containsKey(AlarmReceiver.LAUNCH_EX_KEY) &&
                     extras.getInt(AlarmReceiver.LAUNCH_EX_KEY) == AlarmReceiver.LAUNCH_EX_VALUE)
             {
				 Log.i(AlarmReceiver.TAG,
                         "Intent contains the notification intent extra for launching ex dialog");
				 // Time is up so the actual work period is done
				 inPeriod = false;
				 startButton.setChecked(false);

				 exerciseDialog.show(getFragmentManager(), "fragment_edit_name");

				 Log.i(AlarmReceiver.TAG, "Dialog launched after notification intent");
						 
				 extras.clear();

				 Log.i(AlarmReceiver.TAG, "Extras cleared");
			 }
		 }else
         {
			 Log.i(AlarmReceiver.TAG, "No extras in the received intent");
		 }
	 }

		
	/** This method displays the actual time on the clock textView
	 *  
	 * @param timeInSeconds Actual time left on the timer
	 * @return String displaying the current time left for the clock textView
	 */
	public static String setTextTime(int timeInSeconds){
		int mins = timeInSeconds / 60;
		int hours = mins / 60;
		mins = mins % 60;
		int secs = timeInSeconds % 60;
		return "" + hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
	}
	
	/** This Runnable iterates the time clock for the timer */
	private Runnable updateTimerThread = new Runnable(){		
		public void run(){
			// stop timer when it reaches zero and lunch the exercise
			if (timeInSeconds < 0){
				inPeriod = false;
				customHandler.removeCallbacks(updateTimerThread);
				startButton.setChecked(false);
				timeInSeconds = chosenTimeInSeconds;
				timerValue.setText(setTextTime(timeInSeconds));
				
				// otherwise a an intent from the notification will launch the dialog
				if(activityActive)
					exerciseDialog.show(getFragmentManager(), "fragment_edit_name");
				
			}else{
			
				// set  time shown on the clock
				timerValue.setText(setTextTime(timeInSeconds) );
				
				/* The handler sends a message to the queue every second (1000 ms) so on that
				 * frequency we set an iteration of minus 1 to the timer
				 */
				timeInSeconds -= 1;
				
				//send message to the queue every second
				customHandler.postDelayed(this, 1000);
			}
		}
	};

    /**
     * Runnable to show to the user the presence of the drawer
     */
    private Runnable announceDrawerPresence = new Runnable() {
        @Override
        public void run() {
            if (drawerOpened)
            {
                mDrawerLayout.closeDrawer(Gravity.START);
                drawerOpened = false;
            }
            else
            {
                mDrawerLayout.openDrawer(Gravity.START);
                drawerOpened = true;
                // close it in 1 second
                customHandler.postDelayed(this, 1500);
            }

        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerRelativeLayout);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		chosenTimeInSeconds = 60*(hourOfDay*60 + minute);
		timeInSeconds = MainActivity.chosenTimeInSeconds;
		timerValue.setText(setTextTime(60*(hourOfDay*60 + minute)));
	}
}
