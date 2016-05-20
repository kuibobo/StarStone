package apollo.app.wofang.widget.fragment.webcontent;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import java.net.URI;
import java.net.URISyntaxException;

import apollo.app.wofang.activity.WofangWebContentFragmentActivity;
import apollo.app.wofang.bll.Users;
import apollo.data.model.Section;
import apollo.fragments.WebContentFragment;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by Texel on 2015/8/5.
 */

public class WofangWebFragment extends WebContentFragment<Section> {

    private final String TAG = "WofangWebFragment";

    @Override
    protected String getCacheKey() {
        return "WCF";
    }

    @Override
    protected void executeOnLoadDataSuccess(String content) {
        URI uri = null;
        String baseUrl = null;

        try {
            uri = new URI(mUrl);
        } catch (URISyntaxException e) {
        }
        baseUrl = uri.getScheme() + "://" + uri.getAuthority();

        content = Regex.replace(content, "(?s)<header class=\"bot_menu\">.*</header>", "");

        content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
        content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
        content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
        content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");

        mWebView.loadDataWithBaseURL(
                baseUrl, content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }

    @Override
    protected boolean onUrlClick(String url) {
        Log.i(TAG, "onUrlClick:" + url);
        Class<?> fragment  = null;
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else if (url.startsWith("g=user&m=sale&a=add") && Users.getUser() == null) {
            fragment = WofangLoginWebFragment.class;
        } else {
            fragment = BlankWebFragment.class;
        }

        WofangWebContentFragmentActivity.startActivity(super.getActivity(), url, fragment);
        return true;
    }

    @Override
    public void onWebPageFinished(WebView view, String url) {
        Log.i(TAG, view.getOriginalUrl());
    }
}
