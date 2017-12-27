package com.ly.luoyan.mylibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class WindowUtil {
	
	public static MyWindow getWindow(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();
		MyWindow window = new MyWindow();
		window.winth = dm.widthPixels;
		window.height = dm.heightPixels;
		return window;
	}
	
}
 