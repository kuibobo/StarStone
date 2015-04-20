package apollo.app;

import android.app.Application;

public class ApolloApplication extends Application {

	private static ApolloApplication app;

	public static ApolloApplication app() {
		return ApolloApplication.app;
	}

}
