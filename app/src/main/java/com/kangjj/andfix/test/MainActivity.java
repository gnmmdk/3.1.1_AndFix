package com.kangjj.andfix.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    //请求状态码
    private static final String  TAG = MainActivity.class.getSimpleName();
    private static int REQUEST_PERMISSION_CODE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Caculator caculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        caculator = new Caculator();
        findViewById(R.id.caculator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Caculator().caculator(MainActivity.this);
                caculator.caculator(MainActivity.this);
            }
        });
        findViewById(R.id.fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDex();
                //判断当前版本
              /*  if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);//requesrCode
                    } else {
                        Log.d(TAG, "已有权限");
                loadDex();
                  }
                } else {
                    Log.d(TAG, "小于 6.0");
                }*/


            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == 0) {
                    Log.d(TAG, "获取权限成功");
                    loadDex();
                } else {
                    Log.d(TAG, "获取权限失败");
                }
            }
        }
    }

    private void loadDex() {
        DexManager.getInstance().setContext(MainActivity.this);
                DexManager.getInstance().loadDex(new File(Environment.getExternalStorageDirectory(),"out.dex"));
//        DexManager.getInstance().loadDex(new File("/sdcard/out.dex"));
    }

}
