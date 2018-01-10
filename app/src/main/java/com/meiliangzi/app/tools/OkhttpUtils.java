package com.meiliangzi.app.tools;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.meiliangzi.app.tools.picompressor.HttpCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.meiliangzi.app.config.Constant.BASE_URL;


/**
 * Created by kk on 2017/9/19.
 */

public class OkhttpUtils {
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    private static OkhttpUtils downloadUtil;
    private  static Context context;
    private static File CacheRoot;
    private final OkHttpClient okHttpClient;
    String rootpath;

    public OkhttpUtils(Context context) {
        this.context=context;
        CacheRoot=Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? context
                .getExternalCacheDir() : context.getCacheDir();
        okHttpClient = new OkHttpClient();
        rootpath=getpath();
    }
    private String getpath() {
        boolean hsaSDscard=   Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(hsaSDscard){
            return Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        }else
            return Environment.getDownloadCacheDirectory().getAbsolutePath()+"/";

    }
    public static void postJson(String json, String url, HttpCallback callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        Request request;
        if (json == null) {
            //创建一个请求对象
            request = new Request.Builder()
                    .url(BASE_URL + url).get()
                    .build();
        } else {
            RequestBody requestBody = RequestBody.create(JSON, json);
            //创建一个请求对象
            request = new Request.Builder()
                    .url(BASE_URL + url)
                    .post(requestBody)
                    .build();
        }


        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        //判断请求是否成功
           /* if(response.isSuccessful()){
                //打印服务端返回结果
                //response.body();
                callback.onResponse();
            }else {

            }*/


    }
    public static void imageloger(String url, HttpCallback callback) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
       /* Response response = client.newCall(request).execute();
        InputStream is = response.body().byteStream();
        Bitmap bm = BitmapFactory.decodeStream(is);
        imageView.setImageBitmap(bm);*/
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
       /* Request request;
        if (json == null) {
            //创建一个请求对象
            request = new Request.Builder()
                    .url(BASE_URL + url).get()
                    .build();
        } else {
            RequestBody requestBody = RequestBody.create(JSON, json);
            //创建一个请求对象
            request = new Request.Builder()
                    .url(BASE_URL + url)
                    .post(requestBody)
                    .build();
        }*/


        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        //判断请求是否成功
           /* if(response.isSuccessful()){
                //打印服务端返回结果
                //response.body();
                callback.onResponse();
            }else {

            }*/


    }
    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
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
     * @throws IOException
     * 判断下载目录是否存在
     */
    private  String isExistDir(String saveDir) throws IOException {

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
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }



    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess( String url);

        /**
         * @param progress
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
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
}
