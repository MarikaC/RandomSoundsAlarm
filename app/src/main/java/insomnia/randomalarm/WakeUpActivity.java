package insomnia.randomalarm;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OwnerMC on 16/01/27.
 */
public class WakeUpActivity extends Activity{

    private MediaPlayer mMediaPlayer;
    private TextView currentTime;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        currentTime = (TextView)findViewById(R.id.wake_currenttime);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        currentTime.setText(time.format(date));

        // スクリーンロックを解除する
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("onTick", Long.toString(millisUntilFinished / 1000 % 60));
            }
            @Override
            public void onFinish() {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                finish();
            }
        }.start();
    }


    @Override
    public void finish() {
        super.finish();
        Log.d("WakeUpActivity", "finish()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WakeUpActivity", "onResume");
        // 音を鳴らす
        //mMediaPlayer = null;
        // TODO: 16/01/27 DBから音楽をRandomで取得してstartする
        // resのrawディレクトリにtest.mp3を置いてある場合
        mMediaPlayer = MediaPlayer.create(this, R.raw.winter);
//        mMediaPlayer.setOnCompletionListener(soundLooper);
        mMediaPlayer.start();
    }

//    MediaPlayer.OnCompletionListener soundLooper = new MediaPlayer.OnCompletionListener() {
//        @Override
//        public void onCompletion(MediaPlayer mp) {
//            mMediaPlayer.release();
//            Log.d("MediaPlayer:","Completion");
//        }
//    };

    //swipe eventでactivityをfinish()してDestroy()する
    //tap eventならrepeateかstopかの分岐

    private void stopSnooze(){
        MyAlarmManager alarmManager = new MyAlarmManager(this);
        //alarmManager.addAlarm();
    }

}
