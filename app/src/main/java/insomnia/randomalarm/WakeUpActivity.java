package insomnia.randomalarm;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by OwnerMC on 16/01/27.
 */
public class WakeUpActivity extends Activity{
    private MediaPlayer mMediaPlayer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        // スクリーンロックを解除する
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 音を鳴らす
        if (mMediaPlayer == null)
            // TODO: 16/01/27 DBから音楽をRandomで取得してstartする
            // resのrawディレクトリにtest.mp3を置いてある場合
            mMediaPlayer = MediaPlayer.create(this, R.raw.winter);
        mMediaPlayer.start();
    }

    //swipe eventでactivityをfinish()してDestroy()する
    //tap eventならrepeateかstopかの分岐

    @Override
    public void onDestroy() {
        super.onDestroy();
        StopSound();
    }

    private void StopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //alarmNowText = (TextView) findViewById(R.id.alarm_now_time);
        //handler.sendEmptyMessage(WHAT);
        // mam.stopAlarm();
    }

}
