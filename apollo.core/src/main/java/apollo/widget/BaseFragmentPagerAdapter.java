package apollo.widget;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import apollo.data.model.Entity;
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
        //Collections.swap(this.mFragments, index1, index2);
        EntityBaseFragment<T> ebf1 = null;
        EntityBaseFragment<T> ebf2 = null;
        T e1 = null;
        T e2 = null;

        ebf1 = this.getItem(index1);
        ebf2 = this.getItem(index2);

        e1 = ebf1.getEntity();
        e2 = ebf2.getEntity();

        ebf1.setEntity(e2);
        ebf2.setEntity(e1);
        notifyDataSetChanged();
    }
}
