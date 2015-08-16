package project.boostbreak.helper;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import project.boostbreak.R;
import project.boostbreak.activity.MainActivity;
import project.boostbreak.application.BoostBreakApplication;
import project.boostbreak.ui.fragment.ExerciseDialogFragment;
import project.boostbreak.ui.fragment.ExercisesListFragment;
import project.boostbreak.ui.fragment.TimePickerFragment;
import project.boostbreak.ui.fragment.TimerFragment;

/**
 * Class to implement navigation helper
 */
public class NavigationHelper {

    private static NavigationHelper instance;

    static {
        instance = new NavigationHelper();
    }

    /**
     * Get singleton instance
     * @return NavigationHelper : singleton
     */
    public static NavigationHelper getInstance() {
        return instance;
    }

    /**
     * Navigate to main fragment
     */
    public void navigateToMainFragment() {
        navigateToTimerFragment();
    }

    /**
     * Navigate to timer fragment
     */
    public void navigateToTimerFragment() {
        
        replaceFragment(
                new TimerFragment(),
                true,
                true,
                null,
                null);
        
    }

    public  void navigateToExerciseListFragment() {

        replaceFragment(
                new ExercisesListFragment(),
                true,
                true,
                null,
                null
        );

    }

    public void navigateToStatisticsFragment() {



    }

    /**
     * Show exercise dialog fragment
     * @param exerciseId : exercise id
     */
    public void showExerciseDialogFragment(@Nullable String exerciseId) {

        ExerciseDialogFragment exerciseDialogFragment = new ExerciseDialogFragment();
        exerciseDialogFragment.show(getSupportFragmentManager(), null);

    }

    /**
     * Show timer picker dialog fragment
     * @param time : time to initially display on the picker
     */
    public void showTimePickerDialog(int time) {

        DialogFragment timePickerFragment = TimePickerFragment.newInstance(time);
        timePickerFragment.show(getSupportFragmentManager(), null);
    }

    /**
     * Get fragment in the main activity frame layout container
     * @return Fragment : fragment currently
     */
    public Fragment getContainerFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    /**
     * Replace fragment
     * @param fragment : fragment to display
     * @param withTransition : whether transition is used in the replacement
     * @param addToBackStack : whether the fragment is added to the back stack
     * @param tag : fragment tag
     * @param backStackEntryName : back stack entry name
     */
    private void replaceFragment(Fragment fragment, boolean withTransition, boolean addToBackStack, 
                                @Nullable String tag, @Nullable String backStackEntryName) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackEntryName);
        }
        if (withTransition) {
            fragmentTransaction.setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right);
        }

        fragmentTransaction.commit();

    }

    /**
     * Get fragment manager
     * @return FragmentManager : fragment manager
     */
    private FragmentManager getSupportFragmentManager() {
        
        Context context = BoostBreakApplication.getGlobalContext();
        return MainActivity.class.cast(context).getSupportFragmentManager();
        
    }




}
