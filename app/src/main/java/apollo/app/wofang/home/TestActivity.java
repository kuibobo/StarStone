package apollo.app.wofang.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;


import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import apollo.app.wofang.R;
import apollo.widget.NavigationDrawerFragment;

public class TestActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_menu_search).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_search)
                        .colorRes(R.color.toolbar_text_color)
                        .sizeDp(22)
        );

        menu.findItem(R.id.action_menu_favor).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_star)
                        .colorRes(R.color.toolbar_text_color)
                        .actionBarSize()
        );

        return super.onCreateOptionsMenu(menu);
    }

}
