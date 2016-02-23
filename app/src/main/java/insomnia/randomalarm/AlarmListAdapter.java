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

    public AlarmListAdapter(Context context) {
        super(context,0);
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
        convertView = layoutInflater.inflate(R.layout.alarm_item_row,parent,false);
        }

        AlarmItem alarmItem = getItem(position);

        TextView triggerTime = (TextView) convertView.findViewById(R.id.itemTriggerTime);
        triggerTime.setText(alarmItem.getTextTriggerTime());

        TextView label = (TextView) convertView.findViewById(R.id.itemLabel);
        label.setText(alarmItem.getLabel());

        TextView whenRing = (TextView) convertView.findViewById(R.id.itemWhenring);
        whenRing.setText(alarmItem.getWhenRing());

        TextView snooze = (TextView) convertView.findViewById(R.id.itemSnooze);
        snooze.setText(alarmItem.SnoozeForText());

        Switch valid = (Switch) convertView.findViewById(R.id.itemSwitch);
        valid.setChecked(alarmItem.isValid());

        return convertView;
    }
}
