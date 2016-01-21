package insomnia.randomalarm;

import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by OwnerMC on 16/01/18.
 */
public class MyAlarmManager {
    Calendar cal;
    /*
    AlarmItemのobj持ってきてAlarmManagerでsetする
     */

    public long ConvertTriggerTimeMilli(TimePicker triggertime){
        long tt = 0;
        //timepickerの値をlongに変換する処理
        cal = Calendar.getInstance();
        cal.getTimeInMillis();
        return tt;
    }
}
