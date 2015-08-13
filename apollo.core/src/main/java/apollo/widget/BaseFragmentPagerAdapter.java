package apollo.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import apollo.fragments.EntityBaseFragment;


/**
 * Created by kuibo on 2015/8/8.
 */
public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected List<EntityBaseFragment> mFragments;
    private Class<?> mFragment;

    public BaseFragmentPagerAdapter(FragmentManager fm, Class<?> fragment, List<T> items) {
        super(fm);

        EntityBaseFragment ebf = null;
        this.mFragments = new ArrayList<EntityBaseFragment>();
        this.mFragment = fragment;
        for(int i=0; i<items.size();i++) {
            try {
                ebf = (EntityBaseFragment) fragment.newInstance();
                this.mFragments.add(ebf);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            ebf.setEntity(items.get(i));
        }
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    @Override
    public EntityBaseFragment<T> getItem(int position) {
        return this.mFragments.get(position);
    }

    public void addItem(T t) {
        EntityBaseFragment ebf = null;

        try {
            ebf = (EntityBaseFragment) this.mFragment.newInstance();
            ebf.setEntity(t);
            this.mFragments.add(ebf);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.notifyDataSetChanged();
    }

    public void swap(int index1, int index2) {
        EntityBaseFragment ebf = this.getItem(index1);


        if (index1 < index2) {
            this.mFragments.add(index2 + 1, ebf);
            this.mFragments.remove(index1);
        } else {
            this.mFragments.add(index2, ebf);
            this.mFragments.remove(index1 + 1);
        }
        notifyDataSetChanged();
    }
}
