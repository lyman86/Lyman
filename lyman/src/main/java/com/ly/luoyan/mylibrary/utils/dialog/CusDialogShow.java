package com.ly.luoyan.mylibrary.utils.dialog;

/**
 * Created by luoyan on 2017/3/26.
 */

public interface CusDialogShow {
    /**
     * 展示alertDialog（有标题）
     * @param title
     * @param msg
     */
    void showDialogAlert(String title,String msg);
    /**
     * 展示alertDialog（无标题）
     * @param msg
     */
    void showDialogAlert(String msg);
    /**
     * 展示progressDialog 使用系统默认的progressBar
     * @param content
     */
    void showDialogProgress(String content);
    /**
     * 展示progressDialog 使用自定义的progress Image
     */
    void showDialogProgressCus();

    void dismissDialog();

    void remove();

}
