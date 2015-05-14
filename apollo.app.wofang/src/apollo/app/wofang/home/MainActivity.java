package apollo.app.wofang.home;

import java.util.ArrayList;
import java.util.List;

import org.miscwidgets.widget.Panel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.model.Section;
import apollo.view.DragGridView;
import apollo.widget.HorizontalListView;

public class MainActivity extends BaseActivity {
	
	class SectionAdapter extends BaseAdapter {

		List<Section> mItems = new ArrayList<Section>();
		LayoutInflater mInflater = null;
				
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
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SectionViewHolder holder = null;
			Section section = null;
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_list_main_section, null);
				
				holder = new SectionViewHolder();
				holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
				convertView.setTag(holder);
			} else {
				holder = (SectionViewHolder) convertView.getTag();
			}
						
			section = (Section) this.getItem(position);
			holder.sectionName.setText(section.getName());
			holder.sectionName.setTag(section);
			return convertView;
		}
	}
	

	private ViewPager mViewPager = null;
	private View mView = null;
	private LinearLayout mLayoutSections = null; 
	private RelativeLayout mLayoutMain = null;
	private RelativeLayout mLayoutBottom = null;
	private LinearLayout mLayoutTop = null;
	private HorizontalListView mSectionListView = null;
	private SectionAdapter mSectionAdapterCurrent = null;
	private SectionAdapter mSectionAdapterSource = null;
	private Button mBtnSecitonAdd = null;
	
	private Panel mSectionsPanel = null;
	private DragGridView mDragGridViewCurrent = null;
	private DragGridView mDragGridViewSource = null;
	
	private List<Section> mSectionsCurrent = null;
	private List<Section> mSectionsSource = null;
	
	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		

		this.mSectionsCurrent = new ArrayList<Section>();
		this.mSectionsSource = new ArrayList<Section>();
		
		this.initView();
		this.initListener();
		
		// test code
		
		for(int i=0; i<4; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSectionsCurrent.add(s);
		}
		this.mSectionAdapterCurrent.notifyDataSetChanged();
		// end test code
		
		// test code
		
		for(int i=0; i<20; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSectionsSource.add(s);
		}
		this.mSectionAdapterSource.notifyDataSetChanged();
		// end test code
	}
	
	
	@SuppressLint("InflateParams")
	private void initView() {
		View view = null;
		
		this.mSectionAdapterCurrent = new SectionAdapter(this.mSectionsCurrent);
		this.mSectionAdapterSource = new SectionAdapter(this.mSectionsSource);
		
		view = super.getLayoutInflater().inflate(R.layout.item_layout_main_sections, null);
		this.mSectionsPanel = (Panel) view.findViewById(R.id.layout_main_sections);
		this.mDragGridViewCurrent = (DragGridView) view.findViewById(R.id.grid_current);
		this.mDragGridViewCurrent.setAdapter(this.mSectionAdapterCurrent);
		
		this.mDragGridViewSource = (DragGridView) view.findViewById(R.id.grid_source);
		this.mDragGridViewSource.setAdapter(this.mSectionAdapterSource);
		
		this.mLayoutSections = (LinearLayout) super.findViewById(R.id.layout_sections);
		this.mLayoutTop = (LinearLayout) super.findViewById(R.id.layout_top);
		this.mLayoutBottom =  (RelativeLayout) super.findViewById(R.id.layout_bottom);
 
		
		this.mBtnSecitonAdd = (Button) super.findViewById(R.id.btn_section_add);
		this.mSectionListView = (HorizontalListView) super.findViewById(R.id.section_list);
		this.mSectionListView.setAdapter(this.mSectionAdapterCurrent);		
		
		this.mLayoutMain = (RelativeLayout) super.findViewById(R.id.layout_main);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		this.mLayoutBottom.addView(this.mSectionsPanel, params);
	}
	
	private void initListener() {
		this.mSectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MainActivity.this.mSectionListView.setSelection(position);
			}
			
			
		});
		
		this.mBtnSecitonAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (MainActivity.this.mSectionsPanel.isOpen()) {
					MainActivity.this.mSectionsPanel.setOpen(false, true);
				} else {
					MainActivity.this.mSectionsPanel.setOpen(true, true);
				}
			}
		});
		
		this.mDragGridViewCurrent.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textView = (TextView) view.findViewById(R.id.section_name);
				Section section = (Section)textView.getTag();
				
				MainActivity.this.mSectionsCurrent.remove(section);
				MainActivity.this.mSectionAdapterCurrent.notifyDataSetChanged();
				
				MainActivity.this.mSectionsSource.add(section);
				MainActivity.this.mSectionAdapterSource.notifyDataSetChanged();
			}
			
		});
		
		this.mDragGridViewSource.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 
				TextView textView = (TextView) view.findViewById(R.id.section_name);
				Section section = (Section)textView.getTag();
				
				MainActivity.this.mSectionsCurrent.add(section);
				MainActivity.this.mSectionAdapterCurrent.notifyDataSetChanged();
				
				MainActivity.this.mSectionsSource.remove(section);
				MainActivity.this.mSectionAdapterSource.notifyDataSetChanged();
			}
			
			
		});
	}
}
