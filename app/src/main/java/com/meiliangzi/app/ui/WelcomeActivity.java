package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.WelcomeAnimatorListener;
import com.meiliangzi.app.ui.base.BaseActivity;


public class WelcomeActivity extends BaseActivity {

    private ImageView loadingItem;
    private Animation welcomeAnimation;

    private SQLHelper helper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_welcome);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        if (!PreferManager.getUserId().isEmpty())
        {
            PreferManager.saveTimeStart(System.currentTimeMillis()+"");
        }
        if(helper ==null){
            helper = new SQLHelper(this);
        }
    }

    @Override
    protected void findWidgets() {
        loadingItem = findView(R.id.iv_welcome_loading_item);
    }

    @Override
    protected void initComponent() {
        welcomeAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_loading);
        welcomeAnimation.setAnimationListener(new WelcomeAnimatorListener(this));
        if(!"".equals(PreferManager.getUserId())){
            //TODO 获取部门列表
            //ProxyUtils.getHttpProxy().querydepartmentusernumber(this, Integer.valueOf(PreferManager.getUserId()));

        }


    }

    @Override
    protected void excuteOther() {
        loadingItem.startAnimation(welcomeAnimation);
    }
    private void getmentusernumber(DepartmentuserNumberBean data){
        MyApplication.numberBean=data;
        // TODO Auto-generated method stub
       /* SQLiteDatabase database = null;
        long id = -1;
        int count = 0;
        Cursor cursor = null;
        try {
            database = helper.getWritableDatabase();
            for(int i=0;i<data.getData().size();i++){
                for(int j=0;j<data.getData().get(i).getDepartmentUser().size();j++){
                    String sql = "select count(1) as count from "+ SQLHelper.TABLE_NAME+" where "+ SQLHelper.ID+"=" + data.getData().get(i).getDepartmentUser().get(j).getId(); // 定义SQL
                    cursor = database.rawQuery(sql, null);
                    while (cursor.moveToNext())
                    {
                        count = cursor.getInt(cursor.getColumnIndex("count"));
                    }
                    Log.i("grage","count==="+count);
                    if (count==0){
                        ContentValues values = new ContentValues();
                        values.put(SQLHelper.NAME, data.getData().get(i).getDepartmentUser().get(j).getNickName());
                        values.put(SQLHelper.ID, data.getData().get(i).getDepartmentUser().get(j).getId());
                        values.put(SQLHelper.IMAGE, data.getData().get(i).getDepartmentUser().get(j).getUserHead());
                        id = database.insert(SQLHelper.TABLE_NAME, null, values);
                    }
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
            //helper.onUpgrade(database,1,2);
            if (database != null) {
                helper.onUpgrade(database,1,2);
                database.close();
            }
        } finally {
            if (database != null) {
                database.close();
            }
//			if (cursor!=null){
//				cursor.close();
//			}*/

    }
}
