package com.meiliangzi.app.model.http;


import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.meiliangzi.app.tools.JsonUtils;
import com.meiliangzi.app.tools.ReflectUtils;
import com.meiliangzi.app.tools.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class HttpResponseHandler extends JsonHttpResponseHandler
{
	private static final int HTTP_SUCCESS = 0;
	private static final int HTTP_NODATA = 1;
	private static final int HTTP_SUCCESS_200 = 200;
	private static final int SESSION_INVALID = -1;
	private static final String OPCODE = "status";
	private static final String DATA = "msg";
	private static final String ERROR = "showErrorMessage";
	private Object context;
	private Class<?> resultCls;
	private String refreshMethod;
	private int opcode;

	public HttpResponseHandler(Object context, Class<?> resultCls, String refreshMethod)
	{
		this.context = context;
		this.resultCls = resultCls;
		this.refreshMethod = refreshMethod;
	}



	@Override
	public void onStart()
	{
//		DialogUtils.showLoading(context, refreshMethod);
	}

	@Override
	public void onFinish()
	{
//		DialogUtils.dismissLoading(refreshMethod);
;	}

	@Override
	public void onSuccess(JSONObject response)
	{
		try
		{
			Log.i("grage", response.toString());

			opcode = response.getInt(OPCODE);

				switch (opcode)
			{
				case HTTP_SUCCESS:
					Object bean = JsonUtils.parserJSONObject(response.toString(), resultCls);
					ReflectUtils.invokeMethod(context, refreshMethod, bean, resultCls);
					break;
				case HTTP_SUCCESS_200:
					bean = JsonUtils.parserJSONObject(response.toString(), resultCls);
					ReflectUtils.invokeMethod(context, refreshMethod, bean, resultCls);
					break;

				default:
					//bean = JsonUtils.parserJSONObject(response.toString(), resultCls);

					throw new JSONException(response.getString(DATA));
			}
		} catch (Exception e)
		{
			ReflectUtils .invokeMethod(context, ERROR, opcode, e.getMessage(), Integer.class, String.class);
//			ReflectUtils.invokeMethod(context, ERROR, opcode, e.getMessage(), Integer.class, String.class);

		}
	}

	@Override
	public void onFailure(Throwable e, JSONObject errorResponse)
	{
//		DialogUtils.dismissLoading(refreshMethod);
		ToastUtils.custom("请求失败");
		Log.e("grage", errorResponse.toString());
	}

	@Override
	public void onFailure(Throwable e)
	{
//		DialogUtils.dismissLoading(refreshMethod);
		Log.e("grage", e.toString());
	}
}
