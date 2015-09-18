package apollo.app.wofang.home;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import apollo.app.wofang.R;
import apollo.app.wofang.activity.WofangWebContentFragmentActivity;

/**
 * Created by kuibo on 2015/9/9.
 */
public class TopicActivity extends WofangWebContentFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.mLayoutResId = R.layout.activity_topic;

        super.onCreate(savedInstanceState);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initFragment() {
        super.mUrl = "http://m.wofang.com/asks/";
        super.initFragment();
    }

}
