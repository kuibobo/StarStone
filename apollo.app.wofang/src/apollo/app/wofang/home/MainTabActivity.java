package apollo.app.wofang.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.widget.ColumnHorizontalScrollView;

public class MainTabActivity extends BaseActivity {

	private ViewPager mViewPager;
	private View mView;
	private ColumnHorizontalScrollView mScrollView;
	
	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainTabActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_maintabs);
		
		initView();
		initListener();
	}
	
	
	private void initView() {
		this.mScrollView = (ColumnHorizontalScrollView) super.findViewById(R.id.scroll_view);
	}
	
	private void initListener() {
		
	}
}
