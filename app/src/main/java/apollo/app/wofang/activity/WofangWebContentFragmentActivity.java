package apollo.app.wofang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

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

    private String mUrl;

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
        super.setContentView(R.layout.activity_webcontent_fragment);

        i = super.getIntent();

        this.mUrl = i.getStringExtra(BUNDLE_KEY_URL);
        this.mFragment = (Class) i.getSerializableExtra(BUNDLE_KEY_FRAGMENT_CLASS);

        this.initViews();
        this.initListener();
    }

    private void initViews() {
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

    private void initListener() {}
}
