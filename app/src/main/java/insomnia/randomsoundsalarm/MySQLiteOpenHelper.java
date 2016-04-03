package insomnia.randomsoundsalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OwnerMC on 16/01/31.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    private SQLiteDatabase alarm_info;
    static final String DB = "alarm_info.db";
    static final int DB_VERSION = 1;
    static final String TABLE_ALARM = "alarm_list";
    static final String TABLE_SOUND = "sound_list";
    static final String CREATE_TABLE_ALARM = "create table alarm_list (" +
                                                    "ID              integer  primary key," +
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
    static final String CREATE_TABLE_SOUND = "create table sound_list (" +
                                                    "mediaId       integer," +
                                                    "name          text     not null," +
                                                    "artistName    text,"              +
                                                    "path          text      not null);";
    static final String DROP_TABLE_ALARM = "drop table alarm_list;";
    static final String DROP_TABLE_SOUND = "drop table sound_list;";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALARM);
        db.execSQL(CREATE_TABLE_SOUND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ALARM);
        db.execSQL(DROP_TABLE_SOUND);
        onCreate(db);
    }

    public long insertAlarmItem(AlarmItem alarmItem){
        alarm_info = getWritableDatabase();
        long ret = 0;

        if(TABLE_ALARM == alarm_info.findEditTable(TABLE_ALARM)) {
            Log.d("TABLE_SOUND NAME IS", alarm_info.findEditTable(TABLE_ALARM));

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
            values.put("ImageFilePath", alarmItem.getImageFilePath());
            values.put("Valid", alarmItem.isValid());

            ret = alarm_info.insert(TABLE_ALARM, null, values);

            values.clear();

            alarm_info.close();
            Log.d("ret VALUE IS", String.valueOf(ret));
            Log.d("insertTriggerTime",String.valueOf(alarmItem.getTriggerTime()));
            Log.d("insertTesxtTime",alarmItem.getTextTriggerTime());
        }
        return ret;
    }

    public AlarmItem getLastInsertAlarmItem(AlarmItem alarmItem){
        alarm_info = getReadableDatabase();
        try{
            Cursor cursor = alarm_info.rawQuery
                    ("SELECT * FROM "+TABLE_ALARM+" WHERE ID=(SELECT max(ID) FROM "+TABLE_ALARM+");",null);
            cursor.moveToFirst();
            alarmItem = createAlarmItem(cursor);
        }catch(Exception e){
            e.printStackTrace();
        }
        alarm_info.close();
        Log.d("alarmItem.getID()FROMDB",String.valueOf(alarmItem.getId()));
        Log.d("TriggerTimeLastItem",String.valueOf(alarmItem.getTriggerTime()));
        return alarmItem;
    }

    public AlarmItem getAlarmItemByID(int id){
        alarm_info = getReadableDatabase();
        AlarmItem alarmItem = null;
        try{
            Cursor cursor = alarm_info.rawQuery
                    ("SELECT * FROM "+TABLE_ALARM+" WHERE ID="+String.valueOf(id)+";",null);
            cursor.moveToFirst();
            alarmItem = createAlarmItem(cursor);
        }catch(Exception e){
            e.printStackTrace();
        }
        return alarmItem;
    }

    public List addAlarmItemFromDB(){
        alarm_info = getWritableDatabase();
        List mainAlarmList = new ArrayList<>();
        try {
            Cursor cursor = alarm_info.rawQuery("SELECT * FROM "+TABLE_ALARM+";",null);
            while (cursor.moveToNext()) {
                AlarmItem alarmItem = createAlarmItem(cursor);
                mainAlarmList.add(alarmItem);
                alarmItem = null;
            }
        }catch (NullPointerException npe){
            Log.d("***************", "error:DB is empty!!");
            mainAlarmList = null;
        }catch (Exception e){
            e.getMessage();
            e.getStackTrace();
        }
        alarm_info.close();
        return mainAlarmList;
    }

    private AlarmItem createAlarmItem(Cursor cursor){
        AlarmItem alarmItem = new AlarmItem(
                cursor.getInt(0),
                (long) cursor.getLong(1),
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
        return alarmItem;
    }

    public boolean ParseToBooleanForIntOfDB(int intOfCursor){
        boolean b = false;
        if(intOfCursor == 1){
            b = true;
        }
        return b;
    }

    public void deleteAlarmItem(int position, AlarmItem selectedItem){
        Log.d("position", String.valueOf(position));
        alarm_info = getWritableDatabase();
        alarm_info.beginTransaction();
        try {
            //delete recode
            alarm_info.delete(TABLE_ALARM, "ID = " + selectedItem.getId(), null);
            alarm_info.setTransactionSuccessful();
        }catch (Exception e){
            e.getMessage();
            e.getStackTrace();
        }finally {
            alarm_info.endTransaction();
            alarm_info.close();
        }
        Log.d("deleteAlarmItem:", "Complete !!");
    }

    public void updateAlarmItem(AlarmItem alarmItem){
        alarm_info = getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("Valid",alarmItem.isValid());
        alarm_info.update(TABLE_ALARM, values, "ID=" + String.valueOf(alarmItem.getId()), null);

        alarm_info.close();
    }

    public void writeSoundListToDB(ArrayList<Sound> soundList){
        alarm_info = getWritableDatabase();
        long ret = 0;

        alarm_info.execSQL("drop table " + TABLE_SOUND + ";");
        alarm_info.execSQL(CREATE_TABLE_SOUND);

        if(TABLE_SOUND == alarm_info.findEditTable(TABLE_SOUND)){
            Log.d("TABLE NAME IS", alarm_info.findEditTable(TABLE_SOUND));

            ContentValues values = new ContentValues();
            for(Sound sound : soundList) {
                if(sound.isValid() == false){
                    continue;
                }
                values.put("mediaId", sound.getId());
                values.put("name", sound.getName());
                values.put("artistName", sound.getArtistName());
                values.put("path", sound.getPath());
                ret = alarm_info.insert(TABLE_SOUND, null, values);
                Log.d("INSERT ret VALUE IS", String.valueOf(ret));
                values.clear();
            }
        }
        alarm_info.close();
    }

    public ArrayList setCheckedSoundStatus(ArrayList<Sound> soundList){
        alarm_info = getReadableDatabase();
        try{
            Cursor cursor = alarm_info.rawQuery("SELECT * FROM "+TABLE_SOUND+";",null);
            long recodeCount = DatabaseUtils.queryNumEntries(alarm_info, TABLE_SOUND);
            if(recodeCount == 0){
                Log.d("setCheckedSoundStatus", "recodeCount == 0");
                return soundList;
            }
            int i = 0;
            int count = 0;
            while(cursor.moveToNext()){
                Log.d("cursor values",String.valueOf(cursor.getLong(0)) + cursor.getString(1) + cursor.getString(2) + cursor.getString(3));
                while(count != recodeCount){
                    Sound sound = soundList.get(i);
                    if (cursor.getLong(0) == sound.getId()){
                        sound.setValid(true);
                        count++;
                        i++;
                        break;
                    }else{
                        i++;
                    }
                }
            }
        }catch(Exception e){
            Log.d("setCheckedSoundStatus", ":error:", new Throwable());
        }
        alarm_info.close();
        return soundList;
    }

    public String getMediaPath(){
        alarm_info = getReadableDatabase();
        Cursor cursor = alarm_info.rawQuery("SELECT * FROM " + TABLE_SOUND + " ORDER BY RANDOM() LIMIT 1;", null);
        cursor.moveToFirst();
        String path = cursor.getString(3);
        Log.d("path",path);
        alarm_info.close();

        return path;
    }

}

