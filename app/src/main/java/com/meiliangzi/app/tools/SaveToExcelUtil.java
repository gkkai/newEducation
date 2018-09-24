package com.meiliangzi.app.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.meiliangzi.app.model.bean.QuerySendacarinfoBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by kk on 2018/9/20.
 */

public class SaveToExcelUtil {
    private WritableWorkbook wwb;
    private String excelPath;
    private File excelFile;
    private Activity activity;

    public SaveToExcelUtil(Activity activity, String excelPath) {
        this.excelPath = excelPath;
        this.activity = activity;
        //excelFile = new File(excelPath);
       // createExcel(excelFile);

    }

    // 创建excel表.
    public void createExcel(File file) {
        WritableSheet ws = null;
        excelFile=file;
        try {
            if (!file.exists()) {
                wwb = Workbook.createWorkbook(file);

                ws = wwb.createSheet("sheet1", 0);

                // 在指定单元格插入数据
                Label lbl1 = new Label(0, 0, "日期       ");
                Label lbl2 = new Label(1, 0, "车号        ");
                Label lbl3 = new Label(2, 0, "驾驶员        ");
                Label lbl4 = new Label(3, 0, "用车部门          ");
                Label lbl5 = new Label(4, 0, "使用人         ");
                Label lbl6 = new Label(5, 0, "起点    ");
                Label lbl7 = new Label(6, 0, "终点    ");
                Label lbl8 = new Label(7, 0, "起始里程      ");
                Label lbl9 = new Label(8, 0, "终止里程      ");
                Label lbl10 = new Label(9, 0, "出车时间    ");
                Label lbl11= new Label(10, 0, "返回时间   ");
                Label lbl12 = new Label(11, 0, "行驶里程   ");
                Label lbl13 = new Label(12, 0, "备注      ");


                ws.addCell(lbl1);
                ws.addCell(lbl2);
                ws.addCell(lbl3);
                ws.addCell(lbl4);
                ws.addCell(lbl5);
                ws.addCell(lbl6);
                ws.addCell(lbl7);
                ws.addCell(lbl8);
                ws.addCell(lbl9);
                ws.addCell(lbl10);
                ws.addCell(lbl11);
                ws.addCell(lbl12);
                ws.addCell(lbl13);

                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将数据存入到Excel表中
    public void writeToExcel(List<QuerySendacarinfoBean.Data> list) {

            try {

                Workbook oldWwb = Workbook.getWorkbook(excelFile);
                wwb = Workbook.createWorkbook(excelFile, oldWwb);
                WritableSheet ws = wwb.getSheet(0);
                //设置字体为Arial，30号，加粗
                CellView cellView = new CellView();
                cellView.setAutosize(true); //设置自动大小
                ws.setColumnView(0, cellView);//根据内容自动设置列宽
                for(int i=0;i<list.size();i++){

                    // 当前行数
                    int row = ws.getRows();

                Label lab1 = new Label(0, row, list.get(i).getStartAt() + "       ");
                Label lab2 = new Label(1, row, list.get(i).getPlateNumber() + "         ");
                Label lab3 = new Label(2, row, list.get(i).getDriverName() + "           ");
                Label lab4 = new Label(3, row, list.get(i).getDepartmentName() + "              ");
                    Label lab5 = new Label(4, row, list.get(i).getProposer() + "          ");
                    Label lab6 = new Label(5, row, list.get(i).getStart() + "            ");
                    Label lab7 = new Label(6, row, list.get(i).getEnd() + "           ");
                    Label lab8 = new Label(7, row, list.get(i).getInitialMileage() + "KM     ");
                    Label lab9 = new Label(8, row, list.get(i).getReturnMileage() + "KM     ");
                    Label lab10 = new Label(9, row, list.get(i).getStartAt() + "     ");
                    Label lab11= new Label(10, row, list.get(i).getEndAt() + "     ");
                    Label lab12 = new Label(11, row, list.get(i).getMileage() + "KM    ");
                    Label lab13= new Label(12, row, list.get(i).getRemarks() + "     ");

                    ws.addCell(lab1);
                    ws.setColumnView(1, cellView);//根据内容自动设置列宽
                    ws.addCell(lab2);
                    ws.setColumnView(2, cellView);//根据内容自动设置列宽
                    ws.addCell(lab3);
                    ws.setColumnView(3, cellView);//根据内容自动设置列宽
                    ws.addCell(lab4);
                    ws.setColumnView(4, cellView);//根据内容自动设置列宽
                    ws.addCell(lab5);
                    ws.setColumnView(5, cellView);//根据内容自动设置列宽
                    ws.addCell(lab6);
                    ws.setColumnView(6, cellView);//根据内容自动设置列宽
                    ws.addCell(lab7);
                    ws.setColumnView(7, cellView);//根据内容自动设置列宽
                    ws.addCell(lab8);
                    ws.setColumnView(8, cellView);//根据内容自动设置列宽
                    ws.addCell(lab9);
                    ws.setColumnView(9, cellView);//根据内容自动设置列宽
                    ws.addCell(lab10);
                    ws.setColumnView(10, cellView);//根据内容自动设置列宽
                    ws.addCell(lab11);
                    ws.setColumnView(11, cellView);//根据内容自动设置列宽
                    ws.addCell(lab12);
                    ws.setColumnView(12, cellView);//根据内容自动设置列宽
                    ws.addCell(lab13);
                    ws.setColumnView(13, cellView);//根据内容自动设置列宽

                }
                // 从内存中写入文件中,只能刷一次.
                wwb.write();
                wwb.close();

                shareFile(activity,excelFile);

            } catch (Exception e) {
                e.printStackTrace();
            }


    }
    // 調用系統方法分享文件
    public static void shareFile(Context context, File file) {
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            ArrayList<Uri> imageUris = new ArrayList<Uri>();
           Uri uri=Uri.fromFile(file);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Uri contentUri = FileProvider.getUriForFile(context, "com.meiliangzi.app.FileProvider", file);
                uri=contentUri;
            }else {
                //Uri contentUri = FileProvider.getUriForFile(this, "com.meiliangzi.app.FileProvider", sdcardTempFile);
            }

            share.putExtra(Intent.EXTRA_STREAM,uri );
            share.setType(getMimeType(file.getAbsolutePath()));//此处可发送多种文件
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(share, "分享文件"));
        } else {
            ToastUtils.show("分享文件不存在");
        }
    }
    // 根据文件后缀名获得对应的MIME类型。
    private static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "*/*";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

}
