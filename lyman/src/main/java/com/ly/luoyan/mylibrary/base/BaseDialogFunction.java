package com.ly.luoyan.mylibrary.base;

/**
 * Created by luoyan on 2017/8/8.
 */

public interface BaseDialogFunction {
    void showProgress(String string);

    void showProgressCus();

    void showAlertDialog(String title,String message);

    void showAlertDialog(String message);

    void dismissDialog();
}
