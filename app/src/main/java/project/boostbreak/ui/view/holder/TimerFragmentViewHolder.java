package project.boostbreak.ui.view.holder;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
     * Drawer list view
     */
    private ListView drawerList;

    private DrawerLayout drawerLayout;

    /**
     * Drawer relative layout
     */
    private RelativeLayout drawerRelativeLayout;

    /**
     * Constructor
     * @param view : parent view
     */
    public TimerFragmentViewHolder(View view) {
        super(view);

        startTimerButton = ToggleButton.class.cast(view.findViewById(R.id.startButton));
        setTimeButton = Button.class.cast(view.findViewById(R.id.setTimeButton));
        resetTimerButton = Button.class.cast(view.findViewById(R.id.resetButton));
        drawerList = ListView.class.cast(view.findViewById(R.id.left_drawer));
        drawerLayout = DrawerLayout.class.cast(view.findViewById(R.id.drawer_layout));
        drawerRelativeLayout = RelativeLayout.class.cast(view.findViewById(R.id.drawer_rel_layout));
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

    public ListView getDrawerList() {
        return drawerList;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public RelativeLayout getDrawerRelativeLayout() {
        return drawerRelativeLayout;
    }
}
