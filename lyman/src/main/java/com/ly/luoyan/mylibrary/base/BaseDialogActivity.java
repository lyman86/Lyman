package com.ly.luoyan.mylibrary.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ly.luoyan.mylibrary.listener.OnCusDialogListener;
import com.ly.luoyan.mylibrary.utils.dialog.CusDialogShow;
import com.ly.luoyan.mylibrary.utils.dialog.CusDialogShowImpl;

/**
 * Created by luoyan on 2017/8/8.
 */

public abstract class BaseDialogActivity extends BaseActivity implements BaseDialogFunction,DialogInterface.OnClickListener{

    private CusDialogShow cusDialogShow;

    private OnCusDialogListener cusDialogListener;

    private static final String TAG = "BaseDialogActivity";

    public void setOnCusDialogListener(OnCusDialogListener listener){
        this.cusDialogListener = listener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        cusDialogShow = new CusDialogShowImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress(String content) {
        cusDialogShow.showDialogProgress(content);
    }

    @Override
    public void showProgressCus() {
        cusDialogShow.showDialogProgressCus();
    }

    @Override
    public void showAlertDialog(String title, String message) {
        cusDialogShow.showDialogAlert(title,message);
    }

    @Override
    public void showAlertDialog(String message) {
        cusDialogShow.showDialogAlert(message);
    }

    @Override
    public void dismissDialog() {
        cusDialogShow.dismissDialog();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (cusDialogListener!=null){
            cusDialogListener.onCustomDialog(dialogInterface,i);
        }
        cusDialogShow.dismissDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cusDialogShow.remove();
    }

    @Override
    public abstract void setContentView();
}
