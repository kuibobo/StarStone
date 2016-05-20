package apollo.app.wofang.widget.fragment.webcontent;


/**
 * Created by Texel on 2015/9/1.
 */
public class BlankWebFragment extends WofangWebFragment {
    private final String TAG = this.getClass().getName();

    @Override
    protected boolean onUrlClick(String url) {
        if (super.onUrlClick(url) == false)
            this.sendRequestData(url);

        return true;
    }
}
