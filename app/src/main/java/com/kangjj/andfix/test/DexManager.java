package com.kangjj.andfix.test;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class DexManager {
    public static final String TAG = DexManager.class.getSimpleName();

    private static final DexManager ourInstance = new DexManager();

    public static DexManager getInstance(){
        return ourInstance;
    }

    private Context mContext;
    public void setContext(Context context){
        this.mContext = context;
    }


    public void loadDex(File file){
        try {
            if(file!=null && file.exists()){
                DexFile dexFile = DexFile.loadDex(file.getAbsolutePath(),
                        new File(mContext.getCacheDir(),"opt").getAbsolutePath(),
                        Context.MODE_PRIVATE);
                Enumeration<String> entries = dexFile.entries();
                while (entries.hasMoreElements()) {
                    String className = entries.nextElement();
//                Class.forName(className);这种方式只能获取安装app中的class
                    Class fixClazz = dexFile.loadClass(className, mContext.getClassLoader());
                    if(fixClazz!=null){
                        fixClass(fixClazz);
                    }
                }
            }else{
                Log.e(TAG,"文件不存在。");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixClass(Class fixClazz) {
        Method[] methods = fixClazz.getDeclaredMethods();
        MethodReplace methodReplace;
        String className;
        String methodName;
        Class<?> bugClass;
        Method bugMethod;
        for (Method fixMethod : methods) {
            methodReplace = fixMethod.getAnnotation(MethodReplace.class);
            if(methodReplace == null){
                continue;
            }
            Log.e(TAG,"找到修复好的方法："+fixMethod.getDeclaringClass()+"@"+fixMethod.getName());
            className = methodReplace.className();
            methodName = methodReplace.methodName();
            if(!TextUtils.isEmpty(className) && !TextUtils.isEmpty(methodName)){
                try {
                    bugClass = Class.forName(className);
                    bugMethod = bugClass.getDeclaredMethod(methodName,fixMethod.getParameterTypes());
                    replace(bugMethod,fixMethod);
                    Log.e(TAG, "修复完成！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Log.e(TAG, "/修复好的方法未设置自定义注解属性");
            }
            
        }
    }

    private native void replace(Method bugMethod,Method fixMethod);

    {
        System.loadLibrary("native-lib");
    }
}
