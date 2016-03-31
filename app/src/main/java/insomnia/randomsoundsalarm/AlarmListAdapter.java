package insomnia.randomsoundsalarm;

import android.content.Context;
import android.util.Log;
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
    Switch valid;
    Context context;

    public AlarmListAdapter(Context context) {
        super(context,0);
        this.context = context;
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

        final AlarmItem alarmItem = getItem(position);

        TextView triggerTime = (TextView) convertView.findViewById(R.id.itemTriggerTime);
        triggerTime.setText(alarmItem.getTextTriggerTime());

        TextView label = (TextView) convertView.findViewById(R.id.itemLabel);
        label.setText(alarmItem.getLabel());

        TextView whenRing = (TextView) convertView.findViewById(R.id.itemWhenring);
        whenRing.setText(alarmItem.getWhenRing());

        TextView snooze = (TextView) convertView.findViewById(R.id.itemSnooze);
        snooze.setText(alarmItem.SnoozeForText());

        valid = (Switch) convertView.findViewById(R.id.itemSwitch);
        valid.setChecked(alarmItem.isValid());
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "SwitchClicked");
                alarmItem.setValid(valid.isChecked());

                MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
                helper.updateAlarmItem(alarmItem);

                MyAlarmManager alarmManager = new MyAlarmManager(context);
                if (alarmItem.isValid() == true) {
                    alarmManager.addAlarm(alarmItem);
                } else {
                    Log.d("alarmManager","want to cancelAlarm"+alarmItem.getId());
                    alarmManager.cancelAlarm(alarmItem);
                }
            }
        });

        return convertView;
    }
}
