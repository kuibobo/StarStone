package apollo.app.wofang.widget.fragment;

import android.os.Bundle;
import android.util.Log;

import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by kuibo on 2015/8/8.
 */
public class MainContentFragment extends WofangWebContentFragment {

    private static final String TAG = MainContentFragment.class.getName();

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

        Log.i(TAG, content);
        mWebView.loadDataWithBaseURL(
                this.getBaseUrl(), content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }


    @Override
    protected void sendRequestData() {
        String url = super.getEntity().getUrl();

        sendRequestData(url);
    }
}
