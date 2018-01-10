package com.meiliangzi.app.ui.listener;

import android.os.Handler;
import android.os.Message;

import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.SetttingActivity;

public class ClearCacheHandler extends Handler
{
	private static final int SUCCES_MESSAGE = Integer.MAX_VALUE;
	private SetttingActivity settingActivity;
	
	public ClearCacheHandler(SetttingActivity settingActivity)
	{
		this.settingActivity = settingActivity;
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		int delCount = (Integer) msg.obj;
		if (delCount == SUCCES_MESSAGE)
		{
			ToastUtils.custom("清理成功");
			settingActivity.refreshCacheSize();
			settingActivity.onClearCacheOver();
		}
	}
}
