package apollo.fragments;

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

import apollo.widget.StatusLayout;

/**
 * Created by Texel on 2016/5/11.
 */
public class WebContentFragment<T> extends WebViewBaseFragment<T> {

    private final String TAG = "WebContentFragment";

    public static final String BUNDLE_KEY_FRAGMENT_CLASS = "FRAGMENT_CLASS";
    public static final String BUNDLE_KEY_URL = "URL";

    @Override
    protected String getCacheKey() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = null;

        args = super.getArguments();
        if (args != null) {
            mUrl = args.getString(BUNDLE_KEY_URL);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void sendRequestData() {
        sendRequestData(mUrl);
    }

    @Override
    protected void sendRequestData(String url) {
        Log.i(TAG, "sendRequestData:" + url);

        mUrl = url;
        mStatusLayout.setStatus(StatusLayout.NETWORK_LOADING);

        if (mHttpContentTask != null && mHttpContentTask.getStatus() != AsyncTask.Status.FINISHED)
            mHttpContentTask.cancel(true);

        mHttpContentTask = new HttpContentAsyncTask();
        mHttpContentTask.execute(url);
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

        mWebView.loadDataWithBaseURL(
                baseUrl, content, "text/html", "UTF-8", null);

        mStatusLayout.setStatus(StatusLayout.HIDE_LAYOUT);
    }

    @Override
    protected void executeOnLoadDataError(String content) {
        mStatusLayout.setStatus(StatusLayout.NETWORK_ERROR);
    }

    @Override
    protected boolean onUrlClick(String url) {
        Log.i(TAG, "onUrlClick:" + url);

        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        return false;
    }
}
