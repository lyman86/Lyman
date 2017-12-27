package com.ly.luoyan.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ly.luoyan.mylibrary.R;
import com.ly.luoyan.mylibrary.widget.CustomSelectItem;

import butterknife.ButterKnife;

/**
 * Created by luoyan on 2017/8/31.
 */

public abstract class BaseFragment extends Fragment implements BaseStatusFragmentFunction,BaseViewFunction {

    private Toast mToast;

    private static final String TAG = "base2.BaseFragment";

    private View rootView;

    protected boolean userButterKnif = true;

    protected CustomSelectItem titleBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.from(getContext()).inflate(R.layout.fragment_default,null);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (userButterKnif){ButterKnife.bind(this,view);}
        titleBar = (CustomSelectItem) view.findViewById(R.id.cus_title_bar);
        initDatas();
        initListener();
    }

    @Override
    public void showToast(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), string, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER,0,0);
        } else {
            mToast.setText(string);
        }
        mToast.show();
    }

    @Override
    public void showToast(int source) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), getResources().getString(source), Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER,0,0);
        } else {
            mToast.setText(getResources().getString(source));
        }
        mToast.show();
    }

    @Override
    public void showLogE(String tag, String msg) {
        Log.e(TAG +" "+ tag,msg);
    }

    @Override
    public void showLogD(String tag, String msg) {
        Log.d(TAG +" "+ tag,msg);
    }

    @Override
    public void setContentView(int layoutId){
        rootView = LayoutInflater.from(getContext()).inflate(layoutId,null);
    }

    @Override
    public void initListener() {}

    @Override
    public void initDatas() {}

    public View getContentView(){
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userButterKnif){ButterKnife.unbind(rootView);}
    }
}
