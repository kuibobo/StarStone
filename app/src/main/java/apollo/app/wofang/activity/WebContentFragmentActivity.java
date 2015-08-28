package apollo.app.wofang.activity;

import android.os.Bundle;

import apollo.activity.BaseShareFragmentActivity;
import apollo.app.wofang.R;

/**
 * Created by Texel on 2015/8/28.
 */
public class WebContentFragmentActivity extends BaseShareFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_webcontent_fragment);

        this.initViews();
        this.initListener();
    }

    private void initViews() {}

    private void initListener() {}
}
