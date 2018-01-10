package com.meiliangzi.app.ui.view.train;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.FileDownloadBean;
import com.meiliangzi.app.model.bean.MyClassInfoBean;
import com.meiliangzi.app.model.bean.RecentOpenClassBean;
import com.meiliangzi.app.model.bean.StudyCenternBean;
import com.meiliangzi.app.model.bean.TrainQueryDataBean;
import com.meiliangzi.app.tools.FileUtil;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.widget.XListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import butterknife.BindView;
import us.zoom.androidlib.util.CaptionStyleCompat;
import us.zoom.androidlib.util.FileUtils;
import xiaobo.com.video.Utils;



public class TrainActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tv_title_train)
    TextView title;
    private  MyDialog myDialog;
    BaseTrainAdapter<RecentOpenClassBean.DataBean> recentadapter;//近期开班
    BaseTrainAdapter<StudyCenternBean.DataBean> studycenternadapter;//学习中心
    BaseTrainAdapter<TrainQueryDataBean.Databena> querydata;//信息查询
    BaseTrainAdapter<FileDownloadBean.DataBean> dowmloadadapter;//资料下载
    BaseTrainAdapter<MyClassInfoBean.Databean> myclassinfoadapter;//我的课程

    Intent intent;
    int currentPage = 1;
    int pageSize = 10;
    String type;
    OkhttpUtils okhttp;
    String rootpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_train);
        rootpath = getpath();
        okhttp = new OkhttpUtils(this);

    }

    private String getpath() {
        boolean hsaSDscard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hsaSDscard) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return Environment.getDownloadCacheDirectory().getPath();
        }


    }

    @Override
    protected void findWidgets() {
        intent = getIntent();
        if(intent.getIntExtra("Reciver",0)==1000){
            finish();
        }
        type = intent.getStringExtra("type");
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        listView.setXListViewListener(this);
        if ("recentopenclass".equals(type)) {
            title.setText("近期开班");
            recentadapter = new BaseTrainAdapter<RecentOpenClassBean.DataBean>(this, listView, R.layout.item_train_) {
                @Override
                public void convert(BaseViewHolder helper, final RecentOpenClassBean.DataBean item) {
                    helper.setImageByUrl(R.id.ivImg, item.getImage(), R.mipmap.defaule, R.mipmap.defaule);
                    helper.setText(R.id.tvTitle, item.getTitle());
                    helper.setText(R.id.tvTime, item.getStart_at());

                }

            };
            listView.setAdapter(recentadapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent studycenterintent = new Intent(TrainActivity.this, TrainClassDataelsActivity.class);
                    studycenterintent.putExtra("trainingId", recentadapter.getItem(position - 1).getId());
                    TrainActivity.this.startActivity(studycenterintent);

                }
            });
        } else if ("studycenter".equals(intent.getStringExtra("type"))) {
            title.setText("学习中心");
            studycenternadapter = new BaseTrainAdapter<StudyCenternBean.DataBean>(this, listView, R.layout.item_train_studycenter) {
                @Override
                public void convert(BaseViewHolder helper, final StudyCenternBean.DataBean item) {
                    helper.setImageByUrl(R.id.ivImg, item.getImage(), R.mipmap.test_artical, R.mipmap.test_artical);
                    helper.setText(R.id.tvTitle, item.getVideo_name());
                    helper.setText(R.id.tvTime, item.getVideo_time());
                    helper.setText(R.id.tvlecturer, "讲师  :" + item.getVideo_teacher());

                }
            };
            listView.setAdapter(studycenternadapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent studycenterintent = new Intent(TrainActivity.this, TrainClassStudyActivity.class);
                    studycenterintent.putExtra("videoId", studycenternadapter.getItem(position - 1).getId());
                    TrainActivity.this.startActivity(studycenterintent);
                }
            });
        } else if ("dataquery".equals(intent.getStringExtra("type"))) {

            title.setText("信息查询");
            querydata = new BaseTrainAdapter<TrainQueryDataBean.Databena>(this, listView, R.layout.train_querydata) {
                @Override
                public void convert(BaseViewHolder helper, TrainQueryDataBean.Databena item) {
                    helper.setText(R.id.tv_query_tiele, item.getName());
                    helper.setText(R.id.tv_query_create_at, item.getCreate_at());

                }
            };
            listView.setAdapter(querydata);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(TrainActivity.this, QueryDataActivity.class);
                    intent.putExtra("studyId", querydata.getItem(position - 1).getId());
                    intent.putExtra("type", "querydata");
                    startActivity(intent);
                    finish();
                    //TODO 返回查询数据

                }
            });

        } else if ("datadownload".equals(intent.getStringExtra("type"))) {
            title.setText("资料下载");
            dowmloadadapter = new BaseTrainAdapter<FileDownloadBean.DataBean>(this, listView, R.layout.item_train_filedownload) {
                @Override
                public void convert(BaseViewHolder helper, final FileDownloadBean.DataBean item) {
                    String prefix = item.getFile_type();
                    if("word".contains(prefix)){
                        helper.setImageByUrl(R.id.image_file_type,null,R.mipmap.word,R.mipmap.word);
                    }
                    helper.setText(R.id.tx_enclosure_name, item.getEnclosure_name());
                    helper.getView(R.id.tx_filedownload).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 点击下载并且打开
                            downloadfile(item.getEnclosure_address());

                        }
                    });


                }
            };
            listView.setAdapter(dowmloadadapter);
        } else if ("myclassinfo".equals(type)) {
            title.setText("我的课程");
            myclassinfoadapter = new BaseTrainAdapter<MyClassInfoBean.Databean>(this, listView, R.layout.item_train_myclassinfo) {
                @Override
                public void convert(BaseViewHolder helper, MyClassInfoBean.Databean item) {
                    helper.setText(R.id.tv_train_myclassinfo_repair_title, item.getTitle());
                    helper.setText(R.id.tv_train_myclassinfo_repair_start_at, "开班日期：" + item.getStart_at());
                    helper.setText(R.id.tv_train_myclassinfo_repair_scroe, "补考成绩：" + item.getRepair_scroe());
                    helper.setText(R.id.tv_train_myclassinfo_scroe, "初考成绩：" + item.getScroe());

                }
            };


            listView.setAdapter(myclassinfoadapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO 跳转报名界面
                    Intent intent =new Intent(TrainActivity.this,TrainClassDataelsActivity.class);
                    intent.putExtra("trainingId",myclassinfoadapter.getItem(position-1).getId());
                    startActivity(intent);
                    finish();
                }
            });
        }


    }


    //TODO 开始下载
    private void downloadfile(final String uri) {
        okhttp.download(uri, "/storage/emulated/0/Train", new OkhttpUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String url) {
                //先判断是不是zip文件
                String prefix = url.substring(url.lastIndexOf(".") + 1);
                String path = url.substring(0, url.lastIndexOf("."));
                if ("zip".contains(prefix)) {
                    try {
                        MyApplication.zipfilelist.clear();
                        upZipFile(url, path + "/unzip");
                        Intent intent = new Intent(TrainActivity.this, ZipFileShowActivity.class);
                        intent.putExtra("path", path + "/unzip");
                        startActivity(intent);
                        //finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    MyApplication.clsaaname=TrainActivity.class;
                    openFile(TrainActivity.this,url);

                }


            }

            @Override
            public void onDownloading(int progress) {
                //progressBar.setProgress(progress);


            }

            @Override
            public void onDownloadFailed() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show("下载失败，请查看网络是否链接异常");
                    }
                });


            }
        });

    }

    /*
    * 这个是解压ZIP格式文件的方法
    *
    * @zipFileName：是传进来你要解压的文件路径，包括文件的名字；
    *
    * @outputDirectory:选择你要保存的路劲；
    *
    */
    private void unzip(String zipFileName, String outputDirectory)
            throws Exception {
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry z;
        String name = "";
        String extractedFile = "";
        int counter = 0;

        while ((z = in.getNextEntry()) != null) {
            name = z.getName();
            if (z.isDirectory()) {
                // get the folder name of the widget
                name = name.substring(0, name.length() - 1);
                File folder = new File(outputDirectory + File.separator + name);
                folder.mkdirs();
                if (counter == 0) {
                    extractedFile = folder.toString();
                }
                counter++;
            } else {
                File file = new File(outputDirectory + File.separator + name);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int ch;
                byte[] buffer = new byte[1024];
                // read (ch) bytes into buffer
                while ((ch = in.read(buffer)) != -1) {
                    // write (ch) byte from buffer at the position 0
                    out.write(buffer, 0, ch);
                    out.flush();
                }
                out.close();


            }

            in.close();


        }
    }

    private static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }


    /**
     * 含子目录的文件压缩
     *
     * @throws Exception
     */
    // 第一个参数就是需要解压的文件，第二个就是解压的目录
    public boolean upZipFile(String zipFile, String folderPath) {
        ZipFile zfile = null;
        try {
            // 转码为GBK格式，支持中文
            zfile = new ZipFile(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            // 列举的压缩文件里面的各个文件，判断是否为目录
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                dirstr.trim();
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = null;
            FileOutputStream fos = null;
            // ze.getName()会返回 script/start.script这样的，是为了返回实体的File
            File realFile = getRealFileName(folderPath, ze.getName());


            try {
                fos = new FileOutputStream(realFile);
            } catch (FileNotFoundException e) {
                return false;
            }
            os = new BufferedOutputStream(fos);
            InputStream is = null;
            try {
                is = new BufferedInputStream(zfile.getInputStream(ze));
            } catch (IOException e) {
                return false;
            }
            int readLen = 0;
            // 进行一些内容复制操作
            try {
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
            } catch (IOException e) {
                return false;
            }
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                return false;
            }
        }
        try {
            zfile.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public File getRealFileName(String baseDir, String absFileName) {

        absFileName = absFileName.replace("\\", "/");
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                ret = new File(ret, substr);

            }

            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            ret = new File(ret, substr);
            return ret;
        } else {
            ret = new File(ret, absFileName);
        }


        return ret;
    }

    @Override
    protected void initComponent() {
        if ("recentopenclass".equals(type)) {
            ProxyUtils.getHttpProxy().recentclasslist(this, currentPage, pageSize, Integer.valueOf(PreferManager.getUserId()));
        } else if ("studycenter".equals(intent.getStringExtra("type"))) {
            ProxyUtils.getHttpProxy().qualityvideolist(this, currentPage, pageSize);
        } else if ("dataquery".equals(intent.getStringExtra("type"))) {
            int userId = Integer.valueOf(PreferManager.getUserId());
            String name = intent.getStringExtra("name");
            String cardId = intent.getStringExtra("id_card");
            //Todo 开始进行查询
            ProxyUtils.getHttpProxy().queryuserstudylist(this, userId, name, cardId);
        } else if ("datadownload".equals(type)) {
            //Todo 查询资料列表
            ProxyUtils.getHttpProxy().filedownload(this);
        } else if ("myclassinfo".equals(type)) {
            //Todo 查询我的课程
            ProxyUtils.getHttpProxy().usercurriculum(this, Integer.valueOf(PreferManager.getUserId()));
        }

    }

    protected void getusercurriculum(MyClassInfoBean data) {
        myclassinfoadapter.setDatas(data.getData());

    }

    protected void getfiledownloadt(FileDownloadBean data) {
        dowmloadadapter.setDatas(data.getData());

    }

    protected void getqueryuserstudylist(TrainQueryDataBean data) {
        querydata.setDatas(data.getData());

    }

    protected void getstudylists(StudyCenternBean data) {

        if (currentPage == 1) {
            studycenternadapter.pullRefresh(data.getData());
        } else {
            studycenternadapter.pullLoad(data.getData());
        }


    }

    protected void getrecentclasslist(RecentOpenClassBean data) {

        if (currentPage == 1) {
            recentadapter.pullRefresh(data.getData());
        } else {
            recentadapter.pullLoad(data.getData());
        }


    }

    @Override
    public void onRefresh() {
        currentPage = 1;

        initComponent();
    }

    @Override
    public void onLoadMore() {

        currentPage++;
        initComponent();
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_singup:
                //TODO 报名参与

                break;

        }


    }


   private boolean openFile(final Activity context, String path)
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
        intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);

        File file = new File(path);
        if (file == null || !file.exists())
        {                        return false;
        }

        Uri uri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
