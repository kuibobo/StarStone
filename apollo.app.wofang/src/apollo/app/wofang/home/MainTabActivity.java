package apollo.app.wofang.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.model.Section;

public class MainTabActivity extends BaseActivity {
	
	class SectionAdapter extends BaseAdapter {

		List<Section> mItems = new ArrayList<Section>();
		LayoutInflater mInflater = null;
		
		class SectionViewHolder {
			TextView sectionName;
		}
		
		public SectionAdapter(List<Section> items) {
			this.mItems = items;
			this.mInflater = LayoutInflater.from(MainTabActivity.this);
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
				convertView = mInflater.inflate(R.layout.item_maintabs_section_list, null);
				
				holder = new SectionViewHolder();
				holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
				convertView.setTag(holder);
			} else {
				holder = (SectionViewHolder) convertView.getTag();
			}
			
			section = (Section) this.getItem(position);
			holder.sectionName.setText(section.getName());
			return convertView;
		}
	}
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private ListView mSectionListView = null;
	private HorizontalScrollView mScrollView = null;
	private SectionAdapter mSectionAdapter = null;
	
	private List<Section> mSections = null;
	
		
	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainTabActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_maintabs);
		
		this.mSections = new ArrayList<Section>();

		this.initView();
		this.initListener();
		
		// test code
		for(int i=0; i<10; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSections.add(s);
		}
		this.mSectionAdapter.notifyDataSetChanged();
		// end test code
	}
	
	
	private void initView() {
		this.mSectionAdapter = new SectionAdapter(this.mSections);
		this.mScrollView = (HorizontalScrollView) super.findViewById(R.id.scroll_view);
		this.mSectionListView = (ListView) super.findViewById(R.id.section_list);
		this.mSectionListView.setAdapter(this.mSectionAdapter);
	}
	
	private void initListener() {
		
	}
}
