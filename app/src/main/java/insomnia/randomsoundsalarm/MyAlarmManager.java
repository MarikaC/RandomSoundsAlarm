package insomnia.randomsoundsalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by OwnerMC on 16/01/18.
 */
public class MyAlarmManager {
    Context context;

    public MyAlarmManager(Context c) {
        // init
        this.context = c;
    }

    // TODO: 16/01/27  DBからAlarmItemの情報持ってきてaddAlarmでsetする
    public void addAlarm(AlarmItem alarmItem){
        // アラームを設定する
        Intent intent = getMyIntent(alarmItem);
        intent.putExtra("ALARMITEM_IMAGEFILEPATH", alarmItem.getImageFilePath());
        intent.putExtra("ALARMITEM_LABEL", alarmItem.getLabel());
        PendingIntent mAlarmSender = PendingIntent.getService(context, alarmItem.getId(),
                intent, 0);
        // 過去だったら明日にする
        if (alarmItem.getTriggerTime() < System.currentTimeMillis()) {
            long oneday = 86400000;
            alarmItem.setTriggerTime(alarmItem.getTriggerTime() + oneday);
        }
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, alarmItem.getTriggerTime(), mAlarmSender);
    }

    public void cancelAlarm(AlarmItem alarmItem) {
        if(isSetPendingIntent(alarmItem)) {
            Intent intent = getMyIntent(alarmItem);
            PendingIntent mAlarmSender = PendingIntent.getService(context, alarmItem.getId(),
                    intent, 0);
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(mAlarmSender);
            mAlarmSender.cancel();
            Log.d("cancelAlarm","Done");
        }
    }

    private boolean isSetPendingIntent(AlarmItem alarmItem){
        Intent intent = getMyIntent(alarmItem);
        PendingIntent mAlarmSender = PendingIntent.getService(context,alarmItem.getId(),
                intent,PendingIntent.FLAG_NO_CREATE);
        if(mAlarmSender == null){
            Log.d("isSetPendingIntent","return false");
            return false;
        }else{
            Log.d("isSetPendingIntent","return true");
            return true;
        }
    }

    private Intent getMyIntent(AlarmItem alarmItem) {
        // アラーム時に起動するアプリケーションを登録
        Intent intent = new Intent(context, MyAlarmService.class);
        Log.d("alarmItem.getId()",String.valueOf(alarmItem.getId()));
        intent.setType(String.valueOf(alarmItem.getId()));
        return intent;
    }
}

