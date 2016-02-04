package insomnia.randomalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by OwnerMC on 16/01/27.
 */
public class MyAlarmService extends Service {

    //private final IBinder mBinder = new MyServiceLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
        //return mBinder;
    }

    @Override
    public void onCreate() {
        Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
        thr.start();
    }
    // アラーム用サービス
    Runnable mTask = new Runnable() {
        public void run() {
            // アラームを受け取るActivityを指定
            Intent alarmBroadcast = new Intent();
            // ここでActionをセットする(Manifestに書いたものと同じであれば何でもよい)
            alarmBroadcast.setAction("MyAlarmAction");
            // レシーバーへ渡す
            sendBroadcast(alarmBroadcast);
            // 役目を終えたサービスを止める
            MyAlarmService.this.stopSelf();
        }
    };

}
