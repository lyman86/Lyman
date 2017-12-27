package com.ly.luoyan.mylibrary.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.ly.luoyan.mylibrary.R;
import com.ly.luoyan.mylibrary.utils.MyWindow;
import com.ly.luoyan.mylibrary.utils.StatusBarUtils;
import com.ly.luoyan.mylibrary.utils.WindowUtil;


public abstract class BasePopUpWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Context context;
    // PopupWindow的宽度
    public int mWidth;
    // PopupWindow的高度
    public int mHeight;
    // 获取PopupWindow的view
    public View mConvertView;
    //从右向左平移，高一定，宽待定，停留在屏幕的右侧
    public static final int TANS_HOR_FROM_RIGHT = 0;
    //从左向右平移，高一定，宽待定，停留在屏幕的左侧
    public static final int TANS_HOR_FROM_LEFT = 1;
    //从下向上平移，宽一定，高待定，停留在屏幕的下侧
    public static final int TANS_VER_FROM_BOTTOM = 2;
    //从上向下平移，宽一定，高待定，停留在屏幕的上侧
    public static final int TANS_VER_FROM_TOP = 3;
    /**
     * 自定义弹出，动画定制，宽高待定
     */
    public static final int CUSTOM = 4;

    private int mode = TANS_HOR_FROM_RIGHT;
    private MyWindow window;
    private float proPortion = 0.4f;
    /**
     * 是否已关灯
     */
    private boolean lightOff = false;
    private boolean outSideTouch = true;


    /**
     * 设置布局和一些初始化操作,若proPortion为0，则width和height不能为0，反之亦然。
     *
     * @param context
     * @param source
     */
    public void setLayout(final Context context, int source, int mode) {
        this.context = context;
        this.mode = mode;
        window = WindowUtil.getWindow(context);
        mConvertView = LayoutInflater.from(context).inflate(source, null);
        setContentView(mConvertView);
        baseConfig();
        int animationStyle = choiceMode();
        setWidth(mWidth);
        if (mode == TANS_HOR_FROM_RIGHT || mode == TANS_HOR_FROM_LEFT) {
            setHeight(mHeight - StatusBarUtils.getStatusBarHeight(context));
        } else {
            setHeight(mHeight);
        }
        initId();
        initEvent();
        setAnimationStyle(animationStyle);
        setOnDismissListener(this);
    }

    protected int choiceMode() {
        int animationStyle = 0;
        switch (mode) {
            case TANS_HOR_FROM_RIGHT:
                mHeight = window.height;
                mWidth = mWidth == 0 ? (int) (window.winth * proPortion) : mWidth;
                animationStyle = R.style.mypopwindow_anim_style_hor_from_right;
                break;
            case TANS_HOR_FROM_LEFT:
                mHeight = window.height;
                mWidth = mWidth == 0 ? (int) (window.winth * proPortion) : mWidth;
                animationStyle = R.style.mypopwindow_anim_style_hor_from_left;
                break;
            case TANS_VER_FROM_BOTTOM:
                mWidth = window.winth;
                mHeight = mHeight == 0 ? (int) (window.height * proPortion) : mHeight;
                animationStyle = R.style.mypopwindow_anim_style_ver_from_bottom;
                break;
            case TANS_VER_FROM_TOP:
                mWidth = window.winth;
                mHeight = mHeight == 0 ? (int) (window.height * proPortion) : mHeight;
                animationStyle = R.style.mypopwindow_anim_style_ver_from_top;
                break;
            case CUSTOM:
                mWidth = mWidth == 0 ? (int) (window.winth * proPortion) : mWidth;
                mHeight = mHeight == 0 ? (int) (window.height * proPortion) : mHeight;
                animationStyle = R.style.mypopwindow_anim_style_cus;
                break;
        }
        return animationStyle;
    }

    protected void baseConfig() {
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new BitmapDrawable());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                if ((event.getAction() == MotionEvent.ACTION_DOWN)
                        && ((x < 0) || (x >= mWidth) || (y < 0) || (y >= mHeight))) {
                    // donothing
                    // 消费事件
                    if (!outSideTouch) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    if (!outSideTouch) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public abstract void initId();

    public abstract void initEvent();

    /**
     * 设置比例系数（宽占屏幕宽比 or 高占屏幕高比）
     *
     * @param proPortion
     */
    public void setproPortion(float proPortion) {
        this.proPortion = proPortion;
    }

    /**
     * 设置当前window的宽高
     *
     * @param mWidth
     * @param mHeight
     */
    public void setWidthAndHeight(int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    /**
     * 设置屏幕弹出动画
     *
     * @param style
     */
    public void setAnimation(int style) {
        setAnimationStyle(style);
    }

    /**
     * 灯亮
     *
     * @param activity
     */
    public void lightOn(Activity activity) {
        lightOff = false;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);

    }

    /**
     * 灯灭
     *
     * @param activity
     */
    public void lightOff(Activity activity) {
        lightOff = true;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = .3f;
        activity.getWindow().setAttributes(lp);

    }

    public void canNotTouchOutSide() {
        outSideTouch = false;
    }

    public void showPop(View rootView) {
        switch (mode) {
            case TANS_HOR_FROM_RIGHT:
                showAtLocation(rootView, Gravity.RIGHT, 0, 0);
                break;
            case TANS_HOR_FROM_LEFT:
                showAtLocation(rootView, Gravity.LEFT, 0, 0);
                break;
            case TANS_VER_FROM_BOTTOM:
                showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case TANS_VER_FROM_TOP:
                showAtLocation(rootView, Gravity.TOP, 0, 0);
                break;
            case CUSTOM:
                showAtLocation(rootView, Gravity.CENTER, 0, 0);
                break;
        }

    }

    @Override
    public void onDismiss() {
        if (lightOff) lightOn((Activity) context);
    }
}
