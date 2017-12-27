package com.ly.luoyan.mylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class MyStickListView extends SlideListView2 {
    private boolean haveScrollbar = false;

    public MyStickListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (haveScrollbar == false) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
