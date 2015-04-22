package apollo.app.wofang;

import android.content.Context;
import apollo.app.ApolloApplication;
import apollo.app.wofang.home.MainTabActivity;

public class WoFangApplication extends ApolloApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		
		WoFangApplication.app = this;
		
	}
	
	@Override
	public void startMainActivity(Context c) {
		MainTabActivity.startActivity(c);
	}
}
