package apollo.app.wofang.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.miscwidgets.widget.Panel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.R;
import apollo.data.model.Section;
import apollo.util.ResUtil;
import apollo.view.DragAdapter;
import apollo.view.DragGridView;
import apollo.widget.SectionPagerAdapter;
import apollo.widget.HorizontalListView;

/**
 * Created by Texel on 2015/8/6.
 */
public class SectionViewPagerFragment extends Fragment implements
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager = null;
    private SectionPagerAdapter mTabAdapter = null;

    private DragAdapter mSectionAdapterCurrent = null;
    private DragAdapter mSectionAdapterSource = null;
    private DragGridView mDragGridViewCurrent = null;
    private DragGridView mDragGridViewSource = null;

    private HorizontalListView mSectionListView = null;
    private RelativeLayout mLayoutBottom = null;
    private Button mBtnSecitonDropDown = null;
    private Panel mSectionsPanel = null;


    private List<Section> mSectionsCurrent = null;
    private List<Section> mSectionsSource = null;

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
        View parent_view = null;
        View sections_view = null;

        parent_view = inflater.inflate(R.layout.fragment_main_viewpager, container, false);

        Gson gson = new Gson();
        String temp = ResUtil.read(super.getActivity().getAssets(), "recomm_sections.json");
        Type listType = new TypeToken<ArrayList<Section>>(){}.getType();

        this.mSectionsCurrent = gson.fromJson(temp, listType);
        this.mSectionAdapterCurrent = new DragAdapter(this.getActivity(), this.mSectionsCurrent);
        ///this.mSectionAdapterCurrent.notifyDataSetChanged();

        temp = ResUtil.read(super.getActivity().getAssets(), "sub_sections.json");
        this.mSectionsSource = gson.fromJson(temp, listType);
        this.mSectionAdapterSource = new DragAdapter(this.getActivity(), this.mSectionsSource);
        ///this.mSectionAdapterSource.notifyDataSetChanged();

        sections_view = inflater.inflate(R.layout.item_layout_main_sections, null);
        this.mSectionsPanel = (Panel) sections_view.findViewById(R.id.layout_main_sections);

        this.mDragGridViewCurrent = (DragGridView) sections_view.findViewById(R.id.grid_current);
        this.mDragGridViewCurrent.setAdapter(this.mSectionAdapterCurrent);

        this.mDragGridViewSource = (DragGridView) sections_view.findViewById(R.id.grid_source);
        this.mDragGridViewSource.setAdapter(this.mSectionAdapterSource);

        this.mBtnSecitonDropDown = (Button) parent_view.findViewById(R.id.btn_section_drop_down);
        this.mSectionListView = (HorizontalListView) parent_view.findViewById(R.id.section_list);
        this.mSectionListView.setAdapter(this.mSectionAdapterCurrent);

        this.mLayoutBottom = (RelativeLayout) parent_view.findViewById(R.id.layout_bottom);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.mLayoutBottom.addView(this.mSectionsPanel, params);

        this.mViewPager = (ViewPager) parent_view.findViewById(R.id.main_tab_pager);
        if (this.mViewPager.getAdapter() == null &&
                this.mTabAdapter == null) {
            this.mTabAdapter = new SectionPagerAdapter(super.getChildFragmentManager(),
                    SectionContentFragment.class,
                    this.mSectionsCurrent);
        }
        //this.mViewPager.setOffscreenPageLimit(1);
        this.mViewPager.setAdapter(this.mTabAdapter);

        this.initListener(parent_view);
        return parent_view;
    }

    private void initListener(View view) {
        this.mSectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mSectionListView.setSelection(position);
                mViewPager.setCurrentItem(position);
            }


        });

        this.mBtnSecitonDropDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mSectionsPanel.isOpen()) {
                    mSectionsPanel.setOpen(false, true);
                } else {
                    mSectionsPanel.setOpen(true, true);
                }
            }
        });

        this.mDragGridViewSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.section_name);
                final Section section = (Section) textView.getTag();
                final ImageView img = buildImageView(view);

                mSectionAdapterCurrent.addItem(section);
                mSectionAdapterCurrent.setLastItemVisibility(View.GONE);
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        View v = null;
                        int[] location = new int[2];
                        int[] target_location = new int[2];

                        textView.getLocationInWindow(location);
                        v = mDragGridViewCurrent.getChildAt(mDragGridViewCurrent.getLastVisiblePosition());
                        v.getLocationInWindow(target_location);

                        doMoveAnimation(img, mDragGridViewCurrent,
                                location[0], location[1], target_location[0], target_location[1]);
                        mSectionAdapterSource.removeItem(section);
                    }

                });
            }


        });
    }

    private ImageView buildImageView(View view) {
        Bitmap cache = null;
        ImageView iv = null;

        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);

        cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        iv = new ImageView(this.getActivity());
        iv.setImageBitmap(cache);
        return iv;
    }


    private void doMoveAnimation(final View view, final DragGridView gridView, int fromXDelta, int fromYDelta,
                                 int toXDelta, int toYDelta) {
        TranslateAnimation animation = null;
        AnimationSet animationSet = null;
        ViewGroup group = null;
        LinearLayout layout = null;
        LinearLayout.LayoutParams params = null;


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);

        layout = new LinearLayout(this.getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(view);

        group = (ViewGroup) this.getActivity().getWindow().getDecorView();
        group.addView(layout);


        animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(300L);

        animationSet = new AnimationSet(true);
        animationSet.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DragAdapter adapter = (DragAdapter) gridView.getAdapter();
                adapter.setLastItemVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });

        animationSet.setFillAfter(false);//
        animationSet.addAnimation(animation);

        view.startAnimation(animationSet);
    }
}
