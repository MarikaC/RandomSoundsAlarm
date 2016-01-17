package insomnia.randomalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);

        ArrayList<AlarmItem> alarmlist = new ArrayList<>();
        AlarmListAdapter adapter = new AlarmListAdapter(Main.this);
        adapter.setAlarmList(alarmlist);
        listView.setAdapter(adapter);
    }

    public void addButtonOnClick(View view){
        Intent intent = new Intent();
        intent.setClassName("insomnia.randomalarm", "insomnia.randomalarm.AlarmSettingActivity");
        startActivity(intent);
    }

    public void soundButtonOnClick(View view){

    }
}
