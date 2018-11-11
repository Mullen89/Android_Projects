package mullen.liftnotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmMonitorReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
//            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Intent intentAlarm = new Intent(context, AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentAlarm, 0);
//            Calendar time = Calendar.getInstance();
////            time.set(Calendar.HOUR_OF_DAY, 23);
////            time.set(Calendar.MINUTE, 59);
////            time.set(Calendar.SECOND, 0);
//            time.setTimeInMillis(System.currentTimeMillis());
//            time.add(Calendar.SECOND, 10);
//            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
//        }
    }
}
