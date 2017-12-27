package com.ly.luoyan.mylibrary.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.ly.luoyan.mylibrary.utils.DensityUtils;
import com.ly.luoyan.mylibrary.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Created by luoyan on 2017/8/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseStatusFunction,BaseViewFunction {

    private Toast mToast;

    private static final String TAG = "base2.BaseActivity";

    protected boolean userButterKnif = true;

    protected int statusBarHeight = 0;
    protected int titleBarHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        StatusBarUtils.setTranslucent(this);
        statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        setContentView();
        titleBarHeight = DensityUtils.dp2px(this,48);
        if (userButterKnif) {
            ButterKnife.bind(this);
        }
        initDatas();
        initListener();
    }

    @Override
    public abstract void setContentView();

    @Override
    public void initListener(){}

    @Override
    public void initDatas(){}

    @Override
    public void showToast(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(this, string, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER,0,0);
        } else {
            mToast.setText(string);
        }
        mToast.show();
    }

    @Override
    public void showToast(int source) {
        if (mToast == null) {
            mToast = Toast.makeText(this, getResources().getString(source), Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER,0,0);
        } else {
            mToast.setText(getResources().getString(source));
        }
        mToast.show();
    }

    @Override
    public void showLogE(String tag,String msg) {
        Log.e(TAG +" "+ tag,msg);
    }

    @Override
    public void showLogD(String tag,String msg) {
        Log.d(TAG +" "+ tag,msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userButterKnif) {ButterKnife.unbind(this);}
    }
}
