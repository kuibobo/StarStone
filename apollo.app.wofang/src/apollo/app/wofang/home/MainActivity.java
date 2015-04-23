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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.model.Section;
import apollo.widget.HorizontalListView;

public class MainActivity extends BaseActivity {
	
	class SectionAdapter extends BaseAdapter {

		List<Section> mItems = new ArrayList<Section>();
		LayoutInflater mInflater = null;
		
		int mSelectedIndex;
		
		class SectionViewHolder {
			TextView sectionName;
		}
		
		public SectionAdapter(List<Section> items) {
			this.mItems = items;
			this.mInflater = LayoutInflater.from(MainActivity.this);
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
		
		public void setSelectedIndex(int position) {
			mSelectedIndex = position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SectionViewHolder holder = null;
			Section section = null;
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_main_section_list, null);
				
				holder = new SectionViewHolder();
				holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
				convertView.setTag(holder);
			} else {
				holder = (SectionViewHolder) convertView.getTag();
			}
			
			if (position == this.mSelectedIndex)
				convertView.setSelected(true);
			else
				convertView.setSelected(false);
			
			section = (Section) this.getItem(position);
			holder.sectionName.setText(section.getName());
			return convertView;
		}
	}
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private HorizontalListView mSectionListView = null;
	private SectionAdapter mSectionAdapter = null;
	
	private List<Section> mSections = null;
	
		
	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		
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
		this.mSectionListView = (HorizontalListView) super.findViewById(R.id.section_list);
		this.mSectionListView.setAdapter(this.mSectionAdapter);
		this.mSectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MainActivity.this.mSectionAdapter.setSelectedIndex(position);
				MainActivity.this.mSectionAdapter.notifyDataSetChanged();
			}
			
			
		});
		
	}
	
	private void initListener() {
		
	}
}
