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

/**
 *  每一个Java方法在Art虚拟机中都对应一个ArtMethod，ArtMethod记录了该方法的所有信息，包括所属类、访问权限、代码执行地址等。
*  通过env->FromReflectedMethod,可以由Method对象得到这个方法对应的ArtMethod的真正起始地址，然后强转为ArtMethod指针，
*   通过指针的操作对其成员属性进行修改替换。
*  原理总结：  当我们把旧方法（ArtMethod）的所有成员字段都替换为新的方法（ArtMethod）的成员字段后，
*   执行时所有的数据就可以保持和新方法的数据一致。这样在所有执行旧方法的地方，会获取新方法的执行入口、
*   索引类型、方法索引号、以及所属dex信息，然后向调用旧方法一样，执行新方法的逻辑。
 *   兼容性问题的结局方案：https://blog.csdn.net/gnmmdk/article/details/97959108
 */
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
