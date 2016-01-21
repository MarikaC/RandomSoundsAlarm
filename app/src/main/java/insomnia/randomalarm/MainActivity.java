package insomnia.randomalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView MainAlarmListView = (ListView)findViewById(R.id.MainAlarmList);
        //ArrayList<AlarmItem> alarmlist = new ArrayList<>();
        AlarmListAdapter adapter = new AlarmListAdapter(getApplicationContext());

        /* TODO: 16/01/21
        SQlite call to data
        set Alarm data
        */

        //ex)
        AlarmItem mainListItem = new AlarmItem(12345678,"Good Morning!!Wake Up!!",true,
                true,true,true,true,true,false,false,"/imagefile/path");
        //*ArrayAdapterなのでaddをforで回して既存のAlarmItemを全てset

        // /adapter.mainAlarmList.add(mainListItem);
        MainAlarmListView.setAdapter(adapter);
    }

    public void addButtonOnClick(View view){
        Intent intent = new Intent();
        intent.setClassName("insomnia.randomalarm", "insomnia.randomalarm.AlarmSettingActivity");
        startActivity(intent);
    }

    public void soundButtonOnClick(View view){

    }
}
