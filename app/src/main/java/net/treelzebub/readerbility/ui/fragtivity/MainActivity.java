package net.treelzebub.readerbility.ui.fragtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.treelzebub.readerbility.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

//    @InjectView(R.id.progress_bar)
//    ProgressBar mProgressBar;

    //Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.treelzebub.readerbility.R.layout.activity_main);
//        ButterKnife.inject(this);

        FragmentManager fm = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                fm.findFragmentById(net.treelzebub.readerbility.R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                net.treelzebub.readerbility.R.id.navigation_drawer,
                (DrawerLayout) findViewById(net.treelzebub.readerbility.R.id.drawer_layout));

        startActivity(new Intent(this, Login.LoginActivity.class));

//        fm.beginTransaction()
//                .add(R.id.container, new Login.LoginFragment())
//                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, new Login.LoginFragment())
//                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(net.treelzebub.readerbility.R.string.all_bookmarks);
                break;
            case 2:
                mTitle = getString(net.treelzebub.readerbility.R.string.my_library);
                break;
            case 3:
                mTitle = getString(net.treelzebub.readerbility.R.string.new_zine);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(net.treelzebub.readerbility.R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == net.treelzebub.readerbility.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
