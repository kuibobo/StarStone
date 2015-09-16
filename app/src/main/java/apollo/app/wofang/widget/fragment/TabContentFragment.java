package apollo.app.wofang.widget.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import apollo.app.wofang.activity.WofangWebContentFragmentActivity;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by kuibo on 2015/8/8.
 */
public class TabContentFragment extends WofangWebContentFragment {

    private final String TAG = this.getClass().getName();

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
                this.getBaseUrl(), content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }


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

        WofangWebContentFragmentActivity.startActivity(super.getActivity(), url, BlankContentFragment.class);
        return true;
    }
}
