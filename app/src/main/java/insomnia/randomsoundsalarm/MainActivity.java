package insomnia.randomsoundsalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AlarmItem> mainAlarmList;
    private ListView mainAlarmListView;
    private MySQLiteOpenHelper helper;
    private AlarmListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAlarmListView = (ListView)findViewById(R.id.MainAlarmList);
        mainAlarmListView.setOnItemLongClickListener(mainAlarmListViewOnItemLongClickListener);

        FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("insomnia.randomsoundsalarm", "insomnia.randomsoundsalarm.AlarmSettingActivity");
                startActivity(intent);
            }
        });

        FloatingActionButton soundButton = (FloatingActionButton)findViewById(R.id.soundButton);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("insomnia.randomsoundsalarm", "insomnia.randomsoundsalarm.ChooseSoundActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        helper = new MySQLiteOpenHelper(getApplicationContext());
        mainAlarmList = helper.addAlarmItemFromDB();
        if(mainAlarmList != null){
            adapter = new AlarmListAdapter(getApplicationContext());
            for(AlarmItem item : mainAlarmList){
                adapter.add(item);
            }
            mainAlarmListView.setAdapter(adapter);
        }else if(mainAlarmList == null){
            //Toast.makeText(this,"Hello!!What music do you like??",Toast.LENGTH_LONG).show();
        }
        helper.close();
        helper = null;
    }

    AdapterView.OnItemLongClickListener mainAlarmListViewOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Log:ClickListener", "toutched list item !!");
            Log.d("Log:ClickListener", String.valueOf(position));
            createDialog(parent,view,position);
            return false;
        }
    };

    private void createDialog(final AdapterView<?> parent, View view, final int position){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Notice");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setMessage("Delete this?");
        final ListView list = (ListView) parent;
        final AlarmItem selectedItem = (AlarmItem) list.getItemAtPosition(position);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlarmListAdapter adapter = (AlarmListAdapter) list.getAdapter();
                adapter.remove(selectedItem);
                adapter.notifyDataSetChanged();

                MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
                helper.deleteAlarmItem(position, selectedItem);
                helper.close();

                MyAlarmManager alarmManager = new MyAlarmManager(getApplicationContext());
                alarmManager.cancelAlarm(selectedItem);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //none
            }
        });
        dialog.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAlarmList = null;
        releaseListView(mainAlarmListView, adapter);
    }

    private void releaseListView(ListView mainAlarmListView, AlarmListAdapter adapter){
        if(mainAlarmList != null){
            mainAlarmListView.setAdapter(null);
            mainAlarmListView = null;
        }
        if(adapter != null){
            adapter.clear();
            adapter = null;
        }
    }
}
