package apollo.app.wofang.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import apollo.activity.NightModeTabActivity;
import apollo.app.wofang.R;
import apollo.widget.AnimationTabHost;

/**
 * Created by Texel on 2015/9/9.
 */
public class MainTabActivity extends NightModeTabActivity implements  CompoundButton.OnCheckedChangeListener {

    public static final String TAB_Home = "TAB_HOME";
    public static final String TAB_TOPIC = "TAB_TOPIC";
    public static final String TAB_VIDEO = "TAB_VIDEO";
    public static final String TAB_PERSON = "TAB_PERSON";

    public static final String TAGET_TAB = "taget_tab";

    private AnimationTabHost mHost;
    private RadioButton mHomeButton;
    private RadioButton mTopicButton;
    private RadioButton mVideoButton;
    private RadioButton mPersonButton;


    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, MainTabActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_maintabs);

        this.initView();
        this.initListener();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            switch(buttonView.getId()) {
                case R.id.radio_home:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_Home);
                    break;

                case R.id.radio_topic:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_TOPIC);
                    break;

                case R.id.radio_video:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_VIDEO);
                    break;

                case R.id.radio_person:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_PERSON);
                    break;
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        String taget_tab = null;

        super.onNewIntent(intent);
        taget_tab = intent.getStringExtra(TAGET_TAB);
        if (MainTabActivity.TAB_Home.equals(taget_tab))
            this.mHomeButton.setChecked(true);
        else if (MainTabActivity.TAB_TOPIC.equals(taget_tab))
            this.mTopicButton.setChecked(true);
        else if (MainTabActivity.TAB_VIDEO.equals(taget_tab))
            this.mVideoButton.setChecked(true);
        else if (MainTabActivity.TAB_PERSON.equals(taget_tab))
            this.mPersonButton.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private TabHost.TabSpec buildTab(String str, Intent intent) {
        return mHost.newTabSpec(str).setContent(intent).setIndicator("", getResources().getDrawable(R.drawable.ic_launcher));
    }

    private void initView() {
        Intent intent = null;

        mHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
        mHost.setOpenAnimation(true);

        mHomeButton = (RadioButton) findViewById(R.id.radio_home);
        mTopicButton = (RadioButton) findViewById(R.id.radio_topic);
        mVideoButton = (RadioButton) findViewById(R.id.radio_video);
        mPersonButton = (RadioButton) findViewById(R.id.radio_setting);
        mHomeButton.setChecked(true);

        intent = new Intent(this, HomeActivity.class);
        mHost.addTab(buildTab(TAB_Home, intent));

        intent = new Intent(this, TopicActivity.class);
        mHost.addTab(buildTab(TAB_TOPIC, intent));

        intent = new Intent(this, VideoActivity.class);
        mHost.addTab(buildTab(TAB_VIDEO, intent));

        intent = new Intent(this, PersongActivity.class);
        mHost.addTab(buildTab(TAB_PERSON, intent));
    }

    private void initListener() {
        mHomeButton.setOnCheckedChangeListener(this);
        mTopicButton.setOnCheckedChangeListener(this);
        mVideoButton.setOnCheckedChangeListener(this);
        mPersonButton.setOnCheckedChangeListener(this);
    }
}
