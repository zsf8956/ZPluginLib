package com.zsf.pluginlib.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zsf.pluginlib.IPlugin;

public class BasePluginActivity extends Activity implements IPlugin {

    private int mFrom = FROM_INTERNAL;
    private Activity mProxyActivity;

    @Override
    public void attach(Activity activity) {
        mProxyActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mFrom = savedInstanceState.getInt("FROM");
        }

        if (mFrom == FROM_INTERNAL){
            super.onCreate(savedInstanceState);
            mProxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutId) {
        if (mFrom == FROM_INTERNAL){
            super.setContentView(layoutId);
        }else{
            mProxyActivity.setContentView(layoutId);
        }
    }

    @Override
    public void setContentView(View view) {
        if (mFrom == FROM_INTERNAL){
            super.setContentView(view);
        }else{
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL){
            super.onRestart();
        }
    }
}
