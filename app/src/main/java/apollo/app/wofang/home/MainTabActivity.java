package apollo.app.wofang.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TabHost;

import apollo.activity.DrawerNightModeTabActivity;
import apollo.activity.NightModeTabActivity;
import apollo.app.wofang.R;
import apollo.widget.AnimationTabHost;

/**
 * Created by Texel on 2015/9/9.
 */
public class MainTabActivity extends DrawerNightModeTabActivity implements  CompoundButton.OnCheckedChangeListener {

    public static final String TAB_HOME = "TAB_HOME";
    public static final String TAB_NEWS = "TAB_NEWS";
    public static final String TAB_QUESTION = "TAB_QUESTION";
    public static final String TAB_PERSON = "TAB_PERSON";

    public static final String TAGET_TAB = "taget_tab";

    private AnimationTabHost mHost;
    private RadioButton mHomeButton;
    private RadioButton mNewsButton;
    private RadioButton mQuestionButton;
    private RadioButton mPersonButton;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLeft;

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
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_HOME);
                    break;

                case R.id.radio_news:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_NEWS);
                    break;

                case R.id.radio_question:
                    this.mHost.setCurrentTabByTag(MainTabActivity.TAB_QUESTION);
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
        if (MainTabActivity.TAB_HOME.equals(taget_tab))
            this.mHomeButton.setChecked(true);
        else if (MainTabActivity.TAB_NEWS.equals(taget_tab))
            this.mNewsButton.setChecked(true);
        else if (MainTabActivity.TAB_QUESTION.equals(taget_tab))
            this.mQuestionButton.setChecked(true);
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

        mDrawerLayout = (DrawerLayout) super.findViewById(R.id.layout_main);
        mDrawerLeft = (LinearLayout) super.findViewById(R.id.layout_drawer_left);

        mHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
        mHost.setOpenAnimation(true);

        mHomeButton = (RadioButton) findViewById(R.id.radio_home);
        mNewsButton = (RadioButton) findViewById(R.id.radio_news);
        mQuestionButton = (RadioButton) findViewById(R.id.radio_question);
        mPersonButton = (RadioButton) findViewById(R.id.radio_person);
        mHomeButton.setChecked(true);

        intent = new Intent(this, HomeActivity.class);
        mHost.addTab(buildTab(TAB_HOME, intent));

        intent = new Intent(this, TopicActivity.class);
        mHost.addTab(buildTab(TAB_NEWS, intent));

        intent = new Intent(this, VideoActivity.class);
        mHost.addTab(buildTab(TAB_QUESTION, intent));

        intent = new Intent(this, PersonActivity.class);
        mHost.addTab(buildTab(TAB_PERSON, intent));
    }

    private void initListener() {
        mHomeButton.setOnCheckedChangeListener(this);
        mNewsButton.setOnCheckedChangeListener(this);
        mQuestionButton.setOnCheckedChangeListener(this);
        mPersonButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void openLeftDrawer() {
        mDrawerLayout.openDrawer(mDrawerLeft);
    }

    @Override
    public void openRightDrawer() {

    }
}
