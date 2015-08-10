package apollo.widget;

import android.support.v4.app.FragmentManager;

import java.util.List;

import apollo.data.model.Section;

/**
 * Created by kuibo on 2015/8/8.
 */
public class SectionPagerAdapter extends BaseFragmentPagerAdapter<Section> {

    public SectionPagerAdapter(FragmentManager fm, Class<?> fragment, List<Section> source) {
        super(fm, fragment, source);
    }

}
