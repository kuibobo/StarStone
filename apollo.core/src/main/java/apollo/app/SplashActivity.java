package apollo.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.lang.reflect.Method;

import apollo.core.ApolloApplication;
import apollo.core.R;
import apollo.util.ConfigUtil;
import apollo.util.ImageUtil;

public class SplashActivity extends BaseActivity {

	private AlphaAnimation mAnim = null;
	private ImageView mImage = null;
	private Handler mHandler;
	private Class mMainActivityClass = null;

	private boolean mHaveFinishiAnim = false;
	private boolean mHaveInitData = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_splash);

		this.initViews();
		this.initListener();

		this.mImage.startAnimation(mAnim);
	}
	
	private void initViews() {
		Bitmap mBitmap = null;
		
		mBitmap = ImageUtil.getResBitmap(this, R.drawable.bg_splash_logo);
		this.mImage = (ImageView) super.findViewById(R.id.iv_splash_logo);
		this.mImage.setImageBitmap(mBitmap);

		long duration = 300L;
		String activity_main = null;

		try {
			ComponentName cm = getComponentName();

			duration = ConfigUtil.getIntMetaValue("duration");//info.metaData.getInt("duration");//Long.parseLong(info.metaData.getString("duration"));
			activity_main = ConfigUtil.getMetaValue("android.activity.MAIN");//info.metaData.getString("android.activity.MAIN");
		} catch (Exception ex) {
			Log.e("SplachActivity", ex.getMessage());
		}

		try {
			mMainActivityClass = (Class) Class.forName(activity_main);
		} catch (Exception ex) {
			Log.e("SplachActivity", ex.getMessage());
		}

		this.mAnim = new AlphaAnimation(0.5F, 1.0F);
		this.mAnim.setDuration(duration);
	}
	
	private void initListener() {
		this.mAnim.setAnimationListener(new Animation.AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				SplashActivity.this.mHaveFinishiAnim = true;
				if (SplashActivity.this.mHaveInitData) {
					SplashActivity.this.startApp();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
		});
		
		this.mHandler = new Handler(){
			
			public void handleMessage(Message msg) {
				SplashActivity.this.mHaveInitData = true;
				if (SplashActivity.this.mHaveFinishiAnim) {
					SplashActivity.this.startApp();
				}
				super.handleMessage(msg);
			}
			
		};
		
		(new Thread(){
			
			@Override
			public void run() {
				mHandler.handleMessage(mHandler.obtainMessage());
			}
			
		}).start();
	}
	
	private void startApp() {
		Method m = null;

		if (ApolloApplication.app().isFirstUse()) {
			ApolloApplication.app().setUsed();
			GuideActivity.startActivity(this);
		} else {
			try {
				m = mMainActivityClass.getMethod("startActivity", new Class[]{Context.class});
				m.invoke(mMainActivityClass, new Object[]{this});
			} catch (Exception ex) {
				Log.e("SplachActivity", ex.getMessage());
			}
		}

		super.finish();
	}
}
