package apollo.app.wofang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import apollo.activity.BaseShareFragmentActivity;
import apollo.app.wofang.R;
import apollo.app.wofang.widget.fragment.WofangWebContentFragment;

/**
 * Created by Texel on 2015/8/28.
 */
public class WofangWebContentFragmentActivity extends BaseShareFragmentActivity {

    private static final String TAG = WofangWebContentFragmentActivity.class.getName();
    public static final String BUNDLE_KEY_URL = "URL";
    public static final String BUNDLE_KEY_FRAGMENT_CLASS = "FRAGMENT_CLASS";

    private Class<?> mFragment = null;

    protected int mLayoutResId = R.layout.activity_webcontent_fragment;

    public static void startActivity(Activity activity, String url) {
        startActivity(activity, url, WofangWebContentFragment.class);
    }

    public static void startActivity(Activity activity, String url, Class<?> fragment) {
        Intent i = null;
        Bundle b = null;

        b = new Bundle();
        b.putString(BUNDLE_KEY_URL, url);
        b.putSerializable(BUNDLE_KEY_FRAGMENT_CLASS, fragment);

        i = new Intent(activity, WofangWebContentFragmentActivity.class);
        i.putExtras(b);

        activity.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent i = null;

        super.onCreate(savedInstanceState);
        super.setContentView(mLayoutResId);

        i = super.getIntent();

        this.mUrl = i.getStringExtra(BUNDLE_KEY_URL);
        this.mFragment = (Class) i.getSerializableExtra(BUNDLE_KEY_FRAGMENT_CLASS);

        this.initFragment();
        this.initActionBar();
    }

    @Override
    protected void initFragment() {
        Fragment fragment = null;

        try { fragment = (Fragment)this.mFragment.newInstance();} catch (Exception ex) {}
        if (fragment == null)
            fragment = new WofangWebContentFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_URL, this.mUrl);
        fragment.setArguments(args);

        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.frame_content, fragment, TAG);
        trans.commit();
    }

    protected void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.main_top_toolbar_icon);

        super.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_menu_search).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_search)
                        .colorRes(R.color.toolbar_text_color)
                        .sizeDp(20)
        );

        menu.findItem(R.id.action_menu_favor).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_star)
                        .colorRes(R.color.toolbar_text_color)
                        .actionBarSize()
        );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (!this.isFinishing())
                    this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
