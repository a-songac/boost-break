package project.boostbreak.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import project.boostbreak.R;
import project.boostbreak.activity.MainActivity;
import project.boostbreak.application.BoostBreakApplication;

/**
 * Class to implement notification helper
 */
public class NotificationHelper {

    private static NotificationHelper instance;

    static {
        instance = new NotificationHelper();
    }

    /**
     * Get singleton instance
     * @return NotificationHelper : singleton instance
     */
    public static NotificationHelper getInstance() {return instance;}

    /**
     * Launch ongoing work period notification
     */
    public void showOngoingWorkPeriodNotification() {

        Context context = BoostBreakApplication.getGlobalContext();

        Intent ongoingPeriodIntent = new Intent(context, MainActivity.class);
        ongoingPeriodIntent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent ongoingPeriodPendingIntent = PendingIntent.getActivity(
                context,
                0,
                ongoingPeriodIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // content of the notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.running_pictogram)
                        .setContentTitle("Boost Break")
                        .setContentText("In Work Period")
                        .setContentIntent(ongoingPeriodPendingIntent);
        // todo: string values

        getNotificationManager().notify(
                ConstantsHelper.ONGOING_WORK_PERIOD_NOTIFICATION_ID,
                builder.build());
    }

    /**
     * Show notification
     */
    public void showAlarmNotification() {

        // todo: manage resources adequately
        CharSequence tickerText = "Boost Break!";
        CharSequence contentTitle = "Boost Break";
        CharSequence contentText = "Time to take a break";
        Uri soundURI = Uri.parse("android.resource://project.boostbreak/"+ R.raw.boxing_bell);
        long[] vibratePattern = { 0, 200, 200, 300 };


        Context context = BoostBreakApplication.getGlobalContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                context,
                ConstantsHelper.ALARM_PENDING_INTENT_ID,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(
                context)
                .setTicker(tickerText)
                .setSmallIcon(android.R.drawable.star_on)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(notificationPendingIntent);
//                .setSound(soundURI)
//                .setVibrate(vibratePattern);

        getNotificationManager().cancel(ConstantsHelper.ONGOING_WORK_PERIOD_NOTIFICATION_ID);
        getNotificationManager().notify(
                ConstantsHelper.ALARM_NOTIFICATION_ID,
                notificationBuilder.build());
    }



    /**
     * Cancel alarm notification
     */
    public void cancelAlarmNotification() {

        getNotificationManager().cancel(ConstantsHelper.ALARM_NOTIFICATION_ID);

    }

    /**
     * Cancel ongoing work period notification
     */
    public void cancelOngoingWorkPeriodNotification() {

        getNotificationManager().cancel(ConstantsHelper.ONGOING_WORK_PERIOD_NOTIFICATION_ID);

    }

    /**
     * Get notification manager
     * @return NotificationManagerCompat : notification manager
     */
    private NotificationManagerCompat getNotificationManager() {

        Context context = BoostBreakApplication.getGlobalContext();

        return NotificationManagerCompat.from(context);
    }

}
