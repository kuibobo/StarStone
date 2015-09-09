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
	}
	
	private void initListener() {
	}
}
