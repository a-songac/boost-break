package project.boostbreak.ui.view.binder;

import project.boostbreak.ui.core.BaseViewBinder;
import project.boostbreak.ui.core.BaseViewHolder;
import project.boostbreak.ui.view.holder.TimerFragmentViewHolder;

/**
 * Class to implement timer fragment view binder
 */
public class TimerFragmentViewBinder extends BaseViewBinder {

    private TimerFragmentViewHolder viewHolder;

    private long alartmTriggerTimeMillis = 0L;

    private int initialTimeSeconds;

    private int timeRemainingSeconds;

    /**
     * Constructor
     * @param viewHolder : Holder of the view
     */
    public TimerFragmentViewBinder(BaseViewHolder viewHolder) {
        super(viewHolder);
    }

    /**
     * Bind data to view
     * @param initialTimeSeconds : initial time on the timer
     */
    public void bind(int initialTimeSeconds){

        this.viewHolder = TimerFragmentViewHolder.class.cast(getViewHolder());
        this.initialTimeSeconds = this.timeRemainingSeconds = initialTimeSeconds;

        viewHolder.getTimer().setText(setTextTime(initialTimeSeconds));

    }

    /**
     * Reset timer view when time is up
     * @param defaultTime : default time in seconds to set on the timer
     */
    public void resetTimer(int defaultTime){
        viewHolder.getTimer().setText(setTextTime(defaultTime));
        viewHolder.getStartTimerButton().setChecked(false);
    }

    /**
     * Update time on the timer
     * @param timeInSeconds : Time in seconds
     */
    public void upDateTimer(int timeInSeconds) {
        viewHolder.getTimer().setText(setTextTime(timeInSeconds));
    }

    /** This method displays the actual time on the clock textView
     *
     * @param timeInSeconds Actual time left on the timer in seconds
     * @return String displaying the current time left for the clock textView
     */
    private static String setTextTime(int timeInSeconds){
        int minutes = timeInSeconds / 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        int secs = timeInSeconds % 60;
        return "" + hours + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", secs);
    }



}
