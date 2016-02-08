package insomnia.randomalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by OwnerMC on 16/01/31.
 */
public class AlarmListDao extends SQLiteOpenHelper{

    private SQLiteDatabase alarm_info;
    static final String DB = "alarm_info.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "alarm_list";
    static final String CREATE_TABLE = "create table alarm_list (" +
                                            "ID              integer  primary key autoincrement," +
                                            "TriggerTime     integer  not null," +
                                            "textTriggerTime text     not null," +
                                            "Label           text     not null," +
                                            "Snooze          integer  not null," +
                                            "Mon             integer  not null," +
                                            "Tue             integer  not null," +
                                            "Wed             integer  not null," +
                                            "Thu             integer  not null," +
                                            "Fri             integer  not null," +
                                            "Sat             integer  not null," +
                                            "Sun             integer  not null," +
                                            "WhenRing        text     not null," +
                                            "ImageFilePath   text             ," +
                                            "Valid           integer  not null);";
    static final String DROP_TABLE = "drop table alarm_list;";

    public AlarmListDao(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long insertAlarmItem(AlarmItem alarmItem){
        alarm_info = getWritableDatabase();
        long ret = 0;

        if(TABLE == alarm_info.findEditTable(TABLE)) {
            Log.d("TABLE NAME IS",alarm_info.findEditTable(TABLE));

            ContentValues values = new ContentValues();
            values.put("TriggerTime", alarmItem.getTriggerTime());
            values.put("textTriggerTime", alarmItem.getTextTriggerTime());
            values.put("Label", alarmItem.getLabel());
            values.put("Snooze", alarmItem.isSnooze());
            values.put("Mon", alarmItem.isMon());
            values.put("Tue", alarmItem.isTue());
            values.put("Wed", alarmItem.isWed());
            values.put("Thu", alarmItem.isThu());
            values.put("Fri", alarmItem.isFri());
            values.put("Sat", alarmItem.isSat());
            values.put("Sun", alarmItem.isSun());
            values.put("WhenRing", alarmItem.getWhenRing());
            values.put("ImageFilePath", alarmItem.getImageFilePth());
            values.put("Valid", alarmItem.isValid());

            ret = alarm_info.insert(TABLE, null, values);

            values.clear();

            alarm_info.close();
            Log.d("ret VALUE IS",String.valueOf(ret));
        }
        return ret;
    }

    public List addAlarmItemFromDB(List mainAlarmList){
        alarm_info = getWritableDatabase();
        try {
            Cursor cursor = alarm_info.rawQuery("SELECT * FROM alarm_list;",null);
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                AlarmItem alarmItem = new AlarmItem(
                        (long) cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        ParseToBooleanForIntOfDB(cursor.getInt(4)),
                        ParseToBooleanForIntOfDB(cursor.getInt(5)),
                        ParseToBooleanForIntOfDB(cursor.getInt(6)),
                        ParseToBooleanForIntOfDB(cursor.getInt(7)),
                        ParseToBooleanForIntOfDB(cursor.getInt(8)),
                        ParseToBooleanForIntOfDB(cursor.getInt(9)),
                        ParseToBooleanForIntOfDB(cursor.getInt(10)),
                        ParseToBooleanForIntOfDB(cursor.getInt(11)),
                        cursor.getString(12),
                        cursor.getString(13),
                        ParseToBooleanForIntOfDB(cursor.getInt(14))
                );
                mainAlarmList.add(alarmItem);
            }
        }catch (NullPointerException npe){
            Log.d("***************", "error", new Throwable());
            mainAlarmList = null; //case of DB is empty
        }
        return mainAlarmList;
    }

    public boolean ParseToBooleanForIntOfDB(int intOfCursor){
        boolean b = false;
        if(intOfCursor == 1){
            b = true;
        }
        return b;
    }
}

