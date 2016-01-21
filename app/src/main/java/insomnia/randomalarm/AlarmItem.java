package insomnia.randomalarm;

/**
 * Created by OwnerMC on 16/01/04.
 */
public class AlarmItem {

    private long mTriggerTime;
    private String mLabel;
    private boolean mSnooze;
    private boolean mMon,mTue,mWed,mThu,mFri,mSat,mSun = false;
    private String mWhenRing = "";
    private String mImageFilePath;
    private boolean mValid = true;

    /**
     * Constructor
     */
    public AlarmItem(long triggerTime, String label, boolean snooze, boolean mon, boolean tue,
                     boolean wed, boolean thu, boolean fri, boolean sat, boolean sun, String imageFilePath) {
        mTriggerTime = triggerTime;
        mLabel = label;
        mSnooze = snooze;
        mMon = mon;
        mTue = tue;
        mWed = wed;
        mThu = thu;
        mFri = fri;
        mSat = sat;
        mSun = sun;
        mImageFilePath = imageFilePath;
        mWhenRing = setWhenRing();
    }
    //public  AlarmItem(){}


    public  String getWhenRing(){
        return mWhenRing;
    }

    public String setWhenRing(){
        if(mMon && mTue && mWed && mThu && mFri){
            mWhenRing = "Weekday";
            if(mSat && mSun){
                mWhenRing = "Everyday";
            }
        }else if(mSat && mSun && ((mMon && mTue && mWed && mThu && mFri)!= true)){
            mWhenRing = "Weekend";
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
                    mWhenRing = "Sun";
                    break;
                }
                mWhenRing = mWhenRing + sd + ",";
            }
        }

        return mWhenRing;
    }

    public long getTriggerTime() {
        return mTriggerTime;
    }

    public void setTriggerTime(long triggerTime) {
        mTriggerTime = triggerTime;
    }

    public String TriggerTimeForText(){
        String ttft = String.valueOf(mTriggerTime);//??ww
        //longをStringへ変換する処理
        return ttft;
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

    public String SnoozeForText(){
        String sft;
        if(isSnooze()){
            sft = "Snooze:On";
        }else{
            sft = "Snooze:Off";
        }
        return sft;
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

    public String getImageFilePth() {
        return mImageFilePath;
    }

    public void setImageFilePth(String imageFilePath) {
        mImageFilePath = imageFilePath;
    }

    public boolean isValid() {
        return mValid;
    }

    public void setValid(boolean valid) {
        mValid = valid;
    }

}
