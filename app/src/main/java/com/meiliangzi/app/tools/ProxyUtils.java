package com.meiliangzi.app.tools;


import com.meiliangzi.app.model.api.IHttpService;
import com.meiliangzi.app.model.http.HttpProxyInvocation;
import com.meiliangzi.app.model.http.HttpProxyNoDialogInvocation;

import java.lang.reflect.Proxy;


public class ProxyUtils {
    private static HttpProxyInvocation proxyHandler = new HttpProxyInvocation();
    private static HttpProxyNoDialogInvocation proxyHandlerNoDialog = new HttpProxyNoDialogInvocation();
    public static IHttpService getHttpProxyNoDialog() {
        return (IHttpService) Proxy.newProxyInstance(proxyHandlerNoDialog.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxyHandlerNoDialog);
    }
    public static IHttpService getHttpProxy() {
        return (IHttpService) Proxy.newProxyInstance(proxyHandler.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxyHandler);
    }
}
