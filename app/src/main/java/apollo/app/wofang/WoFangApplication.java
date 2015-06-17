package apollo.app.wofang;

import android.content.Context;
import apollo.app.ApolloApplication;
import apollo.app.wofang.home.MainActivity;
import apollo.app.wofang.home.TestActivity;

public class WoFangApplication extends ApolloApplication {
	
	@Override
	public void startMainActivity(Context c) {
		//MainActivity.startActivity(c);
		TestActivity.startActivity(c);
	}
}
