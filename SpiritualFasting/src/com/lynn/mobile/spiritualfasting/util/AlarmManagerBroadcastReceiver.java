package com.lynn.mobile.spiritualfasting.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import com.lynn.mobile.spiritualfasting.R;
import com.lynn.mobile.spiritualfasting.YourFastDetailActivity;
import com.lynn.mobile.spiritualfasting.database.YourFastDB;
import com.lynn.mobile.spiritualfasting.model.YourFast;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent service1 = new Intent(context, AlarmService.class);
//        context.startService(service1);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        YourFastDB yourFastDB = new YourFastDB(context);
        List<YourFast> fasts = yourFastDB.getAllItems();

        for(YourFast fast : fasts) {
            Timestamp today = new Timestamp(Calendar.getInstance().getTimeInMillis());

            if (fast.getEndDate().after(today) && fast.getStartDate().before(today)) {
                setNotification(context, fast);
            }
        }

        //Release the lock
        wl.release();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setNotification(Context context, YourFast fast) {
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        long diffTime = Calendar.getInstance().getTime().getTime() - fast.getStartDate().getTime();
        long diffDays = diffTime / (1000 * 60 * 60 * 24);

        Intent intent = new Intent(context, YourFastDetailActivity.class);
        intent.putExtra(Resources.FAST_NAME, fast.getFast().getName());
        intent.putExtra(Resources.YOUR_FAST_ID, fast.getId());
        long dayInFast = fast.getFast().getLength() - (diffDays + 1);
        intent.putExtra(Resources.PROGRESS, "Day "
                + dayInFast
                + " of " + fast.getFast().getLength());
        intent.putExtra(Resources.DAY, dayInFast);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification mNotification = new Notification.Builder(context)
                .setContentTitle("Reminder")
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setSound(soundUri)

                .addAction(R.drawable.ic_action_go_to_today, "View", pIntent)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("It is day " + (diffDays + 1) + " the " + fast.getFast().getName() + " fast. Make sure " +
                                "you read your scripture and write into your journal your thoughts for the day."))
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }

    public void setAlarm(Context context, long newFastId)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 21);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra("FastId", (int)newFastId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
