package insomnia.randomalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by OwnerMC on 16/01/18.
 */
public class MyAlarmManager extends AlarmSettingActivity{
    Context context;
    AlarmManager am;
    private PendingIntent mAlarmSender;

    public MyAlarmManager(Context c) {
        // init
        this.context = c;
        this.am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
    }

    // TODO: 16/01/27  DBからAlarmItemの情報持ってきてaddAlarmでsetする
    public void addAlarm(AlarmItem alarmItem){
        // アラームを設定する
        mAlarmSender = this.getPendingIntent();
//        // 過去だったら明日にする
//        if(alarmItem.getTriggerTime() < System.currentTimeMillis()){
//            long oneday = 86400000;
//            alarmItem.setTriggerTime(alarmItem.getTriggerTime() + oneday);
//        }
        am.set(AlarmManager.RTC_WAKEUP, alarmItem.getTriggerTime(), mAlarmSender);
    }

    public void StopAlarmAtOnetime() {
        // アラームのキャンセル
        am.cancel(mAlarmSender);
    }

    public void StopSnooze() {
        // snoozeのキャンセル
        am.cancel(mAlarmSender);
    }

    private PendingIntent getPendingIntent() {
        // アラーム時に起動するアプリケーションを登録
        Intent intent = new Intent(context, MyAlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, PendingIntent.FLAG_ONE_SHOT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}

