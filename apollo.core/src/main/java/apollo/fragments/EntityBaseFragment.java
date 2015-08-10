package apollo.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Texel on 2015/8/4.
 */
public abstract class EntityBaseFragment<T> extends Fragment {
    protected static final int STATE_NONE = 0;
    protected static final int STATE_REFRESH = 1;
    protected static final int STATE_LOADMORE = 2;
    protected int mState = STATE_NONE;

    private T mEntity;

    public T getEntity() {
        return this.mEntity;
    }

    public void setEntity(T entity) {
        this.mEntity = entity;
    }
}
