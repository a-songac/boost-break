package project.boostbreak.application;

import android.app.Application;
import android.content.Context;

import project.boostbreak.LogUtils;

/**
 * Class to implement boost break application
 */
public class BoostBreakApplication extends Application{

    private static Context GLOBAL_CONTEXT;

    private static boolean mainActivityVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        GLOBAL_CONTEXT = getApplicationContext();
        LogUtils.debug(this.getClass(), "onCreate", "Global context: " + GLOBAL_CONTEXT);

    }


    /**
     * Get application global context1
     * @return Context : global context
     */
    public static Context getGlobalContext(){
        return GLOBAL_CONTEXT;
    }
    /**
     * Set the application global context (preferred to initialize it with activity context)
     * @param activityContext : global context of application
     */
    public static void setGlobalContext(final Context activityContext) {

        GLOBAL_CONTEXT = activityContext;
    }



    public static void mainActivityPaused() { mainActivityVisible = false;}
    public static void mainActivityResumed() {mainActivityVisible = true;}
    public static boolean isMainActivityVisible() {return  mainActivityVisible;}






}
