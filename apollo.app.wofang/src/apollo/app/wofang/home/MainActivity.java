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
	private SectionAdapter mSectionAdapter = null;
	private Button mBtnSecitonAdd = null;
	private List<Section> mSections = null;
	private Panel mSectionsPanel = null;
		
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
		for(int i=0; i<20; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);
			
			this.mSections.add(s);
		}
		this.mSectionAdapter.notifyDataSetChanged();
		// end test code
	}
	
	
	@SuppressLint("InflateParams")
	private void initView() {
		View view = null;
		
		view = super.getLayoutInflater().inflate(R.layout.item_layout_main_sections, null);
		this.mSectionsPanel = (Panel) view.findViewById(R.id.layout_main_sections);
		
		this.mLayoutSections = (LinearLayout) super.findViewById(R.id.layout_sections);
		this.mLayoutTop = (LinearLayout) super.findViewById(R.id.layout_top);
		this.mLayoutBottom =  (RelativeLayout) super.findViewById(R.id.layout_bottom);
		//this.mLayoutTop.addView(this.mSectionsPanel);
		
		this.mBtnSecitonAdd = (Button) super.findViewById(R.id.btn_section_add);
		this.mSectionAdapter = new SectionAdapter(this.mSections);
		this.mSectionListView = (HorizontalListView) super.findViewById(R.id.section_list);
		this.mSectionListView.setAdapter(this.mSectionAdapter);		
		
		this.mLayoutMain = (RelativeLayout) super.findViewById(R.id.layout_main);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		//params.addRule(RelativeLayout.BELOW, R.id.layout_sections);
		 
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
	}
}
