package com.zsf.pluginlib.proxy;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zsf.pluginlib.IPlugin;
import com.zsf.pluginlib.PluginManager;
import com.zsf.pluginlib.bean.PluginApk;

public class ProxyPluginActivity extends Activity {

    // 指定跳转的activity
    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIplugin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();

        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null){
            Log.e("ERROR","Loading your apk file first please");
        }

        try {
            Class<?> clazz = mPluginApk.mClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            if (object instanceof IPlugin){
                mIplugin = (IPlugin) object;
                mIplugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                mIplugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResource : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mClassLoader : super.getClassLoader();
    }
}
