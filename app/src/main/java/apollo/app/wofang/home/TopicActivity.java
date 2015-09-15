package apollo.app.wofang.home;

import android.os.Bundle;

import apollo.app.wofang.R;
import apollo.app.wofang.activity.WofangWebContentFragmentActivity;

/**
 * Created by kuibo on 2015/9/9.
 */
public class TopicActivity extends WofangWebContentFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_topic);
    }

    @Override
    protected void initFragment() {
        super.mUrl = "http://m.wofang.com/asks/";
        super.initFragment();
    }
}
