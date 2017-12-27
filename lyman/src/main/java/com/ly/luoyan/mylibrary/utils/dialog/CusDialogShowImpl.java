package com.ly.luoyan.mylibrary.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.ly.luoyan.mylibrary.utils.MyWindow;
import com.ly.luoyan.mylibrary.utils.WindowUtil;
import com.ly.luoyan.mylibrary.widget.CustomDialog;

/**
 * Created by luoyan on 2017/3/4.
 */

public class CusDialogShowImpl implements CusDialogShow{
    //这里的context一定是Listener
    private Context mContext;

    private MyWindow myWindow;

    private CustomDialog.Builder builder;

    /**
     * alertDialog默认宽高
     */
    private int alertWidth;
    private int alertHeight;

    private int progressWidth;
    private int progressHeight;

    public CusDialogShowImpl(Context mContext) {
        this.mContext = mContext;
        myWindow = WindowUtil.getWindow(mContext);
        builder = new CustomDialog.Builder(mContext);
        init();
    }

    /**
     * 初始化dialog的宽高
     */
    private void init() {
        alertWidth = 2*myWindow.winth/3;
        alertHeight = 2*myWindow.height/9;
        progressWidth = 2*myWindow.winth/3;
        progressHeight = myWindow.height/7;
    }

    /**
     * 展示alertDialog（有标题）
     * @param title
     * @param msg
     */
    @Override
    public void showDialogAlert(String title,String msg){
        builder.setDialogWidthAndHeight(alertWidth,alertHeight);
        builder.setTitleAndMeg(title,msg);
        builder.setPositiveButton("确定", (DialogInterface.OnClickListener) mContext);
        builder.setNagetiveButton("取消", (DialogInterface.OnClickListener) mContext);
        builder.init();
        builder.setProgressbarState(builder.GONE);
        builder.create().show();
    }

    /**
     * 展示alertDialog（无标题）
     * @param msg
     */
    @Override
    public void showDialogAlert(String msg){
        builder.setDialogWidthAndHeight(alertWidth,alertHeight);
        builder.setMessage(msg);
        builder.setTitle("");//如果在同一个actiity里同时有两个alertDialo的话，就需要至空，否则会显示有标题的dialog
        builder.setPositiveButton("确定", (DialogInterface.OnClickListener) mContext);
        builder.setNagetiveButton("取消", (DialogInterface.OnClickListener) mContext);
        builder.init();
        builder.setProgressbarState(builder.GONE);
        builder.create().show();
    }

    /**
     * 展示progressDialog 使用系统默认的progressBar
     * @param content
     */
    @Override
    public void showDialogProgress(String content){
        builder.setMessage(content);
        builder.setDialogWidthAndHeight(progressWidth, progressHeight);
        builder.setTitle("");
        builder.setCustomProgress(false);
        builder.setPositiveButton("",null);
        builder.setNagetiveButton("",null);
        builder.init();
        builder.setProgressbarState(builder.VISIBLE);
        builder.create().show();
    }

    /**
     * 展示progressDialog 使用自定义的progress Image
     */
    @Override
    public void showDialogProgressCus(){
        builder.setDialogWidthAndHeight(progressWidth, progressHeight);
        builder.setTitleAndMeg("","");
        builder.setCustomProgress(true);
        builder.setPositiveButton("",null);
        builder.setNagetiveButton("",null);
        builder.init();
        builder.setProgressbarState(builder.VISIBLE);
        builder.create().show();
    }
    @Override
    public void dismissDialog(){
        builder.dismissDialog();
    }
    @Override
    public void remove(){
        builder = null;
    }



}
