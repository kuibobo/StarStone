package apollo.app.wofang.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;

public class MainTabActivity extends BaseActivity {

	private ViewPager mViewPager;
	private View mView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_maintabs);
		
		initView();
		initListener();
	}
	
	
	private void initView() {
		mViewPager = (ViewPager)super.findViewById(R.id.view_pager);
		mView = (View) super.findViewById(R.id.view);
	}
	
	private void initListener() {
		
	}
}
