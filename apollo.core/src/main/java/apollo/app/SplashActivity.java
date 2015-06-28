package apollo.app;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import apollo.core.R;
import apollo.util.ImageUtil;

public class SplashActivity extends BaseActivity {

	private AlphaAnimation mAnim = null;
	private ImageView mImage = null;
	private Handler mHandler;

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
		
		ActivityInfo info = null;
		long duration = 300L;

		try {
			info = this.getPackageManager().getActivityInfo(getComponentName(),
					PackageManager.GET_META_DATA);
			duration = Long.parseLong(info.metaData.getString("duration"));
		} catch (Exception ex) {
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
		ApolloApplication.app().startMainActivity(this);
		super.finish();
	}
}
