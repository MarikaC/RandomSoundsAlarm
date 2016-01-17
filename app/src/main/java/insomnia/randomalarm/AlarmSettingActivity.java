package insomnia.randomalarm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;


public class AlarmSettingActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 0;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);
        imgView = (ImageView)findViewById(R.id.sPhotoImageView);//設定済みの場合は初期値を入れる
    }

    public void PhotoButtonOnClick(View view) {
        // ギャラリー呼び出し
        Intent intent = new Intent();
        //Intent#setTypeメソッドで, 画像全般("image/*")を指定する.
        //jpegに限定する場合は, "image/jpeg"と指定.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                //photodata取得後、bitmapへ変換
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // 選択した画像を表示
                imgView.setImageBitmap(img);
                //filepath取得部分
                String picturePath = getFilepath(data);
                Toast.makeText(this,picturePath,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getFilepath(Intent data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = { Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    public void CreateButtonOnClick(View view){
       // Main main = new Main();
//        Intent intent = new Intent();
//        intent.setClassName("insomnia.randomalarm", "insomnia.randomalarm.Main");
//        startActivity(intent);
        finish();
    }

}
