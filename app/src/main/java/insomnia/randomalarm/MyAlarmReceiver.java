package insomnia.randomalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by OwnerMC on 16/01/17.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // アラームを受け取って起動するActivityを指定、起動
        Intent wakeUp = new Intent(context, WakeUpActivity.class);
        // 画面起動に必要
        wakeUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(wakeUp);
    }
}
