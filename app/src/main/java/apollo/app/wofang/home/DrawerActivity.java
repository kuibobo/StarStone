package apollo.app.wofang.home;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.widget.NavigationDrawerFragment;

public class DrawerActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private ActionBarDrawerToggle mDrawerToggle = null;

	private DrawerLayout mLayoutMain = null;
	private View mLayoutLeft = null; // 左滑视图
	private View mLayoutRight = null; // 右滑视图
	private Toolbar mToolbar = null;
	private ActionBar mActionBar = null;

	protected void init() {
		this.initView();
		this.initListener();
	}

	@SuppressLint("InflateParams")
	private void initView() {
		this.mLayoutMain = (DrawerLayout) super.findViewById(R.id.layout_main);
		this.mLayoutLeft = (View) super.findViewById(R.id.layout_left);
		this.mLayoutRight = (View) super.findViewById(R.id.layout_right);
		this.mActionBar = super.getSupportActionBar();

		this.mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		this.mActionBar.setDisplayShowTitleEnabled(true);
		this.mActionBar.setHomeButtonEnabled(true);
		this.mActionBar.setDisplayHomeAsUpEnabled(true);
		this.mActionBar.setHomeButtonEnabled(true);
		this.mActionBar.setDisplayShowHomeEnabled(false); // 隐藏系统actionbar icon
		this.mActionBar.setDisplayShowTitleEnabled(false);
		this.mActionBar.setDisplayShowCustomEnabled(true);

//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//		getActionBar().setDisplayShowHomeEnabled(false); // 隐藏系统actionbar icon
//		getActionBar().setDisplayShowTitleEnabled(false);
//		getActionBar().setDisplayShowCustomEnabled(true);
//		View actionbarLayout = LayoutInflater.from(this).inflate(
//				R.layout.item_layout_action_bar, null);
//
//		getActionBar().setCustomView(actionbarLayout);
//		Button btnLeft = (Button) actionbarLayout.findViewById(R.id.btn_left); // 左边按钮
//		Button btnRight = (Button) actionbarLayout.findViewById(R.id.btn_right); // 右边按钮
//
//		btnLeft.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 如果右边视图显示则关闭，如果左边视图显示则关闭如果关闭则显示
//				if (mLayoutMain.isDrawerOpen(mLayoutRight)) {
//					mLayoutMain.closeDrawer(Gravity.END);
//				}
//				if (mLayoutMain.isDrawerOpen(mLayoutLeft)) {
//					mLayoutMain.closeDrawer(Gravity.START);
//				} else {
//					mLayoutMain.openDrawer(Gravity.START);
//
//				}
//			}
//		});
//		btnRight.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 如果左边视图显示则关闭，如果右边视图显示则关闭如果关闭则显示
//				if (mLayoutMain.isDrawerOpen(mLayoutLeft)) {
//					mLayoutMain.closeDrawer(Gravity.START);
//				}
//				if (mLayoutMain.isDrawerOpen(mLayoutRight)) {
//					mLayoutMain.closeDrawer(Gravity.END);
//				} else {
//					mLayoutMain.openDrawer(Gravity.END);
//
//				}
//
//			}
//		});
	}

	@SuppressWarnings("deprecation")
	private void initListener() {
		this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mLayoutMain, this.mToolbar, R.string.drawer_open,
				R.string.drawer_close);
		this.mDrawerToggle.syncState();
		this.mLayoutMain.setDrawerListener(this.mDrawerToggle);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = this.mLayoutMain.isDrawerOpen(this.mLayoutLeft);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_menu_search).setIcon(
				new IconDrawable(this, Iconify.IconValue.fa_search)
						.colorRes(R.color.toolbar_text_color)
						.sizeDp(22)
		);

        menu.findItem(R.id.action_menu_favor).setIcon(
				new IconDrawable(this, Iconify.IconValue.fa_star)
						.colorRes(R.color.toolbar_text_color)
						.actionBarSize()
		);

		return super.onCreateOptionsMenu(menu);
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

	@Override
	public void onNavigationDrawerItemSelected(int position) {

	}
}
