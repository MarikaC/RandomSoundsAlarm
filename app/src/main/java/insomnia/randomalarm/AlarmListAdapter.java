package insomnia.randomalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by OwnerMC on 16/01/04.
 */
public class AlarmListAdapter extends ArrayAdapter<AlarmItem> {

    LayoutInflater layoutInflater = null;
    AlarmItem mAlarmItem;

    public AlarmListAdapter(Context context) {
        super(context,0);
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    //ListViewに表示したい数
//    @Override
//    public int getCount() {
//        return 10;
//    }

//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

//    //特に使わないので0
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
        convertView = layoutInflater.inflate(R.layout.alarm_item_row,parent,false);
        }

        AlarmItem alarmItem = getItem(position);

        TextView TriggerTime = (TextView) convertView.findViewById(R.id.itemTriggerTime);
        TriggerTime.setText(alarmItem.getTextTriggerTime());

        TextView Label = (TextView) convertView.findViewById(R.id.itemLabel);
        Label.setText(alarmItem.getLabel());

        TextView WhenRing = (TextView) convertView.findViewById(R.id.itemWhenring);
        WhenRing.setText(alarmItem.getWhenRing());

        TextView Snooze = (TextView) convertView.findViewById(R.id.itemSnooze);
        Snooze.setText(alarmItem.SnoozeForText());

        Switch Valid = (Switch) convertView.findViewById(R.id.itemSwitch);
        Valid.setChecked(alarmItem.isValid());

        return convertView;
    }
}
