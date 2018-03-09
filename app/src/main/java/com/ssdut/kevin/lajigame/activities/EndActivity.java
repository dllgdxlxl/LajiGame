package com.ssdut.kevin.lajigame.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssdut.kevin.lajigame.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kevin on 2018/3/1.
 */

public class EndActivity extends BasicActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.end_home:
                finish();
                break;
            case R.id.end_share:
                share();
                break;
            case R.id.end_start_game:
                startActivity(new Intent(this,GameActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    ImageView home;
    ImageView share;
    TextView score;
    ImageView try_again;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        home = (ImageView)findViewById(R.id.end_home);
        home.setOnClickListener(this);
        share = (ImageView)findViewById(R.id.end_share);
        share.setOnClickListener(this);
        try_again = (ImageView)findViewById(R.id.end_start_game);
        try_again.setOnClickListener(this);
        score = (TextView)findViewById(R.id.score);
        Typeface mtypeface= Typeface.createFromAsset(getAssets(), "orange.TTF");
        score.setTypeface(mtypeface);
        score.setText(getIntent().getStringExtra("score"));
    }
    public Bitmap shotScreen(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    public void saveBitmapToFile(String fileName, Bitmap bitmap) {
        Log.e("file",fileName);
        if (TextUtils.isEmpty(fileName) || bitmap == null) return;

        try {
            File f = new File(fileName);
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            Log.i("ScreenShotUtil", "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AlbumScan(String fileName) {
        MediaScannerConnection.scanFile(this.getApplicationContext(), new String[]{fileName}, new String[]{"image/jpeg"}, null);
    }
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
        }
    }
    private void share(){
        checkPermission();
        Bitmap bitmap = shotScreen(this);
        Log.e("shot1","bitmap");
        String filename = Environment.getExternalStorageDirectory()+"/share.jpg";
        saveBitmapToFile(filename,bitmap);
        Log.e("shot2","save");
        AlbumScan(filename);
        Log.e("shot3","alert");
        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();

    }
}
