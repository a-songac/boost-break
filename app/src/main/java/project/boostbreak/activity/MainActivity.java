package project.boostbreak.activity;
/*
 * TODO drawer to point to exercise list, complete with http://www.mkyong.com/android/android-listview-example/
 * TODO implement list view to see the exercises (fragment or activity?)
 * TODO implement database for all exercises
 * TODO implement the possibility to add/delete and enable/disable items
 * TODO highlight the enabled exercises
 * TODO sort list in alphabetical order
 */


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import project.boostbreak.R;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.helper.ActionBarHelper;
import project.boostbreak.helper.ConstantsHelper;
import project.boostbreak.helper.NavigationHelper;
import project.boostbreak.helper.NotificationHelper;
import project.boostbreak.ui.core.BaseFragment;


public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        BoostBreakApplication.setGlobalContext(this);

        /*
         * Set Helpers
         */
        ActionBarHelper.getInstance().setActionBar(this.getActionBar());

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NavigationHelper.getInstance().navigateToMainFragment();
        }

	 }
	 
	 @Override
	 protected void onResume(){
		 super.onResume();
         BoostBreakApplication.mainActivityResumed();
         launchExerciseIfAlarmTriggered();
	 }

    @Override
    protected void onPause() {
        super.onPause();
        BoostBreakApplication.mainActivityPaused();


    }


    /**
     * Checks if alarm was triggered and launches the exercise dialog if it was
     */
    private void launchExerciseIfAlarmTriggered() {

        // verify if the alarm notification was launched by looking for its pending intent
        PendingIntent test = PendingIntent.getActivity(
                this,
                ConstantsHelper.ALARM_PENDING_INTENT_ID,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_NO_CREATE);

        if (test != null) {

            NotificationHelper.getInstance().cancelAlarmNotification();
            NavigationHelper.getInstance().showExerciseDialogFragment(null);
            test.cancel();

        }
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != null &&
                    fragment.isVisible() &&
                    fragment.equals(getSupportFragmentManager().findFragmentById(R.id.container))) {
                try {

                    BaseFragment.class.cast(fragment).onBackPressed();

                } catch (ClassCastException e) {

                    super.onBackPressed();
                }

                break;
            }
        }

    }
}
