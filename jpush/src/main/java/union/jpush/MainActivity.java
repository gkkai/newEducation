package union.jpush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagAliasBean.alias = "123";
        tagAliasBean.isAliasAction=true;
        TagAliasOperatorHelper.getInstance().handleAction(MainActivity.this,1, tagAliasBean);
    }



    public void reMove(View view){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        tagAliasBean.alias = "123";
        tagAliasBean.isAliasAction=true;
        TagAliasOperatorHelper.getInstance().handleAction(MainActivity.this,1, tagAliasBean);
    }
}
