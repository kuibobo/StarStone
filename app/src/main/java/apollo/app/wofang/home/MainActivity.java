package apollo.app.wofang.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.miscwidgets.widget.Panel;

import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.R;
import apollo.data.model.Section;
import apollo.view.DragAdapter;
import apollo.view.DragGridView;
import apollo.widget.HorizontalListView;

public class MainActivity extends DrawerActivity {
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private LinearLayout mLayoutSections = null; 
	private DrawerLayout mLayoutMain = null;

	//private LinearLayout mLayoutTop = null;

	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);

		super.init();
		this.initView();
		this.initListener();
	}
	
	
	@SuppressLint("InflateParams")
	private void initView() {
		this.mLayoutSections = (LinearLayout) super.findViewById(R.id.layout_sections);
		//this.mLayoutTop = (LinearLayout) super.findViewById(R.id.layout_top);
	}
	
	private void initListener() {

		
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
	}
}
