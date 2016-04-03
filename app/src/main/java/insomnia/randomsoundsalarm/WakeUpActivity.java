package insomnia.randomsoundsalarm;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OwnerMC on 16/01/27.
 */
public class WakeUpActivity extends Activity{

    private MediaPlayer mMediaPlayer;
    private CountDownTimer countDownTimer;
    private final long MILLIS_IN_FUTURE = 600000;
    private final long COUNT_DOWN_INTERVAL = 1000;
    private TextView label;
    private TextView currentTime;
    private GestureDetector mGestureDetector;
    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        intent = getIntent();

        label = (TextView)findViewById(R.id.wake_label);
        label.setText(intent.getStringExtra("ALARMITEM_LABEL"));

        currentTime = (TextView)findViewById(R.id.wake_currenttime);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        currentTime.setText(time.format(date));

        mGestureDetector = new GestureDetector(this,mOnGestureListener);

        // スクリーンロックを解除する
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        countDownTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("onTick", Long.toString(millisUntilFinished / 1000/60)
                        +":"+ Long.toString(millisUntilFinished / 1000 % 60));
            }
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
//        Log.d("onTouchEvent", "x:" + event.getX() + " y:" + event.getY());
        return false;
    }

    GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {return false;}
        @Override
        public void onShowPress(MotionEvent e) {}

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            Log.d("WakeUpActivity", "onSingleTapUp");
            finish();
            return false;
        }

        float sumDistance = 0;
        float beforeDistanceX = 0;
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.d("WakeUpActivity","onScroll distanceX:" + String.valueOf(distanceX) + " distanceY:" + String.valueOf(distanceY));
            sumDistance = beforeDistanceX + distanceX;
            if(sumDistance > 50 | sumDistance < -50) {
//                Log.d("sumDistance", "Done");
//                Log.d("WakeUpActivity", "onScroll");
                finish();
            }
            beforeDistanceX = distanceX;

            return false;
        }
        @Override
        public void onLongPress(MotionEvent e) {}
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {return false;}
    };


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WakeUpActivity", "onResume");
        soundSetAndStart();
    }

    public void soundSetAndStart(){
        // 音を鳴らす
        if(mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        try {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
            MediaPlayer m = new MediaPlayer();
            m.setDataSource(helper.getMediaPath());
            mMediaPlayer = m;
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("onCompletion", "SoundComplete");
                soundSetAndStart();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        countDownTimer.cancel();
//        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
//        AlarmItem alarmItem = helper.getAlarmItemByID(intent.getIntExtra("ALARM_ID", 100));
//        MyAlarmManager alarmManager = new MyAlarmManager(getApplicationContext());
//        alarmManager.cancelAlarm(alarmItem);
        Log.d("WakeUpActivity", "finish()");
    }
}
