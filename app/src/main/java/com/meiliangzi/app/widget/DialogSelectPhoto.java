package com.meiliangzi.app.widget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.BitmapUtil;
import com.meiliangzi.app.ui.view.creativecommons.ImageSelectActivity;
import com.tandong.bottomview.view.BottomView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static android.app.Activity.RESULT_OK;

/**
 * 选择照片弹出框
 *
 * @author xiaobo
 * @version 1.0
 * @date 2016/12/3 10:42
 */
public class DialogSelectPhoto {

    String stupath, shenfenpath;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 11;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 12;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private String path;// 图片全路径
    private String temppath;
    private static final int PERMISSON_REQUESTCODE = 0;
    public static final  String FOLODER = Environment.getExternalStorageDirectory()+"/com.union.edu/";
    private File sdcardTempFile;

    private static final int REQUEST_CODE_PERMISSION_CAMERA = 1001;
    private static final int REQUEST_CODE_SETTING = 1002;
    private Context context;
    private  int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void getSelectPhoto(final Context context) {
        final BottomView bottomView = new BottomView(context, R.style.BottomViewTheme_Defalut, R.layout.pannel_select_photo);
        this.context = context;
        View view = bottomView.getView();
        TextView tv_take_photograph = (TextView) view.findViewById(R.id.tv_take_photograph);
        TextView tv_get_albums = (TextView) view.findViewById(R.id.tv_get_albums);
        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);

        tv_take_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
                Date student = new Date();
                String str = sdf.format(student);
                sdcardTempFile = new File(FOLODER, "tmp" + str + ".jpg");
                if(sdcardTempFile.exists()){
                    sdcardTempFile.delete();
                }
                if(!sdcardTempFile.exists()){
                    try {
                        File file = new File(FOLODER);
                        if(!new File(FOLODER).exists()){
                            file.mkdir();
                        }
                        sdcardTempFile.createNewFile();
                        // 调用系统的拍照功能
                        Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 指定调用相机拍照后照片的储存路径
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            intent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(context, "com.meiliangzi.app.FileProvider", sdcardTempFile);
                            intent3.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                        }else {
                            intent3.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
                        }
                        ((FragmentActivity) context).startActivityForResult(intent3, PHOTO_REQUEST_TAKEPHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                bottomView.dismissBottomView();
            }
        });

        tv_get_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((FragmentActivity) context).startActivityForResult(intent2, PHOTO_REQUEST_GALLERY);
                bottomView.dismissBottomView();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomView.dismissBottomView();
            }
        });
        bottomView.showBottomView(true);
    }
    public void getSelectPhoto(final Context context,String type) {
        final BottomView bottomView = new BottomView(context, R.style.BottomViewTheme_Defalut, R.layout.pannel_select_photo);
        this.context = context;
        View view = bottomView.getView();
        TextView tv_take_photograph = (TextView) view.findViewById(R.id.tv_take_photograph);
        TextView tv_get_albums = (TextView) view.findViewById(R.id.tv_get_albums);
        LinearLayout ll_cancel = (LinearLayout) view.findViewById(R.id.ll_cancel);

        tv_take_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
                Date student = new Date();
                String str = sdf.format(student);
                sdcardTempFile = new File(FOLODER, "tmp" + str + ".jpg");
                if(sdcardTempFile.exists()){
                    sdcardTempFile.delete();
                }
                if(!sdcardTempFile.exists()){
                    try {
                        File file = new File(FOLODER);
                        if(!new File(FOLODER).exists()){
                            file.mkdir();
                        }
                        sdcardTempFile.createNewFile();
                        // 调用系统的拍照功能
                        Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 指定调用相机拍照后照片的储存路径
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            intent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(context, "com.meiliangzi.app.FileProvider", sdcardTempFile);
                            intent3.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                        }else {
                            intent3.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
                        }
                        //intent3.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
                        ((FragmentActivity) context).startActivityForResult(intent3, PHOTO_REQUEST_TAKEPHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                bottomView.dismissBottomView();
            }
        });

        tv_get_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((FragmentActivity) context).startActivityForResult(intent2, PHOTO_REQUEST_GALLERY);
//                // TODO  知识共享
//                Intent commmons=new Intent(this, ImageSelectActivity.class);
//                startActivityForResult(commmons);
                bottomView.dismissBottomView();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomView.dismissBottomView();
            }
        });
        bottomView.showBottomView(true);
    }

    public String onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            ((FragmentActivity) context).setResult(Activity.RESULT_CANCELED);
            ((FragmentActivity) context).finish();
            return "";
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                path = sdcardTempFile.getAbsolutePath();
                if(this.type == 1){
                    startPhotoZoom(path);
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data == null) {
                    return "";
                }
                uri = data.getData();
//                if (uri.toString().contains("com.miui.gallery.open")) {
//                    uri = getImageContentUri(context, new File(path));
//                }


                if (uri.toString().toLowerCase().startsWith("file:")) {
                    path = uri.toString().substring(7);
                } else {
                    // startPhotoZoom(data.getData(), 150);
                    // startActivityForResult(data, PHOTO_REQUEST_CUT);
                    Uri originalUri = data.getData(); // 获得图片的uri
                    // bm = MediaStore.Images.Media.getBitmap(resolver,
                    // originalUri); //显得到bitmap图片
                    // 这里开始的第二部分，获取图片的路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    // 好像是android多媒体数据库的封装接口，具体的看Android文档
                    @SuppressWarnings("deprecation")
                    Cursor cursor = ((FragmentActivity) context).managedQuery(originalUri, proj, null, null, null);
                    // 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    path = cursor.getString(column_index);// 获得图片的uri
                }
                if(this.type == 1){
                    startPhotoZoom(path);
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data == null) {
                    // ToastUtils.custom("已取消");
                    return "";
                }
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = bundle.getParcelable("data");
                    temppath = BitmapUtil.SavePhoto(photo, 0);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);//
                    // Drawable drawable = new BitmapDrawable(photo);
                    // int x=2+(int)(Math.random()*2000);
                    // studentpath = "/mnt/sdcard/"+"tmp" + str + ".jpg";;
                    // mAddImgIv.setImageBitmap(photo);
                }
                path = temppath;
                break;
            case 4:
                if(data!=null){

                    path = setPicToView(data);
                    return path;
                }
                break;
            default:
                break;
        }
        return null;
    }


    /**
     * 保存裁剪之后的图片数据
     * @param picdata
     */
    private String setPicToView(Intent picdata) {
        String path="";
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");

           /* if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
            path = BitmapUtil.SavePhoto(photo,0);
			//BitmapCompress.getSmallBitmap(fileName, new File(fileName));
           /* *//**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
             * 传到服务器，QQ头像上传采用的方法跟这个类似
             *//*

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来

			tp = new String(Base64Coder.encodeLines(b));


			*//*如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换
			为我们可以用的图片类型就OK啦...吼吼*//*
			Bitmap dBitmap = BitmapFactory.decodeFile(tp);
			Drawable drawable = new BitmapDrawable(dBitmap);*/


        }
        return path;
    }


    /**
     * 裁剪图片方法实现
     * @param
     */
    public void startPhotoZoom(String path) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html

		 */


        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri uri=null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.meiliangzi.app.FileProvider", new File(path));

        }else {
            uri = Uri.fromFile(new File(path));
        }
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
//
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        intent.putExtra("circleCrop", false);

        ( (FragmentActivity) context).startActivityForResult(intent, 4);
    }
    /**
     * 将URI转为图片的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }



}
