package apollo.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Texel on 2015/8/28.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected String mUrl;

    protected abstract void initFragment();

    protected abstract void initActionBar();
}
