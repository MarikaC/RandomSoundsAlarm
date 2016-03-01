package insomnia.randomalarm;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OwnerMC on 16/02/28.
 */
public class SoundListAdapter extends SectionedBaseAdapter{
    private List soundList;
    private Sound mSound;
    LayoutInflater inflator;


    public List getSoundList() {
        return soundList;
    }

    public void setSoundList(List soundList) {
        this.soundList = soundList;
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 3;
    }

    @Override
    public int getCountForSection(int section) {
        return 5;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        mSound = (Sound)getItem(section,position);
        LinearLayout layout = null;
        if (convertView == null) {
            inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.choose_sound_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.albumName)).setText("Section " + section + " Item " + position);

//        ((TextView)layout.findViewById(R.id.albumName)).setText(mSound.getAlbumName());
//        ((TextView)layout.findViewById(R.id.soundName)).setText(mSound.getName());
//        ((CheckBox)layout.findViewById(R.id.soundCheckBox)).setChecked(mSound.get);
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        mSound = (Sound) getItem(section);
        LinearLayout layout = null;
        if (convertView == null) {
            inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.choose_sound_header, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.artistName)).setText("Header for section " + section);

//        ((TextView)layout.findViewById(R.id.artistName)).setText(mSound.getArtistName());
//        ((CheckBox)layout.findViewById(R.id.artistCheckBox)).setChecked(sound.get);
        return layout;
    }

}
