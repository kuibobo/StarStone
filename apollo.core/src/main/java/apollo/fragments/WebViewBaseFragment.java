package apollo.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import apollo.cache.AppCache;
import apollo.core.R;
import apollo.data.model.Entity;
import apollo.net.SyncHttpClient;
import apollo.util.CompatibleUtil;
import apollo.widget.StatusLayout;

/**
 * Created by Texel on 2015/8/4.
 */
public abstract class WebViewBaseFragment<T> extends EntityBaseFragment<T> {

    class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private Serializable seri;
        private String key;

        private SaveCacheTask(Serializable seri, String key) {
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            AppCache.add(key, seri, true);
            return null;
        }
    }

    protected HttpContentAsyncTask mHttpContentTask;
    public class HttpContentAsyncTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String url = null;
            String content = null;

            url = (String)params[0];

            content = SyncHttpClient.getInstance().getContent(url);
            return content;
        }

        @Override
        protected void onPostExecute(String content) {
            executeOnLoadDataSuccess(content);
        }
    };

    class ReadCacheTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String key = params[0];

            return (String)AppCache.get(key);
        }

        @Override
        protected void onPostExecute(String content) {
            if (TextUtils.isEmpty(content)) {
                executeOnLoadDataSuccess(null);
            } else {
                executeOnLoadDataError(null);
            }
        }
    }

    protected WebViewClient mWebViewClient = new WebViewClient() {
        private boolean receiveError = false;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            receiveError = false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return onUrlClick(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            receiveError = true;
        }

    };

    protected AsyncTask<String, Void, String> mCacheTask;

    protected WebView mWebView;
    protected StatusLayout mStatusLayout;

    protected abstract String getCacheKey();
    protected abstract String getBaseUrl();
    protected abstract void sendRequestData();
    protected abstract void sendRequestData(String url);
    protected abstract void executeOnLoadDataSuccess(String content);
    protected abstract void executeOnLoadDataError(String content);
    protected abstract boolean onUrlClick(String url);

    protected void saveCache(Entity entity) {
        new SaveCacheTask(entity, getCacheKey()).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_content, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        this.mStatusLayout = (StatusLayout) view.findViewById(R.id.layout_status);
        this.mWebView = (WebView)view.findViewById(R.id.webview);
        this.mWebView.setWebViewClient(this.mWebViewClient);

        this.initWebViews(mWebView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData(false);
    }

    protected void initWebViews(WebView webView) {
        WebSettings settings = null;

        settings = webView.getSettings();
        settings.setDefaultFontSize(15);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
    }

    protected void recycleWebView() {
        if (this.mWebView != null) {
            this.mWebView.setVisibility(View.GONE);
            this.mWebView.removeAllViews();
        }
    }

    protected void requestData(boolean refresh) {
        String key = getCacheKey();
        if (CompatibleUtil.hasInternet(super.getActivity())
                && (!AppCache.exists( key) || refresh)) {
            sendRequestData();
        } else {
            readCacheData(key);
        }
    }

    protected void readCacheData(String key) {
        cancelReadCacheTask();
        mCacheTask = new ReadCacheTask().execute(key);
    };

    private void cancelReadCacheTask() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    public void refresh() {
        this.mState = STATE_REFRESH;
        requestData(true);
    }

    @Override
    public void onDestroy() {
        this.recycleWebView();
        super.onDestroy();
    }
}
