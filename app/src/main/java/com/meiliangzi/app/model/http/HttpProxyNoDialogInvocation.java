package com.meiliangzi.app.model.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.annotation.HttpRequest;
import com.meiliangzi.app.tools.ReflectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class HttpProxyNoDialogInvocation implements InvocationHandler
{
	private AsyncHttpClient client = new AsyncHttpClient();
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] params) throws Throwable
	{
		String[] arguments = method.getAnnotation(HttpRequest.class).arguments();
		Class<?> resultCls = method.getAnnotation(HttpRequest.class).resultClass();
		String refreshMethod = method.getAnnotation(HttpRequest.class).refreshMethod();
		String url = method.getAnnotation(HttpRequest.class).url();
		NoDialogResponseHandler response = (NoDialogResponseHandler) ReflectUtils.constructNoDialogResponse(params[0], resultCls, refreshMethod);
		client.post(Constant.BASE_URL +url + method.getName(), createRequestParams(arguments, params), response);
		Log.i("grage", Constant.BASE_URL+url +method.getName());
		return new Object();
	}
	



	private RequestParams createRequestParams(String[] arguments, Object[] params)
	{
		RequestParams requestParams = new RequestParams();
		StringBuilder paras=new StringBuilder();
		if (arguments.length != 0)
		{
			for (int pos = 0; pos < arguments.length; pos++)
			{
				requestParams.put(arguments[pos], params[pos + 1].toString());
				paras.append(arguments[pos]).append("=").append(params[pos + 1].toString()).append("&");
			}

		}

		if (!paras.toString().isEmpty()){
			Log.i("grage",paras.subSequence(0, paras.length()-1).toString());
		}

//		requestParams.put("city", PreferUtils.getString("city", "西安"));
		return requestParams;
	}
}
