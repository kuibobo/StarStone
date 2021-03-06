package apollo.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import apollo.app.wofang.R;
import apollo.data.model.Section;

public class SectionAdapter extends BaseAdapter {

	public interface RemoveHandle {
		void remove(int position);
	}

	private List<Section> mItems = new ArrayList<Section>();
	private LayoutInflater mInflater = null;
	private Animation mShakeAnimation = null;

	private int mLastItemVisibility = View.VISIBLE;
	private int mDragItemPosition = -1;
	private int mSelectedItemPosition = -1;
	private boolean mIsEditMode = false;

	private RemoveHandle mRemoveHandle;

	class SectionViewHolder {
		TextView sectionName;
		ImageView removeButton;
	}
	
	public SectionAdapter(Context context, List<Section> items) {
		this.mItems = items;
		this.mInflater = LayoutInflater.from(context);
		this.mShakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
	}

	public void setRemoveHandle(RemoveHandle handle) {
		this.mRemoveHandle = handle;
	}

	@Override
	public int getCount() {
		return this.mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return getSection(position);
	}

	public Section getSection(int position) {
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
		View close_view = ((SectionViewHolder)parent.getTag()).removeButton;

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
			holder.removeButton = (ImageView) convertView.findViewById(R.id.btn_remove);
			holder.removeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mRemoveHandle != null)
						mRemoveHandle.remove(position);
				}
			});

			convertView.setTag(holder);
		} else {
			holder = (SectionViewHolder) convertView.getTag();
		}

		section = (Section) this.getItem(position);
		holder.sectionName.setText(section.getName());
		holder.sectionName.setTag(section);
		holder.sectionName.setSelected(false);
		if (position == this.mSelectedItemPosition) {
			holder.sectionName.setSelected(true);
		}
		holder.sectionName.setEnabled(true);
		if (this.mIsEditMode && section.isLocked()) {
			holder.sectionName.setEnabled(false);
		}

		holder.removeButton.setVisibility(this.mIsEditMode && !section.isLocked() ? View.VISIBLE : View.GONE);

		convertView.setVisibility(View.VISIBLE);
		if (position == (this.getCount() - 1)) {
			convertView.setVisibility(this.mLastItemVisibility);
		}
		if (position == this.mDragItemPosition) {
			convertView.setVisibility(View.GONE);
		}


		if (this.mIsEditMode && !section.isLocked() && convertView.getVisibility() == View.VISIBLE) {
			convertView.setAnimation(mShakeAnimation);
		} else {
			convertView.clearAnimation();
		}
		return convertView;
	}

	public void addItem(Section s) {
		this.mItems.add(s);
	}

	public void addItem(int position, Section s) {
		this.mItems.add(position, s);
	}
	
	public void removeItem(Section s) {
		this.mItems.remove(s);
	}

	public Section removeItem(int position) {
		return this.mItems.remove(position);
	}

	public void setLastItemVisibility(int visibility) {
		 this.mLastItemVisibility = visibility;
	}

	public void setDragItemPosition(int position) {
		this.mDragItemPosition = position;
	}

	public void setSelectedItemPosition(int position) {
		this.mSelectedItemPosition = position;
	}
}
