package insomnia.randomalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AlarmItem> mainAlarmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ListView MainAlarmListView = (ListView)findViewById(R.id.MainAlarmList);

        AlarmListDao helper = new AlarmListDao(getApplicationContext());
        helper.addAlarmItemFromDB(mainAlarmList);
        if(mainAlarmList != null){
            AlarmListAdapter adapter = new AlarmListAdapter(getApplicationContext());
            for(AlarmItem item : mainAlarmList){
                adapter.add(item);
            }
            MainAlarmListView.setAdapter(adapter);
        }else if(mainAlarmList == null){
            Toast.makeText(this,"Hello!!",Toast.LENGTH_LONG).show();
        }
        helper.close();
    }


    public void addButtonOnClick(View view){
        Intent intent = new Intent();
        intent.setClassName("insomnia.randomalarm", "insomnia.randomalarm.AlarmSettingActivity");
        startActivity(intent);
    }

    public void soundButtonOnClick(View view){

    }
}
