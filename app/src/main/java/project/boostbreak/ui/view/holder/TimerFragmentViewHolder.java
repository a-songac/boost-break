package project.boostbreak.ui.view.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import project.boostbreak.R;
import project.boostbreak.ui.core.BaseViewHolder;

/**
 * Class to implement timer fragment view holder
 */
public class TimerFragmentViewHolder extends BaseViewHolder{

    /**
     * Set time button
     */
    private Button setTimeButton;

    /**
     * Start timer button
     */
    private ToggleButton startTimerButton;

    /**
     * Reset timer button
     */
    private Button resetTimerButton;

    /**
     * Timer text view
     */
    private TextView timer;

    /**
     * Constructor
     * @param view : parent view
     */
    public TimerFragmentViewHolder(View view) {
        super(view);

        startTimerButton = ToggleButton.class.cast(view.findViewById(R.id.startButton));
        setTimeButton = Button.class.cast(view.findViewById(R.id.setTimeButton));
        resetTimerButton = Button.class.cast(view.findViewById(R.id.resetButton));
        timer = TextView.class.cast(view.findViewById(R.id.time_text_view));
    }

    public Button getSetTimeButton() {
        return setTimeButton;
    }

    public ToggleButton getStartTimerButton() {
        return startTimerButton;
    }

    public Button getResetTimerButton() {
        return resetTimerButton;
    }

    public TextView getTimer() {
        return timer;
    }

}
