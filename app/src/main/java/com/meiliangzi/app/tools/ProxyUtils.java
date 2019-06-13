package com.meiliangzi.app.tools;


import com.meiliangzi.app.model.api.IHttpService;
import com.meiliangzi.app.model.http.HttpChanYeYuanProxyInvocation;
import com.meiliangzi.app.model.http.HttpCheckProxyGetInvocation;
import com.meiliangzi.app.model.http.HttpCheckProxyInvocation;
import com.meiliangzi.app.model.http.HttpProxyInvocation;
import com.meiliangzi.app.model.http.HttpProxyNoDialogInvocation;

import java.lang.reflect.Proxy;


public class ProxyUtils {
    private static HttpProxyInvocation proxyHandler = new HttpProxyInvocation();
    private static HttpCheckProxyInvocation proxycheckHandler = new HttpCheckProxyInvocation();
    private static HttpCheckProxyGetInvocation proxycheckgetHandler = new HttpCheckProxyGetInvocation();
    private static HttpProxyNoDialogInvocation proxyHandlerNoDialog = new HttpProxyNoDialogInvocation();
    private static HttpChanYeYuanProxyInvocation chanYeYuanProxyInvocation = new HttpChanYeYuanProxyInvocation();
    public static IHttpService getHttpProxyNoDialog() {
        return (IHttpService) Proxy.newProxyInstance(proxyHandlerNoDialog.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxyHandlerNoDialog);
    }
    public static IHttpService getHttpProxy() {
        return (IHttpService) Proxy.newProxyInstance(proxyHandler.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxyHandler);
    }
    public static IHttpService getHttpCheckProxy() {
        return (IHttpService) Proxy.newProxyInstance(proxycheckHandler.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxycheckHandler);
    }
    public static IHttpService GetHttpCheckProxy() {
        return (IHttpService) Proxy.newProxyInstance(proxycheckgetHandler.getClass().getClassLoader(), new Class[]{IHttpService.class}, proxycheckgetHandler);
    }
    public static IHttpService gethttp() {
        return (IHttpService) Proxy.newProxyInstance(chanYeYuanProxyInvocation.getClass().getClassLoader(), new Class[]{IHttpService.class}, chanYeYuanProxyInvocation);
    }
}
