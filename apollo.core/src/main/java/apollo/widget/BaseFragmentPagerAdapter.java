package apollo.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

import apollo.data.model.Entity;
import apollo.fragments.EntityBaseFragment;


/**
 * Created by kuibo on 2015/8/8.
 */
public abstract class BaseFragmentPagerAdapter<T> extends FragmentStatePagerAdapter {

    private Context mContext = null;
    private FragmentManager mManager = null;
    private List<Entity> mEntities = null;
    private WeakHashMap<Integer, EntityBaseFragment> mFragments = null;
    private Class<?> mClazz = null;

    public BaseFragmentPagerAdapter(FragmentManager m, Context c, Class<?> clazz) {
        super(m);

        this.mContext = c;
        this.mManager = m;
        this.mClazz = clazz;
        this.mEntities = new ArrayList<Entity>();
        this.mFragments = new WeakHashMap<Integer, EntityBaseFragment>();
    }

    private EntityBaseFragment getFragmentFromCache(Entity e) {
        EntityBaseFragment f = null;

        f = this.mFragments.get(e.getId());
        if (f == null) {
            try {f = (EntityBaseFragment) mClazz.newInstance();} catch(Exception ex){};
            f.setEntity(e);
            this.mFragments.put(e.getId(), f);
        }
        return f;
    }

    @Override
    public int getCount() {
        return this.mEntities.size();
    }

    @Override
    public int getItemPosition(Object obj) {
        return POSITION_NONE;
    }

    @Override
    public EntityBaseFragment getItem(int location) {
        return getFragmentFromCache(this.mEntities.get(location));
    }

    public int getCustomPager(Entity e) {
        if (e == null)
            return 0;

        for(int i=0; i<this.mEntities.size(); i++) {
            if (this.mEntities.get(i).getId() == e.getId())
                return i;
        }
        return 0;
    }

    public void refresh(List<Entity> entities) {
        if (entities != null && entities.size() > 0) {
            this.mEntities.clear();
            this.mEntities.addAll(entities);
            this.notifyDataSetChanged();
        }


        Iterator<Entity> itor = null;
        Entity e = null;
        EntityBaseFragment f = null;

        itor = this.mEntities.iterator();
        while(itor.hasNext()) {
            e = itor.next();
            f = getFragmentFromCache(e);

            this.mFragments.put(e.getId(), f);
        }
    }

}
