package com.ly.luoyan.mylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ly.luoyan.mylibrary.R;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context mContext;
		private String mTitle;
		private String mMessage;
		private String mPositiveBtnText;
		private String mNagetiveBtnText;

		private TextView messageTv;
		private TextView titleTv;

		private Button sureBtn;
		private Button cancleBtn;
		private LinearLayout btnLayou;
		private ProgressBar progressBar;
		private ImageView progressImg;
		private CustomDialog dialog;
		private View viewTitleBottom;
		private View lineBottom;
		private View layout;
		private LinearLayout rootView;

		public static final int GONE = 0;
		public static final int VISIBLE = 1;

		public  int mWidth = 600;
		private  int mHeight = 300;

		private OnClickListener mPositiveBtnOnclickListener;
		private OnClickListener mNagetiveBtnOncclickListener;

		private boolean isCustomProgress = false;

		public Builder(Context context) {
			mContext = context;

		}

		/**
		 * 设置消息
		 * @param msg
		 * @return
		 */
		public Builder setMessage(String msg) {
			mMessage = msg;
			return this;
		}

		/**
		 * 设置消息 @string
		 * @param msg
		 * @return
		 */
		public Builder setMessage(int msg) {
			mMessage = (String) mContext.getText(msg);
			return this;
		}

		/**
		 * 设置标题
		 * @return
		 */
		public Builder setTitle(String title) {
			mTitle = title;
			return this;
		}

		/**
		 * 设置标题 @string
		 * @return
		 */
		public Builder setTitle(int title) {
			mTitle = (String) mContext.getText(title);
			return this;
		}

		/**
		 * 同时设置标题和内容
		 * @param title
		 * @param msg
         * @return
         */
		public Builder setTitleAndMeg(String title,String msg){
			mTitle = title;
			mMessage = msg;
			return this;
		}

		/**
		 * 同时设置标题和内容 @string
		 * @param title
		 * @param msg
         * @return
         */
		public Builder setTitleAndMeg(int title,int msg){
			mTitle = (String) mContext.getText(title);
			mMessage = (String) mContext.getText(msg);
			return this;
		}

		/**
		 * 设置positiveButton按钮
		 *
		 * @param positiveBtnText
		 * @param positiveBtnListener
		 * @return
		 */
		public Builder setPositiveButton(String positiveBtnText, OnClickListener positiveBtnListener) {
			mPositiveBtnOnclickListener = positiveBtnListener;
			mPositiveBtnText = positiveBtnText;
			return this;
		}

		/**
		 * 设置positiveButton按钮
		 * @param positiveBtnRes
		 * @param positiveBtnListener
         * @return
         */
		public Builder setPositiveButton(int positiveBtnRes, OnClickListener positiveBtnListener) {
			mPositiveBtnOnclickListener = positiveBtnListener;
			mPositiveBtnText = (String) mContext.getText(positiveBtnRes);
			return this;
		}

		/**
		 * 设置nagetiveButton按钮
		 * @param nagetiveBtnText
		 * @param nagetiveBtnListener
         * @return
         */
		public Builder setNagetiveButton(String nagetiveBtnText, OnClickListener nagetiveBtnListener) {
			mNagetiveBtnOncclickListener = nagetiveBtnListener;
			mNagetiveBtnText = nagetiveBtnText;
			return this;
		}

		/**
		 * 设置nagetiveButton按钮
		 * @param nagetiveBtnRes
		 * @param nagetiveBtnListener
         * @return
         */
		public Builder setNagetiveButton(int nagetiveBtnRes, OnClickListener nagetiveBtnListener) {
			mNagetiveBtnOncclickListener = nagetiveBtnListener;
			mNagetiveBtnText = (String) mContext.getText(nagetiveBtnRes);
			return this;
		}

		/**
		 * 设置dialog的具体宽高
		 * @param width
		 * @param height
         * @return
         */
		public Builder setDialogWidthAndHeight(int width, int height) {
			mWidth = width;
			mHeight = height;
			return this;
		}

		public CustomDialog create() {
			setDialogVIew();
			return dialog;
		}

        public void dismissDialog(){
    	      dialog.dismiss();
		      progressImg.clearAnimation();
        }

		Animation animation;
		public void setProgressbarState(int state) {
			if (state==GONE){
				rootView.setBackgroundResource(R.drawable.dialog_bt_cancle_bg01);
				progressImg.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
				lineBottom.setVisibility(View.VISIBLE);
				viewTitleBottom.setVisibility(View.VISIBLE);
				btnLayou.setVisibility(View.VISIBLE);
			}else{
				if (isCustomProgress){
					rootView.setBackgroundColor(Color.TRANSPARENT);
					animation = AnimationUtils.loadAnimation(mContext, R.anim.progress);
					LinearInterpolator lir = new LinearInterpolator();
					animation.setInterpolator(lir);
					progressImg.startAnimation(animation);
					progressImg.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.GONE);
				}else{
					rootView.setBackgroundResource(R.drawable.dialog_bt_cancle_bg01);
					progressImg.setVisibility(View.GONE);
				}
				lineBottom.setVisibility(View.GONE);
				viewTitleBottom.setVisibility(View.GONE);
				btnLayou.setVisibility(View.GONE);
			}
		}

		public void setCustomProgress(boolean customProgress){
			this.isCustomProgress = customProgress;
		}

		private void setDialogVIew() {
			dialog.getWindow().setLayout(mWidth, mHeight);
			dialog.setContentView(layout);
		}

		private void initView() {

			messageTv = (TextView) layout.findViewById(R.id.tv_message);
			titleTv = (TextView) layout.findViewById(R.id.tv_title);

			sureBtn = ((Button) layout.findViewById(R.id.bt_sure));
			cancleBtn = ((Button) layout.findViewById(R.id.bt_cancle));
			btnLayou = (LinearLayout) layout.findViewById(R.id.btn_llayout);
			progressImg = (ImageView) layout.findViewById(R.id.progress_img);
			progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
			viewTitleBottom = layout.findViewById(R.id.title_bottom_view);
			rootView = (LinearLayout) layout.findViewById(R.id.bg);
			lineBottom = layout.findViewById(R.id.line_bottom);
			if (TextUtils.isEmpty(mMessage)) {
				messageTv.setVisibility(View.GONE);
			} else {
				messageTv.setText(mMessage);
			}

			if (TextUtils.isEmpty(mTitle)) {
				titleTv.setVisibility(View.GONE);
			} else {
				titleTv.setText(mTitle);
				titleTv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,0,2));
				btnLayou.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,0,3));
			}
			if (TextUtils.isEmpty(mPositiveBtnText)) {
				sureBtn.setVisibility(View.GONE);
			} else {
				sureBtn.setText(mPositiveBtnText);
			}

			if (TextUtils.isEmpty(mNagetiveBtnText)) {
				cancleBtn.setVisibility(View.GONE);
			} else {
				cancleBtn.setText(mNagetiveBtnText);
			}

			if (TextUtils.isEmpty(mPositiveBtnText)&&TextUtils.isEmpty(mNagetiveBtnText)) {
				btnLayou.setVisibility(View.GONE);
			}

			initEvent();

		}

		private void initEvent() {
			if (mPositiveBtnOnclickListener != null) {
				sureBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						mPositiveBtnOnclickListener.onClick(dialog,
								DialogInterface.BUTTON_POSITIVE);
					}
				});
			}
			if (mNagetiveBtnOncclickListener != null) {
				cancleBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						mNagetiveBtnOncclickListener.onClick(dialog,
								DialogInterface.BUTTON_NEGATIVE);
					}
				});
			}
		}

		public void init() {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			dialog = new CustomDialog(mContext, R.style.Dialog);
			layout = inflater.inflate(R.layout.dialog_custom, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			dialog.setCanceledOnTouchOutside(false);
			initView();
		}

	}






}
