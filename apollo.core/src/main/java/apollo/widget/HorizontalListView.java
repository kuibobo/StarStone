package apollo.widget;

/* 
 * HorizontalListView.java v1.5 
 * 
 *  
 * The MIT License 
 * Copyright (c) 2011 Paul Soucy (paul@dev-smart.com) 
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions: 
 *  
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software. 
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE. 
 * 
 */ 

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import apollo.core.R;


public class HorizontalListView extends AdapterView<ListAdapter> implements
		OnGestureListener, OnDoubleTapListener {

	private ListAdapter mAdapter = null;
	private Scroller mScroller = null;
	private GestureDetector mGesture = null;
	private View mPreSelectedView = null;
	
	private int mRightItemPosition = 0;
	private int mLeftItemPosition = -1;
	private int mSelectedPosition = 0;
	private int mCurrentX = 0;
	private int mNextX = 0;
	private int mMaxX = 0;
	private int mDisplayOffset = 0;
	private int mRequestedHorizontalSpacing = 0;
	private boolean mDataChanged = false;

	private DataSetObserver mDataObserver = new DataSetObserver(){
		@Override  
        public void onChanged() {  
            synchronized(HorizontalListView.this){  
            	HorizontalListView.this.mDataChanged = true;  
            }  
            HorizontalListView.this.invalidate();  
            HorizontalListView.this.requestLayout();  
        }  
  
        @Override  
        public void onInvalidated() {  
        	HorizontalListView.this.reset();  
        	HorizontalListView.this.invalidate();  
        	HorizontalListView.this.requestLayout();  
        }  
	};
	
	public HorizontalListView(Context context) {  
        this(context, null);
    }  
  
    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalListView);
		int hSpacing = a.getDimensionPixelOffset(
				R.styleable.HorizontalListView_horizontalSpacing, 0);
		this.setHorizontalSpacing(hSpacing);
		this.initViews();
	}

	@Override
	public ListAdapter getAdapter() {
		return this.mAdapter;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (this.mAdapter != null) 
			this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
		
		this.mAdapter = adapter;
		this.mAdapter.registerDataSetObserver(this.mDataObserver);
		this.reset();
	}

	@Override
	public View getSelectedView() {
		return null;
	}

	@Override
	public void setSelection(int position) {
		View view = null;

		if (mPreSelectedView != null)
			mPreSelectedView.setSelected(false);
		
		this.mSelectedPosition = position;
		position -= (this.mLeftItemPosition + 1);
 
		if (position >= 0 && position < super.getChildCount()) {
			view = super.getChildAt(position);
			view.setSelected(true);
			mPreSelectedView = view;
		} 
		super.requestLayout();
	}
	
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    	super.onLayout(changed, left, top, right, bottom);
    	
    	if (this.mAdapter == null)
    		return;
    	
    	if(this.mDataChanged){  
    		int oldX = this.mCurrentX;
    				
            this.initViews();  
            super.removeAllViewsInLayout(); 
            
            this.mNextX = oldX;
            this.mDataChanged = false;  
        }  
		
		if (this.mScroller.computeScrollOffset()) 
			this.mNextX = this.mScroller.getCurrX();
		
		if (this.mNextX <= 0) {
			this.mNextX = 0;
			this.mScroller.forceFinished(true);
		}
		
		if (this.mNextX > this.mMaxX) {
			this.mNextX = this.mMaxX;
			this.mScroller.forceFinished(true);
		}
		
		int offset = mCurrentX - mNextX;  
		this.removeNonVisibleItems(offset);
		this.fillList(offset);
		this.positionItems(offset);
		this.setSelection(this.mSelectedPosition);
		super.invalidate();

		this.mCurrentX = this.mNextX;
		
		if (!this.mScroller.isFinished()) {
			post(new Runnable() {
				@Override
				public void run() {
					HorizontalListView.this.requestLayout();
				}
			});

		}
    }
    
    @Override
	public boolean onDown(MotionEvent e) {
		this.mScroller.forceFinished(true);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		View view = null;
		int pos = -1;
		long id = -1;
		
		for(int i=0; i<super.getChildCount(); i++) {
			view = super.getChildAt(i);
			if (this.isEventWithinView(e, view)) {
				if (super.getOnItemClickListener() != null) {
					pos = this.mLeftItemPosition + 1 + i;
					id = this.mAdapter.getItemId(pos);
					super.getOnItemClickListener().onItemClick(this, view, pos, id);
				}
				
				if (super.getOnItemSelectedListener() != null) {
					pos = this.mLeftItemPosition + 1 + i;
					id = this.mAdapter.getItemId(pos);
					super.getOnItemSelectedListener().onItemSelected(this, view, pos, id);
				}
			}
		}
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
		this.mNextX += (int) distanceX;
		super.requestLayout();  
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		View view = null;
		int pos = -1;
		long id = -1;
		
		for(int i=0; i<super.getChildCount(); i++) {
			view = super.getChildAt(i);
			if (this.isEventWithinView(e, view)) {
				if (super.getOnItemLongClickListener() != null) {
					pos = this.mLeftItemPosition + 1 + i;
					id = this.mAdapter.getItemId(pos);
					super.getOnItemLongClickListener().onItemLongClick(this, view, pos, id);
				}
				
			}
		}
  
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		this.mScroller.fling(mNextX, 0, (int)-velocityX, 0, 0, this.mMaxX, 0, 0);  
		super.requestLayout();
		return true;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean handled = super.dispatchTouchEvent(event);
		handled |= this.mGesture.onTouchEvent(event);
		return handled;
	}

	public void setHorizontalSpacing(int horizontalSpacing) {
		if (horizontalSpacing != mRequestedHorizontalSpacing) {
			mRequestedHorizontalSpacing = horizontalSpacing;
			requestLayoutIfNecessary();
		}
	}

	public int getRequestedHorizontalSpacing() {
		return mRequestedHorizontalSpacing;
	}

	void requestLayoutIfNecessary() {
		if (getChildCount() > 0) {
			reset();
			requestLayout();
			invalidate();
		}
	}

	private boolean isEventWithinView(MotionEvent e, View view) {
		Rect viewRect = new Rect();
		int[] childPosition = new int[2];
		view.getLocationOnScreen(childPosition);
		int left = childPosition[0];
		int right = left + view.getWidth();
		int top = childPosition[1];
		int bottom = top + view.getHeight();
		viewRect.set(left, top, right, bottom);
		return viewRect.contains((int) e.getRawX(), (int) e.getRawY());
	}
	 
	private void reset() {
		this.initViews();  
		super.removeAllViewsInLayout();  
		super.requestLayout();  
	}
	
    private void initViews() {
    	this.mRightItemPosition = 0;
    	this.mLeftItemPosition = -1;
    	this.mCurrentX = 0;
    	this.mNextX = 0;
    	this.mDisplayOffset = 0;
    	this.mMaxX = Integer.MAX_VALUE;
    	
    	this.mScroller = new Scroller(super.getContext());
    	this.mGesture = new GestureDetector(super.getContext(), this);
    }
    
    private void removeNonVisibleItems(int offset) {
    	View view = null;
    	
    	// 清理左边界看不到的内容
    	view = super.getChildAt(0);
    	while(view != null && view.getRight() + offset <= 0) {
    		this.mDisplayOffset += view.getMeasuredWidth();
    		super.removeViewInLayout(view);
    		this.mLeftItemPosition ++;
    		
    		view = super.getChildAt(0);
    	}
    	
    	// 清理右边界看不到的内容
    	view = super.getChildAt(super.getChildCount() - 1);
    	while(view != null && view.getLeft() + offset >= super.getWidth()) {
    		super.removeViewInLayout(view);
    		this.mRightItemPosition --;
    		view = super.getChildAt(super.getChildCount() - 1);
    	}
    }
    
    private void fillList(int offset) {
    	int edge = 0;
    	View view = null;
    	
    	view = super.getChildAt(super.getChildCount() - 1);
    	if (view != null)
    		edge = view.getRight();
    	this.fillListRight(edge, offset);
    	
    	edge = 0;
    	view = super.getChildAt(0);
    	if (view != null)
    		edge = view.getLeft();
    	this.fillListLeft(edge, offset);
    }
    
    private void fillListLeft(int leftEdge, int offset) {
    	while(leftEdge + offset > 0 && this.mLeftItemPosition >= 0) {
    		View view = null;
    		
    		view = this.mAdapter.getView(this.mLeftItemPosition, null, this);
    		view.setSelected(this.mLeftItemPosition == this.mSelectedPosition);
    		
    		this.addAndMeasureChild(view, 0);
    		this.mDisplayOffset -= view.getMeasuredWidth();
    		this.mLeftItemPosition--;
    	}
    }
    
    private void fillListRight(int rightEdge,  int offset) {
    	while(rightEdge + offset < super.getWidth() && 
    			this.mRightItemPosition < this.mAdapter.getCount() ) {
    		View view = null;
    		
    		view = this.mAdapter.getView(this.mRightItemPosition, null, this);
    		view.setSelected(this.mRightItemPosition == this.mSelectedPosition);
    		
    		this.addAndMeasureChild(view, -1);
    		rightEdge += view.getMeasuredWidth();
    		
    		this.mRightItemPosition++;
    	}
    	
    	if (this.mRightItemPosition == this.mAdapter.getCount() )
    		this.mMaxX = this.mCurrentX + rightEdge - getWidth();
    	
    	if (this.mMaxX < 0)
    		this.mMaxX = 0;
    }
    
    private void addAndMeasureChild(View view, int index) {
    	LayoutParams params = null;
    	
    	params = view.getLayoutParams();
    	if (params == null) {
			params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    	}
    	super.addViewInLayout(view, index, params, true);
    	
    	view.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),  
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
    }
    
    
    private void positionItems(int offset) {
    	int top = 0;
    	int left = 0;

		left = (mDisplayOffset += offset) - (this.mRequestedHorizontalSpacing >> 1);
    	for(int i=0; i<super.getChildCount(); i++) {
    		View v = super.getChildAt(i);
    		int width = v.getMeasuredWidth() + this.mRequestedHorizontalSpacing;
    		int height = v.getMeasuredHeight();
    		
    		v.layout(left,  top, left + width, top + height);
    		left += width;
    	}
    }
}  