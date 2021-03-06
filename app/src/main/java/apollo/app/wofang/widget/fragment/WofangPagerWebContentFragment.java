package apollo.app.wofang.widget.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.net.URI;
import java.net.URISyntaxException;

import apollo.app.wofang.activity.WofangWebContentFragmentActivity;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by kuibo on 2015/8/8.
 */
public class WofangPagerWebContentFragment extends WofangWebContentFragment {

    private final String TAG = "WFPWebContentFragment";
    private boolean mIsVisibleToUser = false;
    private boolean mViewCreated = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getCacheKey() {
        return "SEC_CNT";
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
        // fix css
//        content = Regex.replace(content,
//                "<link\\s.*?href=\"([^\"]+)\"[^>]*/>",
//                "<link href=\"http://m.wofang.com$1\" rel=\"stylesheet\" type=\"text/css\" />");
//
//        // fix js
//        content = Regex.replace(content,
//                "<script\\s.*?src=\"([^\"]+)\"[^>]*></script>",
//                "<script src=\"http://m.wofang.com$1\"></script>");

        // remove element
        content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
        content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
        content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
        content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");

        content = Regex.replace(content, "(?s)<a class=\"more\"[^>]*?>.*?</a>", "");

        //content += "<style>.header,.footer,.footer_from{display:none;}</style>";

        mWebView.loadDataWithBaseURL(
                baseUrl, content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }


    @Override
    protected void sendRequestData() {
        String url = super.getEntity().getUrl();

        sendRequestData(url);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mViewCreated = true;
        if (this.mIsVisibleToUser)
            requestData(false);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && mViewCreated)
            requestData(false);
    }
}
