package apollo.fragments;

/**
 * Created by kuibo on 2015/8/8.
 */
public abstract class BaseTabFragment extends BaseFragment {

    protected int mCatalog = 0;
    private TabChangedListener mListener;

    public BaseTabFragment() {
    }

    public void setCatalog(int catalog) {
        mCatalog = catalog;
    }

    public final void addListener(TabChangedListener listener) {
        mListener = listener;
    }


    public interface TabChangedListener {

        boolean isCurrent(BaseTabFragment fragment);
    }
}