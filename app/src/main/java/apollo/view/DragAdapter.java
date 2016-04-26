package apollo.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import apollo.app.wofang.R;
import apollo.data.model.Section;

public class DragAdapter extends BaseAdapter {

	public interface CloseHandle {
		void close(int position);
	}

	private List<Section> mItems = new ArrayList<Section>();
	private LayoutInflater mInflater = null;
	
	private int mLastItemVisibility = View.VISIBLE;
	private int mSelectedItemVisibility = View.VISIBLE;
	private int mSelectedItemPosition;
	private boolean mIsEditMode = false;

	private CloseHandle mCloseHandle;

	class SectionViewHolder {
		TextView sectionName;
		ImageView closeButton;
	}
	
	public DragAdapter(Context context, List<Section> items) {
		this.mItems = items;
		this.mInflater = LayoutInflater.from(context);
	}

	public void setCloseHandle(CloseHandle handle) {
		this.mCloseHandle = handle;
	}

	@Override
	public int getCount() {
		return this.mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ((Section) this.getItem(position)).getId();
	}
	
	public void setEditMode(boolean b) {
		this.mIsEditMode = b;
	}

	public boolean isTouchClose(View parent, int top, int left) {
		Rect close_recv = new Rect();
		View close_view = ((SectionViewHolder)parent.getTag()).closeButton;

		close_view.getDrawingRect(close_recv);
		return close_recv.left < left && left < close_recv.right &&
				close_recv.top < top && top < close_recv.bottom ;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SectionViewHolder holder = null;
		Section section = null;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_main_section_grid_item, null);
			
			holder = new SectionViewHolder();
			holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
			holder.closeButton = (ImageView) convertView.findViewById(R.id.btn_close);
			holder.closeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mCloseHandle != null)
						mCloseHandle.close(position);
				}
			});

			convertView.setTag(holder);
		} else {
			holder = (SectionViewHolder) convertView.getTag();
		}

		section = (Section) this.getItem(position);
		holder.sectionName.setText(section.getName());
		holder.sectionName.setTag(section);
		holder.closeButton.setVisibility(this.mIsEditMode ? View.VISIBLE : View.GONE);

		convertView.setVisibility(View.VISIBLE);
		
		if (position == (this.getCount() - 1)) {
			convertView.setVisibility(this.mLastItemVisibility);
		}
		
		if (position == this.mSelectedItemPosition) {
			convertView.setVisibility(this.mSelectedItemVisibility);
		}
		return convertView;
	}
	
	public void swap(int index1, int index2) {
		this.mSelectedItemPosition = index2;
		Collections.swap(this.mItems, index1, index2);
		this.notifyDataSetChanged();
	}
	
	public void addItem(Section s) {
		this.mItems.add(s);
		this.notifyDataSetChanged();
	}
	
	public void removeItem(Section s) {
		this.mItems.remove(s);
		this.notifyDataSetChanged();
	}
	
	public void setLastItemVisibility(int visibility) {
		 this.mLastItemVisibility = visibility;
	}
	
	public void setSelectedItemVisibility(int visibility) {
		 this.mSelectedItemVisibility = visibility;
	}

	public void setSelectedItemPosition(int position) {
		this.mSelectedItemPosition = position;		
	}
}
