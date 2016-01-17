package insomnia.randomalarm;

import android.widget.ImageView;

/**
 * Created by OwnerMC on 16/01/04.
 */
public class AlarmItem {

    long mId;
    long mTriggerTime;
    String mLabel;
    boolean mSnooze;
    boolean mMon;
    boolean mTue;
    boolean mWed;
    boolean mThu;
    boolean mFri;
    boolean mSat;
    boolean mSun;
    ImageView mImage;


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getTriggerTime() {
        return mTriggerTime;
    }

    public void setTriggerTime(long triggerTime) {
        mTriggerTime = triggerTime;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public boolean isSnooze() {
        return mSnooze;
    }

    public void setSnooze(boolean snooze) {
        mSnooze = snooze;
    }

    public String getWhenRing(){
        String whenRing = "";
        if(mMon && mTue && mWed && mThu && mFri){
            whenRing = "Weekday";
            if(mSat && mSun){
                whenRing = "Everyday";
            }
        }else if(mSat && mSun){
            whenRing = "Weekend";
        }else{
            String[] simpleday = new String[6];
            if(mMon){simpleday[0] = "Mon";}
            if(mTue){simpleday[1] = "Tue";}
            if(mWed){simpleday[2] = "Wed";}
            if(mThu){simpleday[3] = "Thu";}
            if(mFri){simpleday[4] = "Fri";}
            if(mFri){simpleday[5] = "Sat";}
            if(mSun){simpleday[6] = "Sun";}

            for(String sd : simpleday){
                if(sd == null){
                   continue;
                }
                if(sd == "Sun"){
                    whenRing = "Sun";
                }
                whenRing = whenRing + sd + ",";
            }
        }

        return whenRing;
    }


    public boolean isMon() {
        return mMon;
    }

    public void setMon(boolean mon) {
        mMon = mon;
    }

    public boolean isTue() {
        return mTue;
    }

    public void setTue(boolean tue) {
        mTue = tue;
    }

    public boolean isWed() {
        return mWed;
    }

    public void setWed(boolean wed) {
        mWed = wed;
    }

    public boolean isThu() {
        return mThu;
    }

    public void setThu(boolean thu) {
        mThu = thu;
    }

    public boolean isFri() {
        return mFri;
    }

    public void setFri(boolean fri) {
        mFri = fri;
    }

    public boolean isSat() {
        return mSat;
    }

    public void setSat(boolean sat) {
        mSat = sat;
    }

    public boolean isSun() {
        return mSun;
    }

    public void setSun(boolean sun) {
        mSun = sun;
    }

    public ImageView getImage() {
        return mImage;
    }

    public void setImage(ImageView image) {
        mImage = image;
    }


}
