package apollo.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.List;

import apollo.data.model.Section;
import apollo.fragments.BaseTabFragment;

/**
 * Created by kuibo on 2015/8/8.
 */
public class SectionPagerAdapter extends SlidingTabPagerAdapter {

    public SectionPagerAdapter(FragmentManager mgr, Context context,
                               List<Section> source,
                               ViewPager viewPager) {
        super(mgr, source, context.getApplicationContext(),
                viewPager);
    }

    @Override
    public boolean isCurrent(BaseTabFragment fragment) {
        return false;
    }
}
