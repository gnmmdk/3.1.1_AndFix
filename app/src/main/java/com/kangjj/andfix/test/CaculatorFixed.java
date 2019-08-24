package com.kangjj.andfix.test;

import android.content.Context;
import android.widget.Toast;

public class CaculatorFixed {
    @MethodReplace(className = "com.kangjj.andfix.test.Caculator",methodName = "caculator")
    public void caculator(Context context){
        int a = 666;
        int b = 1;
        Toast.makeText(context, "计算a/b = " + a / b, Toast.LENGTH_SHORT).show();
    }
    public void a(){
    }
    public void b(){
    }
}
