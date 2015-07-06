package project.boostbreak.callback;

/**
 * Interface to implement custom time picker call back
 */
public interface TimePickerCallBack {
    /**
     * Action to perform on time set
     * @param timeSeconds : time set in seconds
     */
    void onTimeSet(int timeSeconds);
}