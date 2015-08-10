package apollo.app.wofang.widget;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import apollo.core.R;
import apollo.data.model.Entity;
import apollo.data.model.Section;
import apollo.fragments.WebViewBaseFragment;

/**
 * Created by kuibo on 2015/8/8.
 */
public class SectionContentFragment extends WebViewBaseFragment<Section> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("SectionContentFragment", "onCreate" + this.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);

        Button btn = (Button) view.findViewById(R.id.button);
        btn.setText(this.toString());

        Log.i("SectionContentFragment", "onCreateView" + this.toString());
        return view;
    }

    @Override
    protected String getCacheKey() {
        return null;
    }

    @Override
    protected void executeOnLoadDataSuccess(Entity entity) {

    }

    @Override
    protected void executeOnLoadDataError(String object) {

    }

    protected int getLayoutRes() {
        return R.layout.fragment_tab_base;
    }
}
