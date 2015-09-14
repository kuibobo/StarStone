package apollo.activity;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Texel on 2015/8/28.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected String mUrl;

    protected abstract void initFragment();
}
