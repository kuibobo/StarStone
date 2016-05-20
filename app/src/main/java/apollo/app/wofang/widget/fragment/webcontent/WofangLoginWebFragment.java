package apollo.app.wofang.widget.fragment.webcontent;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import apollo.fragments.WebContentFragment;

/**
 * Created by Texel on 2016/5/20.
 */
public class WofangLoginWebFragment extends WebContentFragment {
    private final String TAG = "WofangLoginWebFragment";

    @Override
    public void onWebPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.e(TAG, "Cookies = " + CookieStr);
    }
}
