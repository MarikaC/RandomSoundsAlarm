package insomnia.randomsoundsalarm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class BackImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_image);
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("ALARMITEM_IMAGEFILEPATH");
        Uri imageUri = Uri.parse(filepath);

        ImageView wakeBackground = (ImageView)findViewById(R.id.wake_background);
        wakeBackground.setImageURI(imageUri);

        Intent wake = new Intent(this,WakeUpActivity.class);
        wake.putExtra("ALARMITEM_LABEL", intent.getStringExtra(intent.getStringExtra("ALARMITEM_LABEL")));
        startActivity(wake);
    }
}
