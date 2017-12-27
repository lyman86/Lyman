package com.ly.luoyan.mylibrary.base;

/**
 * Created by luoyan on 2017/8/8.
 */

public interface BaseViewFunction {

    void showToast(String str);

    void showToast(int source);

    void showLogE(String tag,String msg);

    void showLogD(String tag,String msg);
}
