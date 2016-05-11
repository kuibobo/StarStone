package apollo.app.wofang.activity;

import android.app.Activity;
import android.os.Bundle;

import apollo.app.wofang.R;

/**
 * Created by Texel on 2016/5/11.
 */
public class WebIMActivity extends WofangWebContentFragmentActivity {

    public static void startActivity(Activity activity ) {
        startActivity(activity, "http://qiao.baidu.com/v3/?module=mobile&controller=mobileim&action=index&ucid=7120819&type=z&siteid=4954872", WebIMActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.mLayoutResId = R.layout.activity_webim;

        super.onCreate(savedInstanceState);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initFragment() {
        super.mUrl = "http://qiao.baidu.com/v3/?module=mobile&controller=mobileim&action=index&ucid=7120819&type=z&siteid=4954872";
        super.initFragment();
    }
}
