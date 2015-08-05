package apollo.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Texel on 2015/8/4.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected static final int STATE_NONE = 0;
    protected static final int STATE_REFRESH = 1;
    protected static final int STATE_LOADMORE = 2;
    protected int mState = STATE_NONE;

    @Override
    public void onClick(View v) {

    }

    /**
     * Listener the back key click event.
     *
     * @return false (default)
     */
    public boolean onBackPressed() {
        return false;
    }
}
