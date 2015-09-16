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
    protected void sendRequestData() {
        String url = super.getEntity().getUrl();

        sendRequestData(url);
    }

    @Override
    protected boolean onUrlClick(String url) {
        Log.i(TAG, "onUrlClick:" + url);

        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
        this.sendRequestData(url);
        return true;
    }
}
