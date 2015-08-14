package apollo.app.wofang.widget;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import apollo.app.wofang.R;
import apollo.data.model.Section;
import apollo.fragments.WebViewBaseFragment;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by kuibo on 2015/8/8.
 */
public class SectionContentFragment extends WebViewBaseFragment<Section> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("SectionContentFragment", "onCreate" + this.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_content, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        this.mStatusLayout = (StatusLayout) view.findViewById(R.id.layout_status);
        this.mWebView = (WebView)view.findViewById(R.id.webview);
        this.mWebView.setWebViewClient(super.mWebViewClient);
    }


    @Override
    protected String getCacheKey() {
        return "SEC_CNT";
    }

    @Override
    protected void executeOnLoadDataSuccess(String content) {
        // fix css
        content = Regex.replace(content,
                "<link\\s.*?href=\"([^\"]+)\"[^>]*/>",
                "<link href=\"http://m.wofang.com$1\" rel=\"stylesheet\" type=\"text/css\" />");

        // fix js
        content = Regex.replace(content,
                "<script\\s.*?src=\"([^\"]+)\"[^>]*></script>",
                "<script src=\"http://m.wofang.com$1\"></script>");

        // remove element
        content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
        content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
        content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
        content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");

        //content += "<style>.header,.footer,.footer_from{display:none;}</style>";
        mWebView.loadDataWithBaseURL(
                "wei://base", content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }

    @Override
    protected void executeOnLoadDataError(String content) {
        mStatusLayout.setStatus(StatusLayout.NETWORK_ERROR);
    }

    @Override
    protected void sendRequestData() {
        String url = super.getEntity().getUrl();

        Log.i(this.getClass().getName(), super.getEntity().getName());

        mStatusLayout.setStatus(StatusLayout.NETWORK_LOADING);

        if (mHttpContentTask != null && mHttpContentTask.getStatus() != AsyncTask.Status.FINISHED)
            mHttpContentTask.cancel(true);

        mHttpContentTask = new HttpContentAsyncTask();
        mHttpContentTask.execute(url);
    }
}
