package insomnia.randomalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OwnerMC on 16/01/04.
 */
public class AlarmListAdapter extends BaseAdapter{

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<AlarmItem> alarmList;

    public AlarmListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAlarmList(ArrayList<AlarmItem> alarmList) {
        this.alarmList = alarmList;
    }

    @Override
    public int getCount(){
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return alarmList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
        convertView = layoutInflater.inflate(R.layout.alarm_item_row,parent,false);
        }

        ((TextView)convertView.findViewById(R.id.itemLabel)).setText(alarmList.get(position).getLabel());
        ((TextView)convertView.findViewById(R.id.itemWhenring)).setText(alarmList.get(position).getWhenRing());
        //((Switch)convertView.findViewById(R.id.itemSwitch)).setText(alarmItem.get(position).);

        return convertView;
    }
}
