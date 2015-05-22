package apollo.app.wofang.home;

import java.util.ArrayList;
import java.util.List;

import org.miscwidgets.widget.Panel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.model.Section;
import apollo.view.DragAdapter;
import apollo.view.DragGridView;
import apollo.widget.HorizontalListView;

public class MainActivity extends BaseActivity {
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private LinearLayout mLayoutSections = null; 
	private DrawerLayout mLayoutMain = null;
	private RelativeLayout mLayoutBottom = null;
	private LinearLayout mLayoutTop = null;
	private ListView mLayoutLeft = null;
	private LinearLayout mLayoutRight; //右滑视图
	
	private HorizontalListView mSectionListView = null;
	private DragAdapter mSectionAdapterCurrent = null;
	private DragAdapter mSectionAdapterSource = null;
	private Button mBtnSecitonAdd = null;
	
	private ActionBarDrawerToggle mDrawerToggle;
	
	private Panel mSectionsPanel = null;
	private DragGridView mDragGridViewCurrent = null;
	private DragGridView mDragGridViewSource = null;
	
	private List<Section> mSectionsCurrent = null;
	private List<Section> mSectionsSource = null;
		
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		

		this.mSectionsCurrent = new ArrayList<Section>();
		this.mSectionsSource = new ArrayList<Section>();
		
		this.initView();
		this.initListener();
		

		
		// test code
		
		for(int i=0; i<60; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSectionsCurrent.add(s);
		}
		this.mSectionAdapterCurrent.notifyDataSetChanged();
		// end test code
		
		// test code
		
		for(int i=0; i<64; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSectionsSource.add(s);
		}
		this.mSectionAdapterSource.notifyDataSetChanged();
		// end test code
	}
	
	
	@SuppressLint("InflateParams")
	private void initView() {
		View view = null;
		
		this.mSectionAdapterCurrent = new DragAdapter(this, this.mSectionsCurrent);
		this.mSectionAdapterSource = new DragAdapter(this, this.mSectionsSource);
		

		view = super.getLayoutInflater().inflate(R.layout.item_layout_main_sections, null);
		this.mSectionsPanel = (Panel) view.findViewById(R.id.layout_main_sections);
		this.mDragGridViewCurrent = (DragGridView) view.findViewById(R.id.grid_current);
		this.mDragGridViewCurrent.setAdapter(this.mSectionAdapterCurrent);
		
		this.mDragGridViewSource = (DragGridView) view.findViewById(R.id.grid_source);
		this.mDragGridViewSource.setAdapter(this.mSectionAdapterSource);
		
		this.mLayoutSections = (LinearLayout) super.findViewById(R.id.layout_sections);
		this.mLayoutTop = (LinearLayout) super.findViewById(R.id.layout_top);
		this.mLayoutBottom =  (RelativeLayout) super.findViewById(R.id.layout_bottom);
 
		
		this.mBtnSecitonAdd = (Button) super.findViewById(R.id.btn_section_add);
		this.mSectionListView = (HorizontalListView) super.findViewById(R.id.section_list);
		this.mSectionListView.setAdapter(this.mSectionAdapterCurrent);		
		
		
		mTitle = mDrawerTitle = getTitle();
		this.mLayoutMain = (DrawerLayout) super.findViewById(R.id.layout_main);
		this.mLayoutMain.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		this.mLayoutBottom.addView(this.mSectionsPanel, params);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        this.mLayoutLeft = (ListView) super.findViewById(R.id.layout_left);
        this.mLayoutRight = (LinearLayout) super.findViewById(R.id.layout_right);
	}
	
	private void initListener() {
		this.mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				this.mLayoutMain,      /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
				){
			
			public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
		};
 
		this.mLayoutMain.setDrawerListener(this.mDrawerToggle);
		
		this.mSectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MainActivity.this.mSectionListView.setSelection(position);
			}
			
			
		});
		
		this.mBtnSecitonAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (MainActivity.this.mSectionsPanel.isOpen()) {
					MainActivity.this.mSectionsPanel.setOpen(false, true);
				} else {
					MainActivity.this.mSectionsPanel.setOpen(true, true);
				}
			}
		});
		
//		this.mDragGridViewCurrent.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				final TextView textView = (TextView) view.findViewById(R.id.section_name);
//				final Section section = (Section)textView.getTag();
//				final ImageView img = buildImageView(view);
//				
//				MainActivity.this.mSectionAdapterSource.addItem(section);
//				MainActivity.this.mSectionAdapterSource.setLastItemVisibility(View.GONE);
//				
//				new Handler().post(new Runnable(){
//
//					@Override
//					public void run() {
//						View v = null;
//						int[] location = new int[2];
//						int[] target_location = new int[2];
//						
//						textView.getLocationInWindow(location);
//						v = MainActivity.this.mDragGridViewSource.getChildAt(MainActivity.this.mDragGridViewSource.getLastVisiblePosition());
//						v.getLocationInWindow(target_location);
//												
//						MainActivity.this.doMoveAnimation(img, MainActivity.this.mDragGridViewSource, 
//								location[0], location[1], target_location[0], target_location[1]);						
//						MainActivity.this.mSectionAdapterCurrent.removeItem(section);
//					}
//					
//				});
//			}
//			
//		});
		
		this.mDragGridViewSource.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				final TextView textView = (TextView) view.findViewById(R.id.section_name);
				final Section section = (Section)textView.getTag();
				final ImageView img = buildImageView(view);
				
				MainActivity.this.mSectionAdapterCurrent.addItem(section);
				MainActivity.this.mSectionAdapterCurrent.setLastItemVisibility(View.GONE);
				new Handler().post(new Runnable(){

					@Override
					public void run() {
						View v = null;
						int[] location = new int[2];
						int[] target_location = new int[2];
						
						textView.getLocationInWindow(location);
						v = MainActivity.this.mDragGridViewCurrent.getChildAt(MainActivity.this.mDragGridViewCurrent.getLastVisiblePosition());
						v.getLocationInWindow(target_location);
												
						MainActivity.this.doMoveAnimation(img, MainActivity.this.mDragGridViewCurrent, 
								location[0], location[1], target_location[0], target_location[1]);						
						MainActivity.this.mSectionAdapterSource.removeItem(section);
					}
					
				});
			}
			
			
		});
	}
	
	private ImageView buildImageView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
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
 
		
		params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		
		layout = new LinearLayout(this);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.addView(view);
		
		group = (ViewGroup) getWindow().getDecorView();
		group.addView(layout);

		
		animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		animation.setDuration(300L);
		
		animationSet = new AnimationSet(true);
		animationSet.setAnimationListener(new AnimationListener(){

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
		
		animationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
		animationSet.addAnimation(animation);
		 
		view.startAnimation(animationSet);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = this.mLayoutMain.isDrawerOpen(this.mLayoutLeft);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override  
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig);  
        mDrawerToggle.onConfigurationChanged(newConfig);  
    } 
    
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        // Pass the event to ActionBarDrawerToggle, if it returns  
        // true, then it has handled the app icon touch event  
        if (mDrawerToggle.onOptionsItemSelected(item)) {  
          return true;  
        }  
        // Handle your other action bar items...  
  
        return super.onOptionsItemSelected(item);  
    }  
}
