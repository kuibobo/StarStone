package apollo.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import apollo.fragments.EntityBaseFragment;


/**
 * Created by kuibo on 2015/8/8.
 */
public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected final EntityBaseFragment[] mFragments;

    public BaseFragmentPagerAdapter(FragmentManager fm, Class<?> fragment, List<T> sources) {
        super(fm);

        this.mFragments = new EntityBaseFragment[sources.size()];
        for(int i=0; i<sources.size();i++) {
            try {
                this.mFragments[i] = (EntityBaseFragment)fragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            this.mFragments[i].setEntity(sources.get(i));
        }
    }

    @Override
    public int getCount() {
        return this.mFragments.length;
    }

    @Override
    public EntityBaseFragment<T> getItem(int position) {
        return this.mFragments[position];
    }

}
