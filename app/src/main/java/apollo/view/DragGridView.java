package apollo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import apollo.adapter.SectionAdapter;
import apollo.app.wofang.R;
import apollo.data.model.Section;

public class DragGridView extends GridView {

	public interface SwapItemHandle {
		void swap(int index1, int index2);
	}
	private SwapItemHandle mSwapItemHandle;
	public void setSwapItemHandle(SwapItemHandle h) {
		mSwapItemHandle = h;
	}

	/** 拖拽时经过的Item的位置**/
	private int mMoveOverPosition;
	
	/** 相对DragGridView左上角的X偏移量**/
	private int mDownX;
	
	/** 相对DragGridView左上角的Y偏移量**/
	private int mDownY;
	
	/** 按下Item后X的坐标，其值是相对当前Item的偏移量**/
	private int mPoint2ItemOffsetTop;
	
	/** 按下Item后Y的坐标，其值是相对当前Item的偏移量**/
	private int mPoint2ItemOffsetLeft;
	
	/** DragGridView 相对屏幕顶部的偏移量*/
	private int mOffsetTop;
	
	/** DragGridView 相对屏幕左边的偏移量*/
	private int mOffsetLeft;
	
	/** 动画时间 **/
	private int mDuration;
	
	/** Item是否在移动 **/
	private boolean mIsMoving;
	
	/** 是否允许拖拽 **/
	private boolean mDragEnable;

	/** 是否处于拖拽模式 **/
	private boolean mIsDragMode;
	
	/** WindowManager管理器 */
	private WindowManager mWindowManager;
	
	/** 拖动的时候ITEM对应的VIEW */
	private ImageView mDragImageView;
	
	/** 长按Item对应postion */
	private int mCurrentItemPosition;

	/** 当前移动的元素数量 */
	private int mMovingItems = 0;
	
	/** 按下后的Item对应的View */
	private View mCurrentItemView;
	
	/** 拖拽的时候显示的位图 **/
	private Bitmap mDragBmp;

	/** 数据源适配器 */
	private SectionAdapter mAdapter = null;

	/** 振动器 **/
	private Vibrator mVibrator; 
	
	private WindowManager.LayoutParams mDragViewLayoutParams;

