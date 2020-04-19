package com.zsf.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.zsf.pluginlib.bean.PluginApk;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 创建apk对象所存在的成员变量
 */
public class PluginManager {

    public static PluginManager instance = new PluginManager();

    public static PluginManager getInstance(){
        return instance;
    }

    private PluginManager(){}

    private Context mContext;
    private PluginApk mPluginApk;

    public void init(Context context){
        mContext = context.getApplicationContext() ;
    }

    public PluginApk getPluginApk(){
        return mPluginApk;
    }

    //加载apk文件
    public void loadApk(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);
        if (packageInfo == null){
            return;
        }

        DexClassLoader dexClassLoader = createDexClassLoader(apkPath);
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResource(am);
        mPluginApk = new PluginApk(packageInfo,dexClassLoader,resources,am);
    }

    //创建DexClassLoader 用来加载apk解压之后的dex文件
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex",Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath,file.getAbsolutePath(),null,mContext.getClassLoader());
    }

    //创建 AssetManager
    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.invoke(am,apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //创建 Resource
    private Resources createResource(AssetManager am) {
        Resources resources = mContext.getResources();
        return new Resources(am,resources.getDisplayMetrics(),resources.getConfiguration());
    }

}
