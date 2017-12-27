package com.ly.luoyan.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.ly.luoyan.mylibrary.R;
import com.ly.luoyan.mylibrary.utils.DensityUtils;


public class RoundTextView extends TextView {


    private Context mContext;
    private int mBgColor = 0;
    private int mCornerSize = 0;
    private int padding = 0;
    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        mContext = context;
        padding = DensityUtils.dp2px(context,1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundRounded(canvas, this.getMeasuredWidth(), this.getMeasuredHeight(), this);
        super.onDraw(canvas);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.round_textview);
        mBgColor = ta.getColor(R.styleable.round_textview_round_tv_color, Color.TRANSPARENT);
        mCornerSize = (int) ta.getDimension(R.styleable.round_textview_round_tv_corner_size, 8);

        ta.recycle();
    }

    public void setTextBg(int color){
        mBgColor = color;
        postInvalidate();
    }
    private  void setBackgroundRounded(Canvas c, int w, int h, View v) {
        if (w <= 0 || h <= 0) {
            return;
        }

        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(mBgColor);

        RectF rec = new RectF(0, 0+padding, w, h-padding);
        c.drawRoundRect(rec, mCornerSize, mCornerSize, paint);
    }
}