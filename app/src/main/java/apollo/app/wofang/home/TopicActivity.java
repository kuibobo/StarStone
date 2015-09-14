package apollo.app.wofang.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import apollo.activity.NightModeActivity;
import apollo.activity.NightModeFragmentActivity;
import apollo.app.wofang.R;
import apollo.app.wofang.activity.WofangWebContentFragmentActivity;
import apollo.app.wofang.widget.fragment.WofangWebContentFragment;

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
