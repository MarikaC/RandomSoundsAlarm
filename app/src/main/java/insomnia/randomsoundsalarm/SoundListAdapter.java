package insomnia.randomsoundsalarm;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by OwnerMC on 16/02/28.
 */
public class SoundListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    private ArrayList<Sound> soundList;

    public SoundListAdapter(Context context) {
        super();
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSoundList(ArrayList<Sound> soundList) {
        this.soundList = soundList;
    }

    public ArrayList<Sound> getSoundList() {
        return soundList;
    }

    @Override
    public int getCount() {
        return soundList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Sound sound = soundList.get(position);
        Log.d("getViewSoundListAdapter", "view" + String.valueOf(position));

        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.choose_sound_item,parent,false);

            TextView albumName = (TextView)convertView.findViewById(R.id.albumName);
            TextView soundName = (TextView)convertView.findViewById(R.id.soundName);
            CheckBox soundIsSelected = (CheckBox)convertView.findViewById(R.id.soundCheckBox);

            holder = new ViewHolder();
            holder.albumName = albumName;
            holder.soundName = soundName;
            holder.soundIsSelected = soundIsSelected;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.albumName.setText(sound.getAlbumName());
        holder.soundName.setText(sound.getName());
        holder.soundIsSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sound.setValid(isChecked);
                Log.d("CheckedChanged position", String.valueOf(position));
            }
        });
        if(sound.isValid()) {
            holder.soundIsSelected.setChecked(true);
        }else {
            holder.soundIsSelected.setChecked(false);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView albumName;
        TextView soundName;
        CheckBox soundIsSelected;
    }

}
