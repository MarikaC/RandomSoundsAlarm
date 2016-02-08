package insomnia.randomalarm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Calendar;


public class AlarmSettingActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 0;
    TimePicker timePicker;
    EditText label;
    Switch snooze;
    CheckBox Mon,Tue,Wed,Thu,Fri,Sat,Sun;
    ImageView imgView;
    String ImageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
        //timepickerを24時間表示へ
        timePicker = (TimePicker)this.findViewById(R.id.sTimePicker);
        timePicker.setIs24HourView(true);
        label = (EditText)findViewById(R.id.sLabel);
        snooze = (Switch)findViewById(R.id.sSnoozeSwitch);
        Mon = (CheckBox)findViewById(R.id.sMonCheckBox);
        Tue = (CheckBox)findViewById(R.id.sTueCheckBox);
        Wed = (CheckBox)findViewById(R.id.sWedCheckBox);
        Thu = (CheckBox)findViewById(R.id.sThuCheckBox);
        Fri = (CheckBox)findViewById(R.id.sFriCheckBox);
        Sat = (CheckBox)findViewById(R.id.sSatCheckBox);
        Sun = (CheckBox)findViewById(R.id.sSunCheckBox);
        imgView = (ImageView)findViewById(R.id.sPhotoImageView);//設定済みの場合は初期値を入れる
    }

    public void PhotoButtonOnClick(View view) {
        // ギャラリー呼び出し
        Intent intent = new Intent();
        //Intent#setTypeメソッドで, 画像全般("image/*")を指定する.
        //jpegに限定する場合は, "image/jpeg"と指定.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                //photodata取得後、bitmapへ変換
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // 選択した画像を表示
                imgView.setImageBitmap(img);
                //filepath取得部分
                ImageFilePath = getFilePath(data);
                Toast.makeText(this,ImageFilePath,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getFilePath(Intent data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = { Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    /**
     * SQliteにalarmItemのfieldを記録
     * AlarmListにlabel,whenRing,validを記録
     */
    public void CreateButtonOnClick(View view){

        long triggertime = ConvertTriggerTimeMilli(timePicker);
        String textTriggerTime = ParseTimepickerToString(timePicker);

        AlarmItem alarmItem = new AlarmItem(triggertime, textTriggerTime,
                label.getText().toString(),snooze.isChecked(),
                Mon.isChecked(),Tue.isChecked(),Wed.isChecked(),Thu.isChecked(),Fri.isChecked(),
                Sat.isChecked(),Sun.isChecked(),ImageFilePath);

        AlarmListDao helper = new AlarmListDao(getApplicationContext());

        if (helper.insertAlarmItem(alarmItem) == -1) {
            Toast.makeText(this, "Insert失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insert成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT | intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            startActivity(intent);
            finish();
        }
        Log.d("++++++++++++++++++", "INSERT COMPLETE");
        helper.close();

    }

    public long ConvertTriggerTimeMilli(TimePicker timePicker){
        Calendar cal = Calendar.getInstance();
        int h = timePicker.getCurrentHour().intValue();
        int m = timePicker.getCurrentMinute().intValue();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long triggertime = cal.getTimeInMillis();
        return triggertime;
    }

    public String ParseTimepickerToString(TimePicker timePicker){
        int h = timePicker.getCurrentHour().intValue();
        int m = timePicker.getCurrentMinute().intValue();
        String hour = String.valueOf(h);
        String minute = String.valueOf(m);
        if(hour.length() == 1){hour = "0" + hour;}
        if(minute.length() == 1){minute = "0" + minute;}
        return hour + ":" + minute;
    }

}
