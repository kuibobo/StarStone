package apollo.app.wofang.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import apollo.activity.NightModeFragmentActivity;
import apollo.app.wofang.R;

/// 取消使用 左右滑动菜单。。。
///public class HomeActivity extends DrawerActivity {
public class HomeActivity extends NightModeFragmentActivity {

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

		/// 继承DrawerActivity时用
		///super.init();
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
