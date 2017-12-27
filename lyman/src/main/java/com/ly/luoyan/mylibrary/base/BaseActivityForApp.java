package com.ly.luoyan.mylibrary.base;

import android.view.View;

import com.ly.luoyan.mylibrary.widget.CustomSelectItem;

/**
 * Created by luoyan on 2017/8/8.
 */

public abstract class BaseActivityForApp extends BasePicActivity implements CustomSelectItem.OnBarViewClickListener{

    @Override
    public abstract void setContentView();

    @Override
    public void onBarViewClick(View v, int whitch) {
        switch (whitch){
            case CustomSelectItem.LEFT_VIEW:
                finish();
                break;
        }
    }
}
