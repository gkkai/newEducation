/**
 * Copyright (c) 2013-2014, Rinc Liu (http://rincliu.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meiliangzi.app.ui.dialog;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.meiliangzi.app.R;


public class RLAlertDialog extends RLDialog {
	private Listener listener;
	private CharSequence title, msg;
	private Context context;
	private Button btn_left, btn_right;
	private TextView tv_msg;
	private String leftBtnStr, rightBtnStr;
	private int linkify = -1;
	private View mView;

	/**
	 *
	 * @param context
	 * @param title
	 * @param msg
	 * @param leftBtnStr
	 * @param rightBtnStr
	 * @param listener
	 */
	public RLAlertDialog(Context context, String title, CharSequence msg, String leftBtnStr, String rightBtnStr, Listener listener) {
		super(context);
		this.title = title;
		this.msg = msg;
		this.leftBtnStr = leftBtnStr;
		this.rightBtnStr = rightBtnStr;
		this.listener = listener;
		this.context = context;
		super.createView();
		super.setCanceledOnTouchOutside(false);
	}

	/**
	 *
	 * @param context
	 * @param title
	 * @param view
	 * @param leftBtnStr
	 * @param rightBtnStr
	 * @param listener
	 */
	public RLAlertDialog(Context context, String title, View view, String leftBtnStr, String rightBtnStr, Listener listener) {
		super(context);
		this.title = title;
		this.leftBtnStr = leftBtnStr;
		this.rightBtnStr = rightBtnStr;
		this.listener = listener;
		this.context = context;
		this.mView = view;
		super.createView();
		super.setCanceledOnTouchOutside(false);
	}

	/**
	 *
	 * @param context
	 * @param title
	 * @param msg
	 * @param msgLinkify
	 * @param leftBtnStr
	 * @param rightBtnStr
	 * @param listener
	 */
	public RLAlertDialog(Context context, String title, CharSequence msg, int msgLinkify,
                         String leftBtnStr, String rightBtnStr, Listener listener) {
		super(context);
		this.title = title;
		this.msg = msg;
		this.linkify = msgLinkify;
		this.leftBtnStr = leftBtnStr;
		this.rightBtnStr = rightBtnStr;
		this.listener = listener;
		this.context = context;
		super.createView();
		super.setCanceledOnTouchOutside(false);
	}

	@Override
	protected View getView() {
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		if (mView != null) {
			LinearLayout ll1 = (LinearLayout) view.findViewById(R.id.ll1);
			tv_msg.setVisibility(View.GONE);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			ll1.addView(mView, params);
		}
//		tv_msg.setAutoLinkMask(linkify);
		tv_msg.setText(msg);
		btn_left = (Button) view.findViewById(R.id.btn_left);
		btn_left.setText(leftBtnStr);
		if (linkify == -1) {
			btn_right = (Button) view.findViewById(R.id.btn_right);
			btn_right.setText(rightBtnStr);
		} else {
			btn_right = (Button) view.findViewById(R.id.btn_right);
			btn_right.setText(rightBtnStr);
		}
		btn_left.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dismiss();
				listener.onLeftClick();
			}
		});
		btn_right.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				dismiss();
				listener.onRightClick();
			}
		});
		if (TextUtils.isEmpty(rightBtnStr)) {
			btn_right.setVisibility(View.GONE);
		}
		return view;
	}

	/**
	 *
	 */
	public interface Listener {
		public void onLeftClick();

		public void onRightClick();
	}
}