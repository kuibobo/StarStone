package apollo.app.wofang.home;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import apollo.app.BaseActivity;
import apollo.app.wofang.R;
import apollo.model.Section;
import apollo.view.DragAdapter;
import apollo.view.DragGridView;

public class TestActivity extends BaseActivity {

	private DragGridView mDragGridViewCurrent = null;
	private DragGridView mDragGridViewSource = null;

	private List<Section> mSectionsCurrent = null;
	private List<Section> mSectionsSource = null;

	private DragAdapter mSectionAdapterCurrent = null;
	private DragAdapter mSectionAdapterSource = null;

	public static void startActivity(Context context) {
		Intent intent = null;
		
		intent = new Intent(context, TestActivity.class);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_test);

		this.mSectionsCurrent = new ArrayList<Section>();
		this.mSectionsSource = new ArrayList<Section>();
		
		this.initView();
		this.initListener();

		// test code

		for (int i = 0; i < 60; i++) {
			Section s = new Section();
			s.setId(i);
			s.setName("Section" + i);

			this.mSectionsCurrent.add(s);
		}
		this.mSectionAdapterCurrent.notifyDataSetChanged();
		// end test code

		// test code

		for (int i = 0; i < 64; i++) {
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
		this.mSectionAdapterCurrent = new DragAdapter(this, this.mSectionsCurrent);
		this.mSectionAdapterSource = new DragAdapter(this, this.mSectionsSource);
		
		this.mDragGridViewCurrent = (DragGridView) super.findViewById(R.id.grid_current);
		this.mDragGridViewCurrent.setAdapter(this.mSectionAdapterCurrent);
		
		this.mDragGridViewSource = (DragGridView) super.findViewById(R.id.grid_source);
		this.mDragGridViewSource.setAdapter(this.mSectionAdapterSource);
	}

	private void initListener() {}
}
