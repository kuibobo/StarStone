package apollo.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import apollo.app.wofang.R;
import apollo.data.model.Section;

public class DragAdapter extends BaseAdapter {

	private List<Section> mItems = new ArrayList<Section>();
	private LayoutInflater mInflater = null;
	
	private int mLastItemVisibility = View.VISIBLE;
	private int mSelectedItemVisibility = View.VISIBLE;
	private int mSelectedItemPosition;
	
	class SectionViewHolder {
		TextView sectionName;
	}
	
	public DragAdapter(Context context, List<Section> items) {
		this.mItems = items;
		this.mInflater = LayoutInflater.from(context);
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
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SectionViewHolder holder = null;
		Section section = null;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_grid_main_section, null);
			
			holder = new SectionViewHolder();
			holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
			convertView.setTag(holder);
		} else {
			holder = (SectionViewHolder) convertView.getTag();
		}

		section = (Section) this.getItem(position);
		holder.sectionName.setText(section.getName());
		holder.sectionName.setTag(section);
		
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
//		Section s = (Section) getItem(index1);
//
//		this.mSelectedItemPosition = index2;
//		if (index1 < index2) {
//			this.mItems.add(index2 + 1, s);
//			this.mItems.remove(index1);
//		} else {
//			this.mItems.add(index2, s);
//			this.mItems.remove(index1 + 1);
//		}
		this.mSelectedItemPosition = index2;
		Collections.swap(this.mItems, index1, index2);
		notifyDataSetChanged();
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
