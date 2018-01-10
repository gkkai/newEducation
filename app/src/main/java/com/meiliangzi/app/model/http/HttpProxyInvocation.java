package com.meiliangzi.app.model.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.annotation.HttpRequest;
import com.meiliangzi.app.tools.ReflectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class HttpProxyInvocation implements InvocationHandler {
    private AsyncHttpClient client = new AsyncHttpClient();
    private boolean flag;

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        String[] arguments = method.getAnnotation(HttpRequest.class).arguments();
        Class<?> resultCls = method.getAnnotation(HttpRequest.class).resultClass();
        String refreshMethod = method.getAnnotation(HttpRequest.class).refreshMethod();
        String url = method.getAnnotation(HttpRequest.class).url();
        HttpResponseHandler response = ReflectUtils.constructHttpResponse(params[0], resultCls, refreshMethod);
        RequestParams requestParams = createRequestParams(arguments, params, flag);
        client.post(Constant.BASE_URL + url + method.getName(), requestParams, response);
        Log.i("grage", Constant.BASE_URL + url + method.getName());
        return new Object();

    }


    private RequestParams createRequestParams(String[] arguments, Object[] params, boolean flag) {
        RequestParams requestParams = null;
        try {
            requestParams = new RequestParams();
            StringBuilder paras = new StringBuilder();
            if (arguments.length != 0) {
                for (int pos = 0; pos < arguments.length; pos++) {
                    if (params[pos + 1] == null) {
                        continue;
                    }
                    if (params[pos + 1] instanceof File) {
                        requestParams.put(arguments[pos], (File) params[pos + 1]);
                    } else {
                        requestParams.put(arguments[pos], params[pos + 1].toString());
                    }
                    if (params[pos + 1] != null)
                        paras.append(arguments[pos]).append("=").append(params[pos + 1].toString());
                }

                Log.i("grage", paras.subSequence(0, paras.length() - 1).toString());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//		requestParams.put("city", PreferUtils.getString("city", "西安"));
        return requestParams;
    }
}
