package apollo.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import apollo.cache.AppCache;
import apollo.data.model.Entity;
import apollo.net.AsyncHttpClient;

/**
 * Created by Texel on 2015/8/4.
 */
public abstract class BaseDetailFragment extends BaseFragment {

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

    class HttpContextAsyncTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            String url = null;
            String content = null;

            url = (String)params[0];

            content = AsyncHttpClient.getInstance().getContent(url);
            return content;
        }

        @Override
        protected void onPostExecute(String content) {

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
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            receiveError = true;
        }

    };

    private HttpContextAsyncTask mHttpContentTask;
    protected WebView mWebView;

    protected abstract String getCacheKey();

    protected abstract void executeOnLoadDataSuccess(Entity entity);

    protected abstract void executeOnLoadDataError(String object);


    protected void saveCache(Entity entity) {
        new SaveCacheTask(entity, getCacheKey()).execute();
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
            this.mWebView.destroy();
            this.mWebView = null;
        }
    }


    @Override
    public void onDestroy() {
        this.recycleWebView();
        super.onDestroy();
    }
}
