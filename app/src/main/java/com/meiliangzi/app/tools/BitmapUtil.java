package com.meiliangzi.app.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class BitmapUtil
{
	/**
	 * ����ͼƬ
	 * 
	 * */
	public static Bitmap zoom(Bitmap bitmap, float zf)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(zf, zf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	/** ����ͼƬ */
	public static Bitmap zoom(Bitmap bitmap, float wf, float hf)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//		ToastUtils.custom(degree+"");
		return degree;
	}
	/**
	 * ͼƬԲ�Ǵ���
	 */
	public static Bitmap getRCB(Bitmap bitmap, float roundRX)
	{
		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstbmp);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		// ��ʾ���ϵ�е�һ���������
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// Rect����RectF�������ǣ�Rect����ĸ�Public����bottom��left��right��top������int�ͣ���RectF����float���͡�
		final RectF rectf = new RectF(rect);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setAntiAlias(true);
		paint.setColor(color);
		canvas.drawRoundRect(rectf, roundRX, roundRX, paint);
		// ȡ������ƽ�������ʾ�ϲ㡣�ο�http://www.cnblogs.com/jacktu/archive/2012/01/02/2310326.html
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return dstbmp;
	}
	
	/**
	 * �Ŵ���СͼƬ
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h)
	{
		Bitmap newbmp = null;
		if (bitmap != null)
		{
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		}
		return newbmp;
	}
	
	// ����ͼƬ
	public static String SavePhoto(Bitmap bitmap, int state)
	{
		// Ҫ��ͼ��洢��sd��֮ǰ����ȼ��һ��sd���Ƿ����?
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
		{
			// ���sd�Ƿ����?
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			return null;
		}
		// ���´������ʵ�ֽ�ͼ���ļ��浽��sdcard/myImage/���ļ����£����Ϊ��?11.jpg��
		FileOutputStream b = null;
		Date date = new Date();
		String str = (String) DateFormat.format("yyyyMMddhhmmss", date);
		// Toast.makeText(MyPlantRecordAty.this, str, 1000).show();
		String fileName = "";
		try
		{
			if (state == 0)
			{
				File file = new File("/sdcard/myImage0/");
				file.mkdirs();// �����ļ���
				fileName = "/sdcard/myImage0/" + str + ".jpg";
				b = new FileOutputStream(fileName);
				bitmap.compress(CompressFormat.JPEG, 100, b);// �����д���ļ�?
				bitmap = BitmapUtil.zoomBitmap(bitmap, 147, 238);
			}
			if (state == 1)
			{
				Log.i("info", "/sdcard/myImages/" + str + ".jpg");
				File file = new File("/sdcard/myImages/");
				file.mkdirs();// �����ļ���
				fileName = "/sdcard/myImages/" + str + ".jpg";
				b = new FileOutputStream(fileName);
				bitmap.compress(CompressFormat.JPEG, 100, b);// �����д���ļ�?
				bitmap = BitmapUtil.zoomBitmap(bitmap, 147, 238);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				b.flush();
				b.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return fileName;
	}
	
	/**
	 * url图片地址转化为Bitmap
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url)
	{
		try
		{
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void savePNG_After(Bitmap bitmap, String name)
	{
		File file = new File(name);
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.PNG, 100, out))
			{
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存图片为JPEG
	 *
	 */
	public static File saveFile(Bitmap bitmap, String path)
	{
		File file = new File(path);
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.JPEG, 100, out))
			{
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return file;
	}

	public static void saveJPGE_After(Context context,Bitmap bitmap, String path)
	{
		File file = new File(path);
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.JPEG, 100, out))
			{
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 文件转字节
     */
	public static byte[] picToByte(String path){
		ByteArrayOutputStream out = null;
		try
		{
			out = new ByteArrayOutputStream();
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			if (bitmap.compress(CompressFormat.JPEG, 100, out))
			{
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 字节转bitmap
	 * @return
     */
	public static Bitmap byteToBitmap(byte[] tyteArray)
	{
		Bitmap bmpout = BitmapFactory.decodeByteArray(tyteArray,0,tyteArray.length);
		return bmpout;
	}

	
	/***
	 * 保存图片在系统相
	 * 
	 * @param context
	 * @param bmp
	 * @throws FileNotFoundException
	 */
	public static void saveImageToGallery(Context context, Bitmap bmp, String Rqcode) throws Exception
	{
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), Rqcode);
		if (!appDir.exists())
		{
			appDir.mkdir();
		} else
		{
			throw new IllegalArgumentException(Rqcode + "已存在");
		}
		String fileName = Rqcode + ".jpg";
		File file = new File(appDir, fileName);
		FileOutputStream fos = new FileOutputStream(file);
		bmp.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
		// 其次把文件插入到系统图库
		MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
		// �?��通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
	}
	
	/**
	 * 得到本地或�?网络上的bitmap url - 网络或�?本地图片的绝对路�?比如:
	 * 
	 * @param imgUrl
	 * @return
	 */
	public static Bitmap getImage(String imgUrl)
	{
		byte[] data = null;
		Bitmap bitmap = null;
		try
		{
			// 建立URL
			URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			InputStream input = conn.getInputStream();// 到这可以直接BitmapFactory.decodeFile也行�?
														// 返回bitmap
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = input.read(buffer)) != -1)
			{
				output.write(buffer, 0, len);
			}
			input.close();
			data = output.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			System.out.println("下载完毕");
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 得到本地或�?网络上的bitmap url - 网络或�?本地图片的绝对路�?比如:
	 */
	public static Bitmap returnBitMap(String url)
	{
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try
		{
			myFileUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}
	public static void compressBitmap(Context context,String path) throws FileNotFoundException,Exception
	{
		File file=new File(path);
		if(file.length()/1024 > 700){
			InputStream inputStream = new FileInputStream(file);
			int size = 10;
			Options options = new Options();
			options.inSampleSize = size;
			Bitmap factory = BitmapFactory.decodeStream(inputStream,null,options);
			saveJPGE_After(context,compressImage(factory),path);
		}
	}
	public static Bitmap compressImage(Bitmap image) throws Exception{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100 && options > 0) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}


	/**
	 * drawable转Bitmap
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}

}
