package com.ly.luoyan.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.kifile.materialwidget.MaterialBackgroundDetector;
import com.ly.luoyan.mylibrary.R;

public class MaterialLinearLayout extends LinearLayout implements MaterialBackgroundDetector.Callback{

    private MaterialBackgroundDetector mDetector;

    public MaterialLinearLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public MaterialLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MaterialLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, com.kifile.materialwidget.R.styleable.MaterialTextView, defStyle, 0);
        int color = getContext().getResources().getColor(R.color.lyman_bg_gray_206);
        a.recycle();
        mDetector = new MaterialBackgroundDetector(getContext(), this, this, color);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDetector.onSizeChanged(w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean superResult = super.onTouchEvent(event);
        return mDetector.onTouchEvent(event, superResult);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        mDetector.draw(canvas);
    }

    @Override
    public boolean performClick() {
        return mDetector.handlePerformClick();
    }

    @Override
    public boolean performLongClick() {
        return mDetector.handlePerformLongClick();
    }

    @Override
    public void performClickAfterAnimation() {
        super.performClick();
    }

    @Override
    public void performLongClickAfterAnimation() {
        super.performLongClick();
    }
} 