package project.boostbreak.ui.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import project.boostbreak.R;
import project.boostbreak.UiUtils;
import project.boostbreak.broadcastreceiver.AlarmReceiver;
import project.boostbreak.callback.ITimePickerCallBack;
import project.boostbreak.helper.ActionBarHelper;
import project.boostbreak.helper.ConstantsHelper;
import project.boostbreak.helper.NavigationHelper;
import project.boostbreak.helper.NotificationHelper;
import project.boostbreak.ui.core.BaseFragment;
import project.boostbreak.ui.view.binder.TimerFragmentViewBinder;
import project.boostbreak.ui.view.holder.TimerFragmentViewHolder;

/**
 * Class to implement timer fragment
 */
public class TimerFragment extends Fragment implements ITimePickerCallBack, BaseFragment {

    /**
     * Alarm manager
     */
    private AlarmManager alarmManager;

    /**
     * holder of the view
     */
    private TimerFragmentViewHolder viewHolder;

    /**
     * Binder of the view
     */
    private TimerFragmentViewBinder viewBinder;

    /**
     * Moment at which alarm will trigger
     */
    private long momentAlarmTriggersMillis = -1;

    /**
     * Alarm duration
     */
    private int chosenTimeInSeconds = ConstantsHelper.DEFAULT_INITIAL_TIME_SECONDS;

    /**
     * Current time on the timer
     */
    private int timeInSeconds = ConstantsHelper.DEFAULT_INITIAL_TIME_SECONDS;

    /**
     * Handler
     */
    private Handler handler = new Handler();

    /**
     * Alarm pending intent
     */
    private PendingIntent alarmTriggeredPendingIntent;

    /**
     * Drawer toggle
     */
    private ActionBarDrawerToggle drawerToggle;

    /**
     * Whether activity is being recreated after configuration change
     */
    private boolean activityRecreated = false;


    /**
     * Constructor
     */
    public TimerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            activityRecreated = true;

            momentAlarmTriggersMillis = savedInstanceState.getLong(
                    ConstantsHelper.MOMENT_ALARM_TRIGGERS_MILLI_TAG,
                    -1);
            chosenTimeInSeconds = savedInstanceState.getInt(
                    ConstantsHelper.CHOSEN_TIME_IN_SECONDS_TAG,
                    ConstantsHelper.DEFAULT_INITIAL_TIME_SECONDS);

            timeInSeconds = savedInstanceState.getInt(
                    ConstantsHelper.TIME_IN_SECONDS_TAG,
                    chosenTimeInSeconds);

        }

        ActionBarHelper.getInstance().setTimerFragmentActionBar();

        setHasOptionsMenu(true);

        this.viewHolder = new TimerFragmentViewHolder(getView());
        this.viewBinder = new TimerFragmentViewBinder(viewHolder);

        viewBinder.bind(timeInSeconds);

        this.alarmManager = AlarmManager.class.cast(
                getActivity().getSystemService(Context.ALARM_SERVICE));

        this.setStartTimerButtonBehaviour();
        this.setResetButtonBehaviour();
        this.setSetTimeButtonBehaviour();
        this.setNavigationDrawer();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (isInPeriod()) {

            NotificationHelper.getInstance().cancelOngoingWorkPeriodNotification();

            if (viewHolder.getStartTimerButton().isChecked() && !activityRecreated) {
                timeInSeconds = (int)((momentAlarmTriggersMillis - System.currentTimeMillis()) / 1000);
                handler.post(updateTimerThread);
            }

        }else {

            timeInSeconds = chosenTimeInSeconds;
            viewBinder.resetTimer(timeInSeconds);

        }

        viewHolder.getDrawerLayout().closeDrawer(Gravity.START);

        activityRecreated = false;
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(updateTimerThread);

        if(isInPeriod()){
            NotificationHelper.getInstance().showOngoingWorkPeriodNotification();
        }

    }

    @Override
    public void onTimeSet( int timeSeconds) {

        chosenTimeInSeconds = timeInSeconds = timeSeconds;
        viewBinder.resetTimer(chosenTimeInSeconds = timeSeconds);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(ConstantsHelper.MOMENT_ALARM_TRIGGERS_MILLI_TAG, momentAlarmTriggersMillis);
        outState.putInt(ConstantsHelper.CHOSEN_TIME_IN_SECONDS_TAG, chosenTimeInSeconds);
        outState.putInt(ConstantsHelper.TIME_IN_SECONDS_TAG, timeInSeconds);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
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
    public void onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = viewHolder.getDrawerLayout().isDrawerOpen(
                viewHolder.getDrawerRelativeLayout());
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
        drawerToggle.syncState();
    }

    /**
     * Set start time button behaviour
     */
    private void setStartTimerButtonBehaviour() {
        this.viewHolder.getStartTimerButton().setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (!activityRecreated) {

                        momentAlarmTriggersMillis = System.currentTimeMillis() + timeInSeconds * 1000L;

                        Intent alarmTriggeredIntent = new Intent(getActivity(), AlarmReceiver.class);
                        alarmTriggeredPendingIntent = PendingIntent.getBroadcast(
                                getActivity(),
                                0,
                                alarmTriggeredIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                momentAlarmTriggersMillis,
                                alarmTriggeredPendingIntent);
                    }

                    handler.post(updateTimerThread);

                    if (!activityRecreated) {

                        //TODO String resource
                        String toastMessage = isInPeriod() ?
                                "The work period has resumed." :
                                "A work period has started";

                        UiUtils.displayToastLong(toastMessage);
                    }

                } else {

                    handler.removeCallbacks(updateTimerThread);
                    alarmManager.cancel(alarmTriggeredPendingIntent);

                }

            }
        });
    }

    private void setResetButtonBehaviour() {

        viewHolder.getResetTimerButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.removeCallbacks(updateTimerThread);
                viewBinder.resetTimer(chosenTimeInSeconds);
                timeInSeconds = chosenTimeInSeconds;
                alarmManager.cancel(alarmTriggeredPendingIntent);
                momentAlarmTriggersMillis = -1;

            }
        });
    }

    /**
     * Set set time button behaviour
     */
    private void setSetTimeButtonBehaviour() {

        viewHolder.getSetTimeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // stop the timer and cancel the alarm
                handler.removeCallbacks(updateTimerThread);
                viewHolder.getStartTimerButton().setChecked(false);
                momentAlarmTriggersMillis = -1;
                NavigationHelper.getInstance().showTimePickerDialog(chosenTimeInSeconds);
            }
        });
    }

    /**
     * Set navigation drawer
     */
    private void setNavigationDrawer() {

        // todo: resources (string array)
        final String[] drawerListViewElements = {"Exercises","Statistics"};

        // list adapter for drawer
        viewHolder.getDrawerList().setAdapter(
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.drawer_list_item,
                        drawerListViewElements)
        );

        viewHolder.getDrawerLayout().setScrimColor(0xE0000000);
        viewHolder.getDrawerList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NavigationHelper.getInstance().navigateToExerciseListFragment();
                        break;

                    case 1:
                        NavigationHelper.getInstance().navigateToStatisticsFragment();
                        break;

                }
            }
        });

        this.drawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                viewHolder.getDrawerLayout(),
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                // calling onPrepareOptionsMenu() to show action bar icons
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // calling onPrepareOptionsMenu() to hide action bar icons
                getActivity().invalidateOptionsMenu();
            }
        };
        viewHolder.getDrawerLayout().setDrawerListener(drawerToggle);
    }

    /**
     * Runnable to decrement the timer clock for the timer
     **/
    private Runnable updateTimerThread = new Runnable(){
        public void run(){

            if (timeInSeconds < 0){

                handler.removeCallbacks(updateTimerThread);
                timeInSeconds = chosenTimeInSeconds;
                viewBinder.resetTimer(timeInSeconds);
                NotificationHelper.getInstance().cancelAlarmNotification();
                // Alarm receiver takes care of launching the exercise dialog

            }else{

                viewBinder.upDateTimer(timeInSeconds);
                timeInSeconds -= 1;
                handler.postDelayed(this, ConstantsHelper.ONE_SECOND_IN_MILLIS);
            }
        }
    };

    /**
     * Whether a work period is currently ongoing
     * @return boolean : true if in work period
     */
    private boolean isInPeriod() {

        return chosenTimeInSeconds > timeInSeconds;

    }


}
