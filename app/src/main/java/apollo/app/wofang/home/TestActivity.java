package apollo.app.wofang.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import apollo.app.wofang.R;

public class TestActivity extends DrawerActivity {

    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_test);
        super.onCreate(savedInstanceState);

        this.initView();
        this.initListener();
    }

    private void initView() {
    }

    private void initListener() {}

}