	/** 动画监听器 */
	private Animation mLastAnimation = null;


	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DragGridView);
		
		this.mDuration = ta.getInteger(R.styleable.DragGridView_animationDuration, 750);
		this.mDragEnable = ta.getBoolean(R.styleable.DragGridView_dragEnable, true);
		this.mIsDragMode = ta.getBoolean(R.styleable.DragGridView_dragMode, false);

		if (isInEditMode())
			return ;

		this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		this.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		this.setDragMode(this.mIsDragMode);
	}

	public void setDragMode(boolean b) {
		this.mIsDragMode = b;

		if (this.mAdapter != null) {
			this.mAdapter.setEditMode(this.mIsDragMode);
			this.mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);

		mAdapter = (SectionAdapter) adapter;
		mAdapter.setEditMode(this.mIsDragMode);
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
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (this.mDragEnable == true && ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (this.mIsDragMode == true) {
				this.mDownX = (int) ev.getX();
				this.mDownY = (int) ev.getY();

				this.mCurrentItemPosition = pointToPosition(mDownX, mDownY);
				if (this.mCurrentItemPosition == -1)
					return super.onInterceptTouchEvent(ev);

				if (((Section)this.mAdapter.getItem(this.mCurrentItemPosition)).isLocked())
					return super.onInterceptTouchEvent(ev);

				this.mCurrentItemView = getChildAt(mCurrentItemPosition - getFirstVisiblePosition());

				// 获得当前 point 到 item的偏移量
				mPoint2ItemOffsetLeft = mDownX - mCurrentItemView.getLeft();
				mPoint2ItemOffsetTop = mDownY - mCurrentItemView.getTop();

				// 计算点击所属是否是关闭按钮
				if (mAdapter.isTouchClose(mCurrentItemView, mPoint2ItemOffsetTop, mPoint2ItemOffsetLeft))
					return super.onInterceptTouchEvent(ev);

				// 获得 DragGridView 相对屏幕的偏移量
				mOffsetLeft = (int) (ev.getRawX() - mDownX);
				mOffsetTop = (int) (ev.getRawY() - mDownY);

				// 将当前按下的Item设置不显示, 需要在停止拖放onDrop的时候还原
				mAdapter.setDragItemPosition(this.mCurrentItemPosition);
				mAdapter.notifyDataSetChanged();

				// 创建一个拖拽的位图
				mCurrentItemView.destroyDrawingCache();
				mCurrentItemView.setDrawingCacheEnabled(true);

				mDragBmp = Bitmap.createBitmap(mCurrentItemView.getDrawingCache());
				mIsMoving = false;
				startDrag(mDragBmp,
						mDownX - mPoint2ItemOffsetLeft + mOffsetLeft,
						mDownY - mPoint2ItemOffsetTop + mOffsetTop);

				requestDisallowInterceptTouchEvent(true);
				return false;

			} else {
				this.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						DragGridView.this.mIsDragMode = true;

						// 小振一下
						mVibrator.vibrate(50L);
						mAdapter.setEditMode(true);
						mAdapter.notifyDataSetChanged();
						return false;
					}
				});
				return false;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
	 
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (this.mDragImageView != null && this.mCurrentItemPosition != AdapterView.INVALID_POSITION) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			
			switch(ev.getAction()) {
				
			case MotionEvent.ACTION_MOVE:
				this.onDrag(x, y);
				this.onMove(x, y);
 
				smoothScrollBy(300, 30);
				break;
	
			case MotionEvent.ACTION_UP:				
				this.stopDrag();
				this.onDrop();
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	class TranslateAnimationListener implements Animation.AnimationListener {

		int moveItemPosition = 0;
		int prevItemPosition = 0;

		@Override
		public void onAnimationStart(Animation animation) {
			Log.i("DragGridView", "animation:" + animation);
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (mLastAnimation == animation) {
				mAdapter.setDragItemPosition(moveItemPosition);
				Log.i("DragGridView", "drag item:" + moveItemPosition);
				mCurrentItemPosition = moveItemPosition;
				Log.i("DragGridView", "current:" + mCurrentItemPosition);
				mAdapter.notifyDataSetChanged();
			}
			mMovingItems --;
			Log.i("DragGridView", "items:" + mMovingItems);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}
	}

	private void onMove(int x, int y) {
		// 当前滑动经过的的Item的position 
		mMoveOverPosition = pointToPosition(x, y);

		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				// 需要移动的个数
				int move_items = -1;

				if (mMoveOverPosition == -1)
					return;

				if (mMovingItems > 0)
					return;

				move_items = mCurrentItemPosition - mMoveOverPosition;
				if (move_items == 0)
					return;

				if (mAdapter.getSection(mMoveOverPosition).isLocked())
					return;

				Log.i("DragGridView", "current:" + mCurrentItemPosition + " moveover:" + mMoveOverPosition);
				move_items = Math.abs(move_items);
				for (int i = 0; i < move_items; i++) {
					int move_item_position = 0;
					int prev_item_position = 0;

					if ( mCurrentItemPosition > mMoveOverPosition ) {
						move_item_position = mCurrentItemPosition - i - 1;
						prev_item_position = move_item_position + 1;
					} else {
						move_item_position = mCurrentItemPosition + i + 1;
						prev_item_position = move_item_position - 1;
					}
					moveItem(move_item_position, prev_item_position);
				}
			}
		}, 500L);

	}

	private void moveItem(int move_item_position, int prev_item_position) {
		View move_item_view = null;
		View prev_item_view = null;
		TranslateAnimation animation = null;
		TranslateAnimationListener animationListener = null;
		int[] move_item_location = null;
		int[] prev_item_location = null;
		int fromXDelta; int fromYDelta;
		int toXDelta; int toYDelta;

		if (move_item_position < 0 || prev_item_position < 0)
			return;

		move_item_location = new int[2];
		prev_item_location = new int[2];
		move_item_view = getChildAt(move_item_position);
		move_item_view.getLocationInWindow(move_item_location);
		move_item_view.setVisibility(View.GONE);

		prev_item_view = getChildAt(prev_item_position);
		prev_item_view.getLocationInWindow(prev_item_location);

		fromXDelta = 0;
		fromYDelta = 0;
		toXDelta = prev_item_location[0] - move_item_location[0];
		toYDelta = prev_item_location[1] - move_item_location[1];

		animationListener = new TranslateAnimationListener();
		animationListener.moveItemPosition = move_item_position;
		animationListener.prevItemPosition = prev_item_position;

		animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		animation.setDuration(mDuration);
		animation.setAnimationListener(animationListener);
		mLastAnimation = animation;

		Section s = null;
		s = mAdapter.removeItem(prev_item_position);
		mAdapter.addItem(move_item_position, s);

		move_item_view.clearAnimation();
		move_item_view.startAnimation(animation);
		mMovingItems++;
		Log.i("DragGridView", "move item:" + move_item_position
				+ " pre item:" + prev_item_position + " items:" + mMovingItems + " animation:" + animation);
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
	
	private void onDrop() {
		mAdapter.setDragItemPosition(-1);
		mAdapter.notifyDataSetChanged();
		mMovingItems = 0;
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
