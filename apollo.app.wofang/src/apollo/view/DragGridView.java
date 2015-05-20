package apollo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import apollo.app.wofang.R;

public class DragGridView extends GridView {

	private int mDownX;
	private int mDownY;
	private int mPoint2ItemOffsetTop;
	private int mPoint2ItemOffsetLeft;
	private int mOffsetTop;
	private int mOffsetLeft;
	private int mDuration;
	
	private boolean mIsMoving;
	private boolean mDragEnable;
	/** WindowManager管理器 */
	private WindowManager mWindowManager;
	
	/** 拖动的时候ITEM对应的VIEW */
	private ImageView mDragImageView;
	
	/** 长按Item对应postion */
	private int mCurrentItemPosition;
	
	private View mCurrentItemView;
	
	private Bitmap mDragBmp;
	
	private Vibrator mVibrator; 
	
	private WindowManager.LayoutParams mDragViewLayoutParams;
	
	
	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DragGridView);
		
		this.mDuration = a.getInteger(R.styleable.DragGridView_animationDuration, 750);	
		this.mDragEnable = a.getBoolean(R.styleable.DragGridView_dragEnable, true);
		
		this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		this.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec;

		if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
			// The great Android "hackatlon", the love, the magic.
			// The two leftmost bits in the height measure spec have
			// a special meaning, hence we can't use them to describe height.
			heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
		} else {
			// Any other height should be respected as is.
			heightSpec = heightMeasureSpec;
		}
		super.onMeasure(widthMeasureSpec, heightSpec);
	}
	
	@Override
	public boolean onInterceptTouchEvent(final MotionEvent ev) {
		switch(ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			if (this.mDragEnable == false)
				break;
			
			this.mDownX = (int)ev.getX();
			this.mDownY = (int)ev.getY();
			
			mCurrentItemPosition = pointToPosition(mDownX, mDownY);
			this.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					DragAdapter adapter = (DragAdapter) getAdapter();
					
					mCurrentItemPosition = position;//pointToPosition(mDownX, mDownY);
					mCurrentItemView = getChildAt(mCurrentItemPosition - getFirstVisiblePosition());
					mCurrentItemView.setSelected(true);
					
					// 获得当前 point 到 item的偏移量
					mPoint2ItemOffsetLeft = mDownX - mCurrentItemView.getLeft();
					mPoint2ItemOffsetTop = mDownY - mCurrentItemView.getTop();
					
					// 获得 DragGridView 相对屏幕的偏移量
					mOffsetLeft = (int) (ev.getRawX() - mDownX);
					mOffsetTop = (int) (ev.getRawY() - mDownY);
					
					mVibrator.vibrate(50L);
					
					mCurrentItemView.destroyDrawingCache();
					mCurrentItemView.setDrawingCacheEnabled(true);
					
					mDragBmp = Bitmap.createBitmap(mCurrentItemView.getDrawingCache());
					
					mIsMoving = false;
					
					adapter.setSelectedItemPosition(position);
					adapter.setSelectedItemVisibility(View.GONE);
					adapter.notifyDataSetChanged();
					
					startDrag(mDragBmp, 
							mDownX - mPoint2ItemOffsetLeft + mOffsetLeft,  
							mDownY - mPoint2ItemOffsetTop + mOffsetTop);
					return false;
				}

			});
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (this.mDragImageView != null && this.mCurrentItemPosition != AdapterView.INVALID_POSITION) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			
			switch(ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				break;
				
			case MotionEvent.ACTION_MOVE:
				this.onDrag(x, y);
				this.onMove(x, y);
				break;
	
			case MotionEvent.ACTION_UP:				
				this.stopDrag();
				this.onDrop(x, y);
				break;
			}
		}
		return super.onTouchEvent(ev);
	}
	
	private String mLastAnimation;
	private int mMoveOverPosition;
	
	private void onMove(int x, int y) {
		if (mIsMoving == true)
			return;
		
		mIsMoving = true;
		// 当前滑动经过的的Item的position 
		mMoveOverPosition = pointToPosition(x, y);
		if (mMoveOverPosition == -1) {
			mIsMoving = false;
			return;
		}
		
		// 需要移动的个数
		int move_items = this.mCurrentItemPosition - mMoveOverPosition;
		
		if (move_items == 0) {
			mIsMoving = false;
			return;
		}

		move_items = Math.abs(move_items);
		
		for (int i = 0; i < move_items; i++) {
			TranslateAnimation animation = null;
			View move_item_view = null;
			View prev_item_view = null;
			int[] move_item_location = new int[2];
			int[] prev_item_location = new int[2];
			int move_item_position = 0;
			int prev_item_position = 0;
			int fromXDelta; int fromYDelta; 
			int toXDelta; int toYDelta;
			
			if ( this.mCurrentItemPosition > mMoveOverPosition ) {
				move_item_position = this.mCurrentItemPosition - i - 1;
				prev_item_position = move_item_position + 1;
			} else {
				move_item_position = this.mCurrentItemPosition + i + 1;
				prev_item_position = move_item_position - 1;
			}
			
			move_item_view = getChildAt(move_item_position);
			move_item_view.getLocationInWindow(move_item_location);
			
			prev_item_view = getChildAt(prev_item_position);
			prev_item_view.getLocationInWindow(prev_item_location);
			
			fromXDelta = 0;
			fromYDelta = 0;
			
			toXDelta = prev_item_location[0] - move_item_location[0];
			toYDelta = prev_item_location[1] - move_item_location[1];
			
			animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
			animation.setDuration(mDuration);
			if (move_item_position == mMoveOverPosition) {
				this.mLastAnimation = animation.toString();
			}
			animation.setAnimationListener(new Animation.AnimationListener(){

				@Override
				public void onAnimationStart(Animation animation) {
					mIsMoving = true;
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (animation.toString().equals(mLastAnimation)) {
						DragAdapter adapter = null;
						View view = null;
						
						adapter = (DragAdapter) getAdapter();
						view = getChildAt(mCurrentItemPosition);
						view.setVisibility(View.GONE);
						
						adapter.swap(mCurrentItemPosition, mMoveOverPosition);
						
						mCurrentItemPosition = mMoveOverPosition;
						mIsMoving = false;
					}
					
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				
			});
			
			move_item_view.startAnimation(animation);
		}
	}
	
	private void onDrag(int x, int y) {
		if (this.mDragImageView != null) {
			
			/* 以下方式也可以获得到xy
			 int x = (int) ev.getRawX() - this.mPoint2ItemOffsetLeft;
			 int y = (int) ev.getRawY() - this.mPoint2ItemOffsetTop;
			 */
			this.mDragViewLayoutParams.alpha = 0.6f;
			this.mDragViewLayoutParams.x = x - this.mPoint2ItemOffsetLeft + this.mOffsetLeft;
			this.mDragViewLayoutParams.y = y - this.mPoint2ItemOffsetTop + this.mOffsetTop;
			
			this.mWindowManager.updateViewLayout(this.mDragImageView, this.mDragViewLayoutParams);
		}
	}
	
	private void onDrop(int x, int y) {
		DragAdapter adapter = (DragAdapter) this.getAdapter();
		
		adapter.setSelectedItemPosition(mCurrentItemPosition);
		adapter.setSelectedItemVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}
	
	private void startDrag(Bitmap bmp, int x, int y) {
		ImageView view = null;

		this.stopDrag();
		
		view = new ImageView(this.getContext());
		view.setImageBitmap(bmp);
		
		this.mDragViewLayoutParams = new WindowManager.LayoutParams();
		this.mDragViewLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
		this.mDragViewLayoutParams.x = x;
		this.mDragViewLayoutParams.y = y;
		this.mDragViewLayoutParams.width = bmp.getWidth();
		this.mDragViewLayoutParams.height = bmp.getHeight();
		this.mDragViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE                           
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE                           
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON                           
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		this.mDragViewLayoutParams.format = PixelFormat.TRANSLUCENT;
		this.mDragViewLayoutParams.windowAnimations = 0;
		
		this.mWindowManager.addView(view, this.mDragViewLayoutParams);
		this.mDragImageView = view;
	}
	
	private void stopDrag() {
		if (this.mDragImageView != null) {
			this.mWindowManager.removeView(this.mDragImageView);
			
			this.mDragImageView.setImageDrawable(null);
			this.mDragImageView = null;
		}
	}
}
