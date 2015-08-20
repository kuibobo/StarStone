package apollo.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.SyncStateContract;
import android.util.Log;

import apollo.util.ConfigUtil;

public abstract class ApolloApplication extends Application {
	private static final String LAST_VERSION = "lase_version";

	protected static ApolloApplication app;

	public static ApolloApplication app() {
		return ApolloApplication.app;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		ApolloApplication.app = this;

		Thread.setDefaultUncaughtExceptionHandler(new ApolloExceptionHandler());
		initVersion();
	}

	private void initVersion() {
		try {
			ConfigUtil.VERSION = getPackageManager().getPackageInfo("apollo.app", PackageManager.GET_CONFIGURATIONS).versionName;
		} catch (PackageManager.NameNotFoundException ex) {
			Log.e(getClass().getName(), "InitVersion" + ex.getMessage());
			ConfigUtil.VERSION = "";
		}
	}

	public boolean isFirstUse() {
		boolean isFirest = false;
		String ver = null;

		ver = getSharedPreferences(ConfigUtil.SETTINGFILE, 0).getString(LAST_VERSION, "");
		isFirest = ver.equals(ConfigUtil.VERSION) == false;
		return isFirest;
	}

	public void setUsed() {
		SharedPreferences.Editor editor = getSharedPreferences(ConfigUtil.SETTINGFILE, 0).edit();
		editor.putString(LAST_VERSION, ConfigUtil.VERSION);
		editor.commit();
	}
}
