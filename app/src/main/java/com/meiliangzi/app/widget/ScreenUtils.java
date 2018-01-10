package com.meiliangzi.app.widget;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕工具
 * Created by nereo on 15/11/19.
 * Updated by nereo on 2016/1/19.
 */
public class ScreenUtils {

	public static Point getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point out = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			display.getSize(out);
		} else {
			int width = display.getWidth();
			int height = display.getHeight();
			out.set(width, height);
		}
		return out;
	}

	private ScreenUtils() {
	}

	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static float dpToPxInt(Context context, float dp) {
		return (int) (dpToPx(context, dp) + 0.5f);
	}

	public static float pxToDpCeilInt(Context context, float px) {
		return (int) (pxToDp(context, px) + 0.5f);
	}

	public static int getSlidingMenuWidth(Context context) {
		return (context.getResources().getDisplayMetrics().widthPixels / 2);
	}

	public static int getPading(Context context) {
		return (context.getResources().getDisplayMetrics().widthPixels * 2 / 8);
	}
}
