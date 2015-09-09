package apollo.app.wofang.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import apollo.activity.NightModeTabActivity;
import apollo.widget.AnimationTabHost;

/**
 * Created by Texel on 2015/9/9.
 */
public class MainTabActivity extends NightModeTabActivity implements  CompoundButton.OnCheckedChangeListener {

    public static final String TAB_MAIN = "TAB_MAIN";
    public static final String TAB_TOPIC = "TAB_TOPIC";
    public static final String TAB_VIDEO = "TAB_VIDEO";
    public static final String TAB_SETTING = "TAB_SETTING";

    public static final String TAGET_TAB = "taget_tab";

    private AnimationTabHost mHost;
    private RadioButton mMainButton;
    private RadioButton mTopicButton;
    private RadioButton mVideoButton;
    private RadioButton mSettingButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initView();
        this.initListener();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private TabHost.TabSpec buildTab(String str, Intent intent) {
        return mHost.newTabSpec(str).setContent(intent).setIndicator("", getResources().getDrawable(R.drawable.icon));
    }

    private void initView() {
        Intent intent = null;

        mHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
        mHost.setOpenAnimation(true);

        mMainButton = (RadioButton) findViewById(R.id.radio_main);
        mTopicButton = (RadioButton) findViewById(R.id.radio_topic);
        mVideoButton = (RadioButton) findViewById(R.id.radio_video);
        mSettingButton = (RadioButton) findViewById(R.id.radio_setting);

        intent = new Intent(this, MainActivity.class);
        mHost.addTab(buildTab(TAB_MAIN, intent));

        intent = new Intent(this, TopicActivity.class);
        mHost.addTab(buildTab(TAB_TOPIC, intent));

        intent = new Intent(this, VideoActivity.class);
        mHost.addTab(buildTab(TAB_VIDEO, intent));

        intent = new Intent(this, SettingActivity.class);
        mHost.addTab(buildTab(TAB_SETTING, intent));
    }

    private void initListener() {
        mMainButton.setOnCheckedChangeListener(this);
        mTopicButton.setOnCheckedChangeListener(this);
        mVideoButton.setOnCheckedChangeListener(this);
        mSettingButton.setOnCheckedChangeListener(this);
    }

    private void selectTab() {
        String taget_tab = null;

        taget_tab = getIntent().getStringExtra(TAGET_TAB);
        if (TextUtils.isEmpty(taget_tab)) {

        }
    }
}
