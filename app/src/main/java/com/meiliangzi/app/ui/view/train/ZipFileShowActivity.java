package com.meiliangzi.app.ui.view.train;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.FileDownloadBean;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.widget.XListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZipFileShowActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    private  MyDialog myDialog;
    BaseTrainAdapter<String> zipapter;//资料下载
    /** Called when the activity is first created. */
    private List<String> items = null;//存放名称
    private List<String> paths = null;//存放路径
    private String rootPath = "/";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_zip_file_show);
        rootPath=getIntent().getStringExtra("path");
        onCreateView(R.layout.activity_zip_file_show);
    }

    @Override
    protected void findWidgets() {
        intent = getIntent();
        if(intent.getIntExtra("Reciver",0)==1000){
            finish();
        }
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        zipapter=new BaseTrainAdapter<String>(this,listView,R.layout.item_train_filedownload) {
            @Override
            public void convert(BaseViewHolder helper, final String item) {

                    }
            public void convert(BaseViewHolder helper, final String item,int position) {
                if(paths!=null&&paths.size()!=0){
                    String path = paths.get(position);
                    final File file = new File(path);



//如果是文件夹就继续分解
                    if(file.isDirectory()){
                        helper.setText(R.id.tx_enclosure_name,file.getName());
                        helper.setImageByUrl(R.id.image_file_type,null,R.mipmap.file,R.mipmap.file);
                        helper.showOrGoneView(R.id.tx_filedownload,false);
                    }else {
                        String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        /*if("doc".contains(prefix)||"docx".contains(prefix)||"txt".contains(prefix)){
                            helper.setImageByUrl(R.id.image_file_type,null,R.mipmap.word,R.mipmap.word);
                        }*/
                        helper.setImageByUrl(R.id.image_file_type,null,R.mipmap.word,R.mipmap.word);

                        helper.setText(R.id.tx_enclosure_name,file.getName());
                        helper.setText(R.id.tx_filedownload,"打开");
                        helper.showOrGoneView(R.id.tx_filedownload,true);
                        helper.setOnClickListener(R.id.tx_filedownload, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //ShowFileActivity.show(ZipFileShowActivity.this,file.getPath());
                                String path=file.getPath();
                                MyApplication.clsaaname=ZipFileShowActivity.class;
                                openFile(ZipFileShowActivity.this,path);

                            }
                        });
                    }
                }
                }

                };
        listView.setAdapter(zipapter);

        getFileDir(rootPath);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = paths.get(position-1);
                File file = new File(path);
                //如果是文件夹就继续分解
                if(file.isDirectory()){
                    getFileDir(path);
                }else {

                //TODO
                    //ShowFileActivity.show(ZipFileShowActivity.this,file.getPath());
                   /* Intent intent=new Intent(ZipFileShowActivity.this,DocFileReadActivity.class);
                    intent.putExtra("path",file.getPath());
                    startActivity(intent);*/
                    MyApplication.clsaaname=ZipFileShowActivity.class;
                    openFile(ZipFileShowActivity.this,path);
                }

            }
        });


    }


    @Override
    protected void initComponent() {

    }
    public void getFileDir(String filePath) {
        try{
            items = new ArrayList<String>();
            paths = new ArrayList<String>();
            File f = new File(filePath);
            File[] files = f.listFiles();// 列出所有文件
            // 将所有文件存入list中
            if(files != null){
                int count = files.length;// 文件个数
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    if(file.getName().startsWith("._")||file.getName().startsWith(".")||file.getName().startsWith("_")){
                        //realFile.delete();
                    }else {
                        items.add(file.getName());
                        paths.add(file.getPath());    // MyApplication.zipfilelist.add(zipfiel);
                    }

                }
            }

           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items);
            this.setListAdapter(adapter);*/
            //listView.setAdapter();


           zipapter.setDatas(items);
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }
    private boolean openFile(final Context context, String path)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.READ_ONLY); // 打开模式
        bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        bundle.putString(WpsModel.THIRD_PACKAGE, "com.android.settings"); // 第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
        // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
        bundle.putBoolean(WpsModel.CLEAR_BUFFER, true);
        //bundle.putBoolean(CLEAR_FILE, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setAction("android.intent.action.EDIT");
        intent.setType("application/pdf");
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File file = new File(path);
        if (file == null || !file.exists())
        {                        return false;
        }

        Uri uri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.meiliangzi.app.FileProvider", file);
            //intent.setDataAndType(contentUri, "file/*");
            intent.setData(contentUri);
        } else {
            //intent.setDataAndType(Uri.fromFile(file,"file/*"));

            intent.setData(uri);
        }
        intent.putExtras(bundle);
        if(isAvilible(context,"cn.wps.moffice_eng")){
            context.startActivity(intent);
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // myDialog.show();
                    goToMarket(context,"cn.wps.moffice_eng");
                }
            });

        }
       /* try
        {
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            e.printStackTrace();

            return false;
        }*/

        return true;
    }
    /**检查手机是否安装应用包名
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);


    }
    public  void goToMarket(final Context context, final String packageName) {
        myDialog = new MyDialog(context);

        myDialog.setMessage("您手机未安装WPS，请确认安装");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }


            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();

                myDialog.dismiss();
            }
        });


        myDialog.show();

    }


}
