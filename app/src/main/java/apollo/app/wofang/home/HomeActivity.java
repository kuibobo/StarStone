package apollo.app.wofang.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import apollo.app.wofang.R;

public class HomeActivity extends DrawerActivity {
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private LinearLayout mLayoutSections = null; 
	private DrawerLayout mLayoutMain = null;

	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, HomeActivity.class);
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
