package apollo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import apollo.app.SplashActivity;
import apollo.core.ApolloApplication;

public class ConfigUtil  {
	private Properties props;

	public static String VERSION = "";
	public static final String SETTINGFILE = "settings";

	protected ConfigUtil(String file) {
		InputStream is = null;
		
		this.props = new Properties();
		try {
			is = ApolloApplication.app().getAssets().open(file);
			this.props.load(is);
		} catch (FileNotFoundException ex) {
			Log.e(ConfigUtil.class.toString(), ex.getMessage());
		} catch (IOException ex) {
			Log.e(ConfigUtil.class.toString(), ex.getMessage());
		}
	}
	
	public static ConfigUtil getInstance() {
		ConfigUtil config = null;
		String file = null;
		
		file = "config.properties";
		config = new ConfigUtil(file);
		return config;
	}
	
	public String getString(String key) {
		return this.props.getProperty(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	public static Object getMetaData(String key) {
		Application app = null;
		ComponentName cn = null;
		ActivityInfo ai = null;
		Object value = null;
		PackageManager pm = null;


		app = ApolloApplication.app();
		pm = app.getPackageManager();
		cn = new ComponentName(app.getPackageName(), SplashActivity.class.getName());

		try {
			ai = pm.getActivityInfo(cn, PackageManager.GET_META_DATA);
			value = ai.metaData.get(key);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("ConfigUtil", e.getMessage());
		}

		return value;
	}

	public static String getMetaValue(String key) {
		return (String) getMetaData(key);
	}

	public static int getIntMetaValue(String key) {
		return (Integer) getMetaData(key);
	}


}
