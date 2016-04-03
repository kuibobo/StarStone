package apollo.app.wofang.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.widget.IconTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.R;
import apollo.widget.HorizontalListView;

public class DrawerActivity extends ActionBarActivity {

	class DrawerMenu {
		private String icon;
		private String text;

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	class DrawerMenuHolder {
		private IconTextView mIconTextView;
		private TextView mTextView;

		public DrawerMenuHolder(View view) {
			mIconTextView = (IconTextView) view.findViewById(R.id.drawer_left_list_item_icon);
			mTextView = (TextView) view.findViewById(R.id.drawer_left_list_item_text);
		}

		public TextView getTextView() {
			return mTextView;
		}

		public IconTextView getIconTextView() {
			return mIconTextView;
		}
	}

	class DrawerMenuAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<DrawerMenu> mItemsData;
		private int mResId;

		public DrawerMenuAdapter(Context context, List<DrawerMenu> listItemData, int res_id) {
			mInflater = LayoutInflater.from(context);
			mItemsData = listItemData;
			mResId = res_id;
		}

		@Override
		public Object getItem(int position) {
			return mItemsData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			DrawerMenuHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(mResId, null);
				holder = new DrawerMenuHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (DrawerMenuHolder) convertView.getTag();
			}

			DrawerMenu item = mItemsData.get(position);
			holder.getIconTextView().setText(item.getIcon());
			holder.getTextView().setText(item.getText());

			return convertView;
		}

		@Override
		public int getCount() {
			return mItemsData.size();
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			selectItem(position);
		}
	}

	private ActionBarDrawerToggle mDrawerToggle = null;

	private DrawerLayout mLayoutMain = null;
	private View mLayoutLeft = null; // 左滑视图
	private View mLayoutRight = null; // 右滑视图
	private ListView mListMenu = null;
	private HorizontalListView mListFooter = null;
	private Toolbar mToolbar = null;
	private ActionBar mActionBar = null;

	protected void init() {
		this.initView();
		this.initSlidingMenu();
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

	private void initSlidingMenu() {
		List<DrawerMenu> menus = null;

		menus = new ArrayList<>();
		for (String data : getResources().getStringArray(R.array.main_drawer_left_menu_array)) {
			DrawerMenu itemData = new DrawerMenu();
			itemData.setIcon("{fa-rss}");
			itemData.setText(data);
			menus.add(itemData);
		}
		this.mListMenu = (ListView) super.findViewById(R.id.list_menu);
		this.mListMenu.setAdapter(new DrawerMenuAdapter(this, menus, R.layout.item_list_main_drawer_left));
		this.mListMenu.setOnItemClickListener(new DrawerItemClickListener());

		menus = new ArrayList<>();
		for (String data : getResources().getStringArray(R.array.main_drawer_footer_menu_array)) {
			DrawerMenu itemData = new DrawerMenu();
			itemData.setIcon("{fa-rss}");
			itemData.setText(data);
			menus.add(itemData);
		}
		this.mListFooter = (HorizontalListView) super.findViewById(R.id.list_footer);
		this.mListFooter.setAdapter(new DrawerMenuAdapter(this, menus, R.layout.item_list_main_drawer_footer));
		this.mListFooter.setOnItemClickListener(new DrawerItemClickListener());
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
				new IconDrawable(this, FontAwesomeIcons.fa_search)
						.colorRes(R.color.toolbar_text_color)
						.sizeDp(22)
		);

        menu.findItem(R.id.action_menu_favor).setIcon(
				new IconDrawable(this, FontAwesomeIcons.fa_star)
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

	private void selectItem(int position) {
		mLayoutMain.closeDrawer(mLayoutLeft);
	}
}
