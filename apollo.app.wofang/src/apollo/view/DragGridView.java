package apollo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class DragGridView extends GridView {

	
	public DragGridView(Context context) {
		super(context);
	}
	
	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public DragGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 int heightSpec;
		 
	        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
	            // The great Android "hackatlon", the love, the magic.
	            // The two leftmost bits in the height measure spec have
	            // a special meaning, hence we can't use them to describe height.
	            heightSpec = MeasureSpec.makeMeasureSpec(
	                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
	        }
	        else {
	            // Any other height should be respected as is.
	            heightSpec = heightMeasureSpec;
	        }
	 
	        super.onMeasure(widthMeasureSpec, heightSpec);

	}

}
