package insomnia.randomsoundsalarm;

import android.app.Activity;
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
    private final long MILLIS_IN_FUTURE = 30000;
    private final long COUNT_DOWN_INTERVAL = 1000;
    private TextView currentTime;
    private GestureDetector mGestureDetector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

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
                Log.d("onTick", Long.toString(millisUntilFinished / 1000 % 60));
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
        Log.d("onTouchEvent", "x:" + event.getX() + " y:" + event.getY());
        return false;
    }

    GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {return false;}
        @Override
        public void onShowPress(MotionEvent e) {}

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("WakeUpActivity", "onSingleTapUp");
            finish();
            return false;
        }

        float sumDistance = 0;
        float beforeDistanceX = 0;
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("WakeUpActivity","onScroll distanceX:" + String.valueOf(distanceX) + " distanceY:" + String.valueOf(distanceY));
            sumDistance = beforeDistanceX + distanceX;
            if(sumDistance > 50 | sumDistance < -50) {
                Log.d("sumDistance","Done");
                MyAlarmManager alarmManager = new MyAlarmManager(getApplicationContext());
                alarmManager.stopSnooze();
                Log.d("WakeUpActivity", "onScroll");
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
        // 音を鳴らす
        if(mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = new MediaPlayer();
        }
        try {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
            mMediaPlayer.setDataSource(this, helper.getMediaUri());
//            mMediaPlayer.setDataSource(this,Sound.getItems(this).get(0).getUri());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mMediaPlayer.setOnCompletionListener(soundLooper);
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
        Log.d("WakeUpActivity", "finish()");
    }
}
