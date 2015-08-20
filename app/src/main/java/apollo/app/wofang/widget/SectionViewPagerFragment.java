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

import org.miscwidgets.widget.Panel;

import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.R;
import apollo.app.wofang.bll.Sections;
import apollo.data.model.Section;
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

    private DragAdapter mRecommSectionAdapter = null;
    private DragAdapter mSubSectionAdapter = null;
    private DragGridView mRecommDragGridView = null;
    private DragGridView mSubDragGridView = null;

    private HorizontalListView mSectionListView = null;
    private RelativeLayout mLayoutBottom = null;
    private Button mBtnSecitonDropDown = null;
    private Panel mSectionsPanel = null;


    private List<Section> mRecommSections = null;
    private List<Section> mSubSections = null;

    private int mCurTab = 0;
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


        this.mRecommSections = new ArrayList<Section>();
        this.mRecommSectionAdapter = new DragAdapter(this.getActivity(), this.mRecommSections);

        this.mSubSections = new ArrayList<Section>();
        this.mSubSectionAdapter = new DragAdapter(this.getActivity(), this.mSubSections);

        sections_view = inflater.inflate(R.layout.item_layout_main_sections, null);
        this.mSectionsPanel = (Panel) sections_view.findViewById(R.id.layout_main_sections);

        this.mRecommDragGridView = (DragGridView) sections_view.findViewById(R.id.grid_current);
        this.mRecommDragGridView.setAdapter(this.mRecommSectionAdapter);

        this.mSubDragGridView = (DragGridView) sections_view.findViewById(R.id.grid_source);
        this.mSubDragGridView.setAdapter(this.mSubSectionAdapter);

        this.mBtnSecitonDropDown = (Button) parent_view.findViewById(R.id.btn_section_drop_down);
        this.mSectionListView = (HorizontalListView) parent_view.findViewById(R.id.section_list);
        this.mSectionListView.setAdapter(this.mRecommSectionAdapter);

        this.mLayoutBottom = (RelativeLayout) parent_view.findViewById(R.id.layout_bottom);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.mLayoutBottom.addView(this.mSectionsPanel, params);

        this.mTabAdapter = new SectionPagerAdapter(super.getChildFragmentManager(),
                this.getActivity(), SectionContentFragment.class);
        this.mTabAdapter.refresh(this.mRecommSections);

        this.mViewPager = (ViewPager) parent_view.findViewById(R.id.main_tab_pager);
        this.mViewPager.setOffscreenPageLimit(3);
        this.mViewPager.setAdapter(this.mTabAdapter);

        this.initListener(parent_view);
        this.setSectionsView();
        return parent_view;
    }

    private void setChangelViewUpdate() {
        this.saveSections();
        this.setSectionsView();
        this.mTabAdapter.refresh(this.mRecommSections);
        this.selectTab(mCurTab);
        this.mViewPager.setCurrentItem(mCurTab);
    }

    private void saveSections() {
        Sections.saveRecommendSections(this.mRecommSections);
        Sections.saveSubSections(this.mSubSections);
    }

    private void selectTab(int position) {
        this.mCurTab = position;
    }

    private void setSectionsView() {
        initSectionData();
        initFragment();
    }

    private void initSectionData() {
        List<Section> recom_entities = Sections.getRecommendSections();
        List<Section> sub_entities = Sections.getSubSections();

        this.mRecommSections.clear();
        this.mRecommSections.addAll(recom_entities);
        this.mRecommSectionAdapter.notifyDataSetChanged();

        this.mSubSections.clear();
        this.mSubSections.addAll(sub_entities);
        this.mSubSectionAdapter.notifyDataSetChanged();
    }


    private void initFragment() {
        this.mTabAdapter.refresh(this.mRecommSections);
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

                    setChangelViewUpdate();
                } else {
                    mSectionsPanel.setOpen(true, true);
                }
            }
        });

        this.mRecommDragGridView.setSwapItemHandle(new DragGridView.SwapItemHandle(){

            @Override
            public void swap(int index1, int index2) {
                ///mTabAdapter.swap(index1, index2);
            }
        });

        this.mSubDragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.section_name);
                final Section section = (Section) textView.getTag();
                final ImageView img = buildImageView(view);

                mRecommSectionAdapter.addItem(section);
                mRecommSectionAdapter.setLastItemVisibility(View.GONE);
                ///mTabAdapter.addItem(section);
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        View v = null;
                        int[] location = new int[2];
                        int[] target_location = new int[2];

                        textView.getLocationInWindow(location);
                        v = mRecommDragGridView.getChildAt(mRecommDragGridView.getLastVisiblePosition());
                        v.getLocationInWindow(target_location);

                        doMoveAnimation(img, mRecommDragGridView,
                                location[0], location[1], target_location[0], target_location[1]);
                        mSubSectionAdapter.removeItem(section);
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
