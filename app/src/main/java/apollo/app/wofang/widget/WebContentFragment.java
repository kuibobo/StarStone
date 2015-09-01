package apollo.app.wofang.widget;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import apollo.app.wofang.R;
import apollo.app.wofang.activity.WebContentFragmentActivity;
import apollo.data.model.Entity;
import apollo.fragments.WebViewBaseFragment;
import apollo.util.Regex;
import apollo.widget.StatusLayout;

/**
 * Created by Texel on 2015/8/5.
 */

public class WebContentFragment extends WebViewBaseFragment {
    private static final String TAG = WebContentFragment.class.getName();
    private String mUrl = null;
    @Override
    protected String getCacheKey() {
        return "WCF";
    }

    @Override
    protected void executeOnLoadDataSuccess(String content) {

        content = Regex.replace(content, "(?s)<header class=\"bot_menu\">.*</header>", "");

        content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
        content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
        content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
        content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");

        mWebView.loadDataWithBaseURL(
                "http://m.wofang.com", content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }

    @Override
    protected void executeOnLoadDataError(String object) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_content, container, false);
        Bundle args = null;


        args = super.getArguments();
        if (args != null) {
            mUrl = args.getString(WebContentFragmentActivity.BUNDLE_KEY_URL);
        }
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        this.mStatusLayout = (StatusLayout) view.findViewById(R.id.layout_status);
        this.mWebView = (WebView)view.findViewById(R.id.webview);
        this.mWebView.setWebViewClient(super.mWebViewClient);

        super.initWebViews(mWebView);
    }

    @Override
    protected void sendRequestData() {
        sendRequestData(mUrl);
    }

    @Override
    protected void sendRequestData(String url) {
        Log.i(TAG, "sendRequestData:" + url);

        mStatusLayout.setStatus(StatusLayout.NETWORK_LOADING);

        if (mHttpContentTask != null && mHttpContentTask.getStatus() != AsyncTask.Status.FINISHED)
            mHttpContentTask.cancel(true);

        mHttpContentTask = new HttpContentAsyncTask();
        mHttpContentTask.execute(url);
    }

    @Override
    protected void onUrlClick(String url) {
        //WebContentFragmentActivity.startActivity(super.getActivity(), url)
        sendRequestData(url);
    }
}
