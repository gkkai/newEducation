package com.meiliangzi.app.model.http;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meiliangzi.app.tools.JsonUtils;
import com.meiliangzi.app.tools.ReflectUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author 作者吴伟:
 * @version 创建时间：2016年3月29日 下午1:48:37
 * 类说明
 */
public class NoDialogResponseHandler extends JsonHttpResponseHandler
{
	private static final int SUCCESS = 0;
	private static final String OPCODE = "status";
	private static final String DATA = "msg";
	private static final String ERROR = "showErrorMessage";

	private Object context;
	private Class<?> resultCls;
	private String refreshMethod;
	
	public NoDialogResponseHandler(Object context, Class<?> resultCls, String refreshMethod)
	{
		this.context = context;
		this.resultCls = resultCls;
		this.refreshMethod = refreshMethod;
	}
	
	@Override
	public void onSuccess(JSONObject response)
	{
		Log.i("grage", response.toString());
//		DialogUtils.dismissLoading(refreshMethod);
		// LogUtils.e(response.toString());
//		Log.e("grage", response.toString());
		int status = -5;
		try
		{
			status = response.getInt(OPCODE);
			if (status == SUCCESS)
			{
				Object bean = JsonUtils.parserJSONObject(response, resultCls);
				ReflectUtils.invokeMethod(context, refreshMethod, bean, resultCls);
			} else
			{
				throw new JSONException(response.getString(DATA));
			}
		} catch (Exception e)
		{
			// ReflectUtils.invokeMethod(context, ERROR, e.getMessage(),
			// String.class);
			ReflectUtils.invokeMethod(context, ERROR, status, e.getMessage(), Integer.class, String.class);
		}
	}
	
	@Override
	public void onFailure(Throwable e, JSONObject errorResponse)
	{
//		DialogUtils.dismissLoading(refreshMethod);
//		ToastUtils.custom("请求失败");
		Log.e("grage", errorResponse.toString());
	}
	
	@Override
	public void onFailure(Throwable e)
	{
//		DialogUtils.dismissLoading(refreshMethod);
		Log.e("grage", e.toString());
	}
	
}