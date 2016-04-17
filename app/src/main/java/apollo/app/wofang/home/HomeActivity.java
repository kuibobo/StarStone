package apollo.app.wofang.home;

import android.os.Bundle;
import apollo.app.wofang.R;
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
}
