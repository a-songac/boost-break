package project.boostbreak.helper;

import android.app.ActionBar;

import project.boostbreak.R;

/**
 * Class to implement action bar helper
 */
public class ActionBarHelper {

    /**
     * Singleton instance
     */
    private static ActionBarHelper SINGLETON_INSTANCE = new ActionBarHelper();
    public static ActionBarHelper getInstance() {
        return SINGLETON_INSTANCE;
    }

    private ActionBarHelper (){};

    private ActionBar actionBar;

    public ActionBar getActionBar() {
        return actionBar;
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    /**
     * Set timer fragment action bar
     */
    public void setTimerFragmentActionBar() {

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayUseLogoEnabled(false);

    }

    /**
     * Set exercise List fragment action bar
     */
    public void setExerciseListFragmentActionBar() {

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle(R.string.title_activity_exercises_list);
        actionBar.setDisplayUseLogoEnabled(false);

    }

}
