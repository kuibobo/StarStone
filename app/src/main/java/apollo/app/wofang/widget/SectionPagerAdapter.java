package apollo.app.wofang.widget;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.List;

import apollo.data.model.Section;
import apollo.fragments.WebViewBaseFragment;
import apollo.widget.BaseFragmentPagerAdapter;

/**
 * Created by kuibo on 2015/8/8.
 */
public class SectionPagerAdapter extends BaseFragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager m, Context c) {
        super(m, c);
    }

    @Override
    public Class<?> getFragmentClass() {
        return MainContentFragment.class;
    }
}
