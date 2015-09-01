package apollo.app.wofang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import apollo.activity.BaseShareFragmentActivity;
import apollo.app.wofang.R;
import apollo.app.wofang.widget.WofangWebContentFragment;

/**
 * Created by Texel on 2015/8/28.
 */
public class WebContentFragmentActivity extends BaseShareFragmentActivity {

    private static final String TAG = WebContentFragmentActivity.class.getName();

    public static final String BUNDLE_KEY_URL = "URL";

    private String mUrl;

    public static void startActivity(Activity activity, String url) {
        Intent i = null;

        i = new Intent(activity, WebContentFragmentActivity.class);
        i.putExtra(BUNDLE_KEY_URL, url);
        activity.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent data = null;

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_webcontent_fragment);

        data = super.getIntent();
        this.mUrl = data.getStringExtra(BUNDLE_KEY_URL);

        this.initViews();
        this.initListener();
    }

    private void initViews() {
        Fragment fragment = new WofangWebContentFragment();
        Intent data = super.getIntent();

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
