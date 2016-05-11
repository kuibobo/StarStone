package apollo.app.wofang.widget.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apollo.app.wofang.activity.WofangWebContentFragmentActivity;
import apollo.data.model.Section;
import apollo.fragments.WebContentFragment;
import apollo.fragments.WebViewBaseFragment;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by Texel on 2015/8/5.
 */

public class WofangWebContentFragment extends WebContentFragment<Section> {

    private final String TAG = "WofangWebContentFragment";


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




}
