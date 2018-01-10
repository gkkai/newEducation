package com.meiliangzi.app.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

import java.util.concurrent.ConcurrentHashMap;



public class DialogUtils
{
	private static ConcurrentHashMap<String, Dialog> loadingCache = new ConcurrentHashMap<String, Dialog>();
	private static ConcurrentHashMap<Object, Dialog> errorCache = new ConcurrentHashMap<Object, Dialog>();

	/**
	 * 是否显示dialog
	 */
	private static boolean isLoading;

	private DialogUtils()
	{
	}
	
	public static void showLoading(Object context, String refreshMethod)
	{
		Dialog loadingDialog;
//		if (isLoading){
//			Log.i("grage","isLoading=="+isLoading);
//			return;
//		}
		if (!loadingCache.isEmpty())
		{
			return;
		}
		if (isFragment(context))
		{
			loadingDialog = createDialog(((Fragment) context).getActivity(), R.style.Dialog_Loading);
		} else
		{
			loadingDialog = createDialog(((BaseActivity) context), R.style.Dialog_Loading);
		}
		loadingCache.put(refreshMethod,loadingDialog);
		loadingDialog.setContentView(R.layout.dialog_loading);
		loadingDialog.setCanceledOnTouchOutside(false);
		if (!loadingDialog.isShowing())
		{
			loadingDialog.show();
		}
	}
//
//	public static void showError(Object context)
//	{
//		Dialog error;
//		if (isFragment(context))
//		{
//			if (errorCache.get(((Fragment) context).getActivity()) == null)
//			{
//				error = createDialog(((Fragment) context).getActivity(), R.style.Dialog_Common);
//				errorCache.put(((Fragment) context).getActivity(), error);
//			} else
//			{
//				return;
//			}
//		} else
//		{
//			error = createDialog(((FragmentActivity) context), R.style.Dialog_Common);
//			errorCache.put(context, error);
//		}
//		View view = View.inflate(error.getContext(), R.layout.dialog_error, null);
//		error.setContentView(view);
//		error.setCanceledOnTouchOutside(false);
//		error.setCancelable(false);
//		error.getWindow().setWindowAnimations(R.style.BottomToTopAnim);
//		initErrorBtnListener(context, view);
//		error.show();
//	}
	
//	public static void showExit(Context context)
//	{
//		Dialog exitDialog = createDialog(context, R.style.Dialog_Common);
//		View view = View.inflate(exitDialog.getContext(), R.layout.dialog_exit, null);
//		exitDialog.setContentView(view);
//		exitDialog.setCanceledOnTouchOutside(false);
//		exitDialog.setCancelable(false);
//		initExitBtnListener(context, exitDialog, view);
//		exitDialog.show();
//	}
	/***
	 *拨打电话
	 * @param context
	 * @param tel
	 */
	public static void showTelDialog(final Activity context, final String tel)
	{
		new AlertDialog.Builder(context).setMessage("呼叫:" + tel).setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				IntentUtils.startDialNumberIntent(context, tel);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		}).show();
	}
	
	public static void dismissLoading(String refreshMethod)
	{
		try {
			Dialog dialog = loadingCache.get(refreshMethod);
			if (dialog != null&&dialog.isShowing())
			{
				loadingCache.remove(refreshMethod);
				dialog.dismiss();
			}
		}catch (Exception e){
           e.printStackTrace();
			if (!loadingCache.isEmpty())
			loadingCache.clear();
		}
	}
	
	public static void dismissError(Object context)
	{
		if (isFragment(context))
		{
			errorCache.get(((Fragment) context).getActivity()).dismiss();
			errorCache.remove(((Fragment) context).getActivity());
		} else
		{
			errorCache.get(context).dismiss();
			errorCache.remove(context);
		}
	}
	
	public static void dismissExit(Dialog exitDialog)
	{
		exitDialog.dismiss();
	}
	
	public static Dialog createDialog(Context context, int styleRes)
	{
		return new ProgressDialog(context, styleRes);
	}
	
	private static boolean isFragment(Object context)
	{
		return context instanceof Fragment;
	}

	private static boolean isBaseActivity(Object context)
	{
		return context instanceof BaseActivity;
	}
	
//	private static void initErrorBtnListener(final Object context, View view)
//	{
//		Button retry = (Button) view.findViewById(R.id.retry);
//		retry.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				ReflectUtils.invokeMethod(context, "retryRetrive");
//				dismissError(context);
//			}
//		});
//		Button setNetwork = (Button) view.findViewById(R.id.set_network);
//		setNetwork.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Dialog dialog;
//				if (isFragment(context))
//				{
//					dialog = errorCache.get(((Fragment) context).getActivity());
//				} else
//				{
//					dialog = errorCache.get(context);
//				}
//				IntentUtils.startSettingIntent(dialog.getContext());
//			}
//		});
//		Button back = (Button) view.findViewById(R.id.back);
//		back.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				dismissError(context);
//			}
//		});
//	}
//
//	private static void initExitBtnListener(final Context context, final Dialog exitDialog, View view)
//	{
//		Button exit = (Button) view.findViewById(R.id.fancybtn_home_personal_exit_confirm);
//		exit.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				SessionUtils.clearUserId();
//				dismissExit(exitDialog);
//				((Activity) context).finish();
//			}
//		});
//		Button cancel = (Button) view.findViewById(R.id.fancybtn_home_personal_exit_cancel);
//		cancel.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				dismissExit(exitDialog);
//			}
//		});
//	}
	
}
