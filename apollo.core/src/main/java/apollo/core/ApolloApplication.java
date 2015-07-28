package apollo.core;

import android.app.Application;

public abstract class ApolloApplication extends Application {

	protected static ApolloApplication app;

	public static ApolloApplication app() {
		return ApolloApplication.app;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		ApolloApplication.app = this;

		Thread.setDefaultUncaughtExceptionHandler(new ApolloExceptionHandler());
	}
}
