package com.zsf.pluginlib.bean;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

//插件apk的实体对象
public class PluginApk {

    public PackageInfo mPackageInfo;
    public DexClassLoader mClassLoader;
    public Resources mResource;
    public AssetManager mAssetManager;

    public PluginApk(PackageInfo mPackageInfo, DexClassLoader mClassLoader, Resources mResource, AssetManager mAssetManager) {
        this.mPackageInfo = mPackageInfo;
        this.mClassLoader = mClassLoader;
        this.mResource = mResource;
        this.mAssetManager = mAssetManager;
    }

    
}
