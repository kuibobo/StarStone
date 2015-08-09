package apollo.widget;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import apollo.data.model.Section;
import apollo.fragments.BaseTabFragment;
import apollo.fragments.BaseTabFragment.TabChangedListener;
import apollo.fragments.SectionContentFragment;


/**
 * Created by kuibo on 2015/8/8.
 */
public abstract class SlidingTabPagerAdapter extends FragmentPagerAdapter
        implements TabChangedListener {

    protected final Context context;
    protected final BaseTabFragment[] fragments;

    public SlidingTabPagerAdapter(FragmentManager mgr, List<Section> source, Context context,
                                  ViewPager vp) {
        super(mgr);
        this.fragments = new BaseTabFragment[source.size()];
        this.context = context;

        BaseTabFragment btf = null;

        for(int i=0; i<source.size();i++) {
            fragments[i] = new SectionContentFragment();
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public BaseTabFragment getItem(int position) {
        return fragments[position];
    }

}
