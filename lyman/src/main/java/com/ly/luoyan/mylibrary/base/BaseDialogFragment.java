package com.ly.luoyan.mylibrary.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ly.luoyan.mylibrary.listener.OnCusDialogListener;
import com.ly.luoyan.mylibrary.utils.dialog.CusDialogShow;
import com.ly.luoyan.mylibrary.utils.dialog.CusDialogShowImpl;

/**
 * Created by luoyan on 2017/8/31.
 */

public class BaseDialogFragment extends BaseFragment implements BaseDialogFunction,DialogInterface.OnClickListener{

    private CusDialogShow cusDialogShow;

    private OnCusDialogListener cusDialogListener;

    private static final String TAG = "BaseDialogFragment";

    public void setOnCusDialogListener(OnCusDialogListener listener){
        this.cusDialogListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (cusDialogListener!=null){
            cusDialogListener.onCustomDialog(dialog,which);
        }
        cusDialogShow.dismissDialog();
    }

    @Override
    public void showProgress(String string) {
        cusDialogShow.showDialogProgress(string);
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
    public void onDestroyView() {
        super.onDestroyView();
        cusDialogShow.remove();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cusDialogShow = new CusDialogShowImpl(getContext());
    }
}
