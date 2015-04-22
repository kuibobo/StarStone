package apollo.app;

import android.app.Application;
import android.content.Context;

public abstract class ApolloApplication extends Application {

	protected static ApolloApplication app;
	
	public abstract void startMainActivity(Context c);
	
	public static ApolloApplication app() {
		return ApolloApplication.app;
	}
	
}
