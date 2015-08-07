package apollo.app.wofang.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apollo.app.wofang.R;

/**
 * Created by Texel on 2015/8/6.
 */
public class ContentViewPagerFragment extends Fragment implements
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        view = inflater.inflate(R.layout.fragment_main_viewpager, container, false);
        //mViewPager = (ViewPager) view.findViewById(R.id.main_tab_pager);
        //mViewPager.setOffscreenPageLimit(mTabAdapter.getCacheCount());
        //mViewPager.setAdapter(mTabAdapter);
        //mViewPager.setOnPageChangeListener(this);
        return view;
    }
}
