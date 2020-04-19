package com.zsf.pluginlib;

import android.app.Activity;
import android.os.Bundle;

public interface IPlugin {

    int FROM_INTERNAL = 0; //主apk加载
    int FROM_EXTERNAL = 1; //系统加载


    void attach(Activity activity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();
}
