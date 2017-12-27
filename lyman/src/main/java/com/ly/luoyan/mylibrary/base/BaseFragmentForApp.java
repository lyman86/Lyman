package com.ly.luoyan.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ly.luoyan.mylibrary.utils.DensityUtils;
import com.ly.luoyan.mylibrary.utils.StatusBarUtils;

/**
 * Created by luoyan on 2017/8/31.
 */

public abstract class BaseFragmentForApp extends BaseDialogFragment {

    protected int statusBarHeight = 0;
    protected int titleBarHeight = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarHeight = StatusBarUtils.getStatusBarHeight(getActivity());
        titleBarHeight = DensityUtils.dp2px(getActivity(),48);
    }
}
