package com.meiliangzi.app.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.tools.picompressor.HttpCallback;
import com.meiliangzi.app.ui.view.Academy.NewLoginActivity;
import com.meiliangzi.app.ui.view.Academy.bean.BaseInfo;
import com.meiliangzi.app.ui.view.Academy.bean.RefreshTokenBean;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.meiliangzi.app.config.Constant.ChanYeXY;


/**
 * Created by kk on 2017/9/19.
 */

public class OkhttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType imageType = MediaType.parse("image/jpeg; charset=utf-8");
    private static OkhttpUtils downloadUtil;
    private static Context context;
    private static File CacheRoot;
    private static OkHttpClient okHttpClient;
    private static OkhttpUtils okhttpUtils;
    //private final Handler handler;
    String rootpath;
    Gson gson=new Gson();

    public OkhttpUtils(Context context) {
        this.context = context;

        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? context
                .getExternalCacheDir() : context.getCacheDir();
        rootpath = getpath();

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();

    }

    //设置外部访问的方法
    public static OkhttpUtils getInstance(Context context) {
        if (okhttpUtils == null) {
            synchronized (OkhttpUtils.class) {
                if (okhttpUtils == null) {
                    return okhttpUtils = new OkhttpUtils(context);
                }
            }
        }
        return okhttpUtils;
    }

    private String getpath() {
        boolean hsaSDscard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hsaSDscard) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else
            return Environment.getDownloadCacheDirectory().getAbsolutePath() + "/";

    }

    public static void imageloger(String url, HttpCallback callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();


        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);


    }


    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    //deleteAllFiles(new File(savePath));
                    File file = new File(savePath, getNameFromUrl(url));

                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(file.getPath());
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {

        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String url);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    public interface okhttpListener {
        /**
         * 下载成功
         */
        void okhttpListenerSuccess(String data);

        /**
         * @param progress 下载进度
         */
        void okhttpListenerloading(int progress);

        /**
         * 下载失败
         */
        void okhttpListenerFailed();
    }

    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    public void getList(String url, JSONObject jsonObject, final onCallBack onCallBack) {

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ChanYeXY + url)
                .newBuilder();
        urlBuilder.addQueryParameter("paperId", jsonObject.getString("paperId"));
        reqBuild.addHeader("userId", jsonObject.getString("userId"));
        reqBuild.addHeader("token", NewPreferManager.getToken());
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (onCallBack != null) {
//切换到主线程

                    onCallBack.onFaild(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        try {
                            gson.fromJson(json, BaseInfo.class);
                        } catch (Exception e) {

                        }


                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("异常"));
                }
            }
        });
    }

    public void getErrorList(String url, JSONObject jsonObject, final onCallBack onCallBack) {

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ChanYeXY + url)
                .newBuilder();
        reqBuild.addHeader("userId", jsonObject.getString("userId"));
        reqBuild.addHeader("token", NewPreferManager.getToken());
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (onCallBack != null) {
//切换到主线程

                    onCallBack.onFaild(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.code() == 401) {

                        } else {
                            String json = response.body().string();

                            if (onCallBack != null) {
                                onCallBack.onResponse(json);
                                return;
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("异常"));
                }
            }
        });
    }

    public void getPageList(String url, JSONObject jsonObject, final onCallBack onCallBack) {

        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ChanYeXY + url)
                .newBuilder();
        urlBuilder.addQueryParameter("type", jsonObject.getString("type"));
        urlBuilder.addQueryParameter("pageNumber", jsonObject.getString("pageNumber"));
        urlBuilder.addQueryParameter("pageSize", jsonObject.getString("pageSize"));
        reqBuild.addHeader("userId", jsonObject.getString("userId"));
        reqBuild.addHeader("token", NewPreferManager.getToken());
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                if (onCallBack != null) {
//切换到主线程
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
                    onCallBack.onFaild(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

//                handler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                    }
//                });
                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("异常"));
                }
            }
        });
    }

    public static void postJson(String userId, String json, String url, final onCallBack onCallBack) {

        Request request;
        if (json == null) {
            //创建一个请求对象
            request = new Request.Builder()
                    .url(ChanYeXY + url)
                    .build();
        } else {
            RequestBody requestBody = RequestBody.create(JSON, json);
            //创建一个请求对象
            request = new Request.Builder()
                    .url(ChanYeXY + url)
                    .addHeader("userId", userId)
                    .addHeader("token",NewPreferManager.getToken())
                    .post(requestBody)
                    .build();
        }
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onCallBack != null) {

                    onCallBack.onFaild(e);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("有异常"));
                }

            }
        });


    }

    //post请求
    public void doPost(String url, Map<String, String> maps, final onCallBack onCallBack) {
        Request.Builder rbuilder = new Request.Builder();

        FormBody.Builder builder = new FormBody.Builder();

        //循环form表单，将表单内容添加到form builder中
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
            if (key.equals("userId")) {
                rbuilder.addHeader(key, value);
            }
        }
        rbuilder.addHeader("token", NewPreferManager.getToken());
        RequestBody requestBody = builder.build();
        Request request = rbuilder
                .url(ChanYeXY + url)

                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (onCallBack != null) {

                    onCallBack.onFaild(e);
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("有异常"));
                }
            }
        });
    }
    //post请求
    public void dologin(String url, Map<String, String> maps, final onCallBack onCallBack) {
        Request.Builder rbuilder = new Request.Builder();

        FormBody.Builder builder = new FormBody.Builder();

        //循环form表单，将表单内容添加到form builder中
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
            if (key.equals("userId")) {
                rbuilder.addHeader(key, value);
            }
        }
        RequestBody requestBody = builder.build();
        Request request = rbuilder
                .url(ChanYeXY + url)

                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (onCallBack != null) {

                    onCallBack.onFaild(e);
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("有异常"));
                }
            }
        });
    }

    //定义接口进行回调
    public interface onCallBack {
        void onFaild(Exception e);

        void onResponse(String json);
    }

    /**
     * @param uploadUrl put请求地址
     * @param localPath 本地文件路径
     * @return 响应的结果 和 HTTP status code
     * @throws IOException
     */
    public void put(String uploadUrl, String localPath, final onCallBack onCallBack) throws IOException {
        File file = new File(localPath);


        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            String imageType = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("articleImage", file.getName(), body1);
        }

        Request request = new Request.Builder().addHeader("userId", NewPreferManager.getId()).url(ChanYeXY + uploadUrl).put(requestBody.build()).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onCallBack != null) {
//切换到主线程

                    onCallBack.onFaild(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("异常"));
                }
            }
        });


    }


    public void getList(String url, Map<String, String> maps, final onCallBack onCallBack) {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ChanYeXY + url)
                .newBuilder();
        //循环form表单，将表单内容添加到form builder中
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            urlBuilder.addQueryParameter(key, value);
            if (key.equals("userId")) {
                reqBuild.addHeader(key, value);
            }

            // builder.add(key,value);
        }
        reqBuild.addHeader("token", NewPreferManager.getToken());


        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (onCallBack != null) {
//切换到主线程

                    onCallBack.onFaild(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {


                try {
                    if (response != null && response.isSuccessful()) {
                        String json = response.body().string();
                        if (onCallBack != null) {
                            onCallBack.onResponse(json);
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onCallBack != null) {
                    onCallBack.onFaild(new Exception("异常"));
                }
            }
        });
    }
    public class TokenInterceptor implements Interceptor {

        private String authorization;


        @Override
      public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                     Log.i("response.code=" ,""+response.code());

                    if (isTokenExpired(response)) {
                        synchronized("authorization"){
                            try {
                                //根据和服务端的约定判断token过期
                                Log.i("response.code=" ,"静默自动刷新Token,然后重新请求数据"+response.code());
                                //同步请求方式，获取最新的Token
                                String newSession;
//                                if(!TextUtils.isEmpty(NewPreferManager.getrefreshToken())){
//                                     newSession = NewPreferManager.getrefreshToken();
//                                   // NewPreferManager.saverefreshToken("");
//                                }else {
//
//                                }
                                newSession = getNewToken();

                                Log.i("token","rtoken====="+newSession);
                               // NewPreferManager.saveToken(newSession);
                                //使用新的Token，创建新的请求
                                Request newRequest = chain.request().
                                        newBuilder()
                                        .header("token",newSession)
                                        .build();
                                //request.newBuilder().addHeader("token",NewPreferManager.getToken());
                                //重新请求
                                return chain.proceed(newRequest);
                            }catch (Exception e){
                                Intent intent=new Intent(context, NewLoginActivity.class);
                                context.startActivity(intent);
                                ( (Activity)context).finish();
                            }

                        }
                        }


                     return response;
                 }

             /**
 28      * 根据Response，判断Token是否失效
 29      *
 30      * @param response
 31      * @return
 32      */
             private boolean isTokenExpired(Response response) {
                     if (response.code() == 401) {
                            return true;
                         }
                     return false;
                 }

             /**
 41      * 同步请求方式，获取最新的Token
 42      *
 43      * @return
 44      */
             private String getNewToken() throws IOException {


                 FormBody.Builder builder = new FormBody.Builder();
                 builder.add("token",NewPreferManager.getrefreshToken());

                 RequestBody requestBody = builder.build();
                 Request request = new Request.Builder()
                         .url(ChanYeXY+"organizationService/userAccount/refresh")
                         .put(requestBody)
                         .build();
                 Call call = okHttpClient.newCall(request);
                 Response response = call.execute();

                 if(response.code()!=200){
                     Intent intent=new Intent(context, NewLoginActivity.class);
                     context.startActivity(intent);
                     ( (Activity)context).finish();
                     return "";
                 }else {

                     RefreshTokenBean bean=gson.fromJson(response.body().string(),RefreshTokenBean.class);
                     NewPreferManager.saveToken(bean.getData().getToken());
                     NewPreferManager.saverefreshToken(bean.getData().getRefreshToken());
                     return bean.getData().getToken();
                 }



                 }


 }

}