package apollo.app.wofang.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apollo.data.model.Entity;
import apollo.fragments.WebViewBaseFragment;

/**
 * Created by Texel on 2015/8/5.
 */

public class MainDetailFragment extends WebViewBaseFragment {
    @Override
    protected String getCacheKey() {
        return null;
    }

    @Override
    protected void executeOnLoadDataSuccess(Entity entity) {

    }

    @Override
    protected void executeOnLoadDataError(String object) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        Bundle args = null;


        args = super.getArguments();
        if (args != null) {

        }
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        super.initWebViews(super.mWebView);

        //view.findViewById(R.id.ib_zoomin).setOnClickListener(this);
        //.findViewById(R.id.ib_zoomout).setOnClickListener(this);
    }

    private void fillWebView() {
        String content = null;

        super.mWebView.setWebViewClient(super.mWebViewClient);
       // super.mWebView.loadDataWithBaseURL(
       //         AppConfig.LOCAL_PATH, content, mimeType, AppConfig.UTF8, null);

    }


}
