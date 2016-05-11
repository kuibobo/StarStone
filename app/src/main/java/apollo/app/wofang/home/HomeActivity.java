package apollo.app.wofang.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import apollo.activity.DrawerNightModeTabActivity;
import apollo.app.wofang.R;
import apollo.app.wofang.activity.WebIMActivity;
import apollo.app.wofang.activity.WofangWebContentFragmentActivity;

public class HomeActivity extends WofangWebContentFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.mLayoutResId = R.layout.activity_main;

		super.onCreate(savedInstanceState);
		super.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}

	@Override
	protected void initFragment() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home_title_bar, menu);

		menu.findItem(R.id.action_menu_comments).setIcon(
				new IconDrawable(this, FontAwesomeIcons.fa_comments_o)
						.colorRes(R.color.toolbar_text_color)
						.actionBarSize()
		);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_menu_comments:
				WebIMActivity.startActivity(this);
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}
