package apollo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ColumnHorizontalScrollView extends HorizontalScrollView {

	public ColumnHorizontalScrollView(Context context) {
        super(context, null);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    
	public ColumnHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
