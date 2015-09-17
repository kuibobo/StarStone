package apollo.app.wofang.widget.fragment;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import apollo.app.wofang.activity.WofangWebContentFragmentActivity;

/**
 * Created by Texel on 2015/9/1.
 */
public class BlankContentFragment extends WofangWebContentFragment {
    private final String TAG = this.getClass().getName();

    @Override
    protected boolean onUrlClick(String url) {
        if (super.onUrlClick(url) == false)
            this.sendRequestData(url);

        return true;
    }
}
