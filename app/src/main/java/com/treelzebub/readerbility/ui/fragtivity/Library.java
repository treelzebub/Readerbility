package com.treelzebub.readerbility.ui.fragtivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.treelzebub.readerbility.NavigationDrawerFragment;
import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.api.BookmarksAsyncTask;
import com.treelzebub.readerbility.api.Readability;
import com.treelzebub.readerbility.api.model.Bookmark;

import java.util.List;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Library {
    public class LibraryActivity extends ActionBarActivity
            implements NavigationDrawerFragment.NavigationDrawerCallbacks {

        private NavigationDrawerFragment mNavigationDrawerFragment;

        //Used to store the last screen title. For use in {@link #restoreActionBar()}.
        private CharSequence mTitle;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            FragmentManager fm = getSupportFragmentManager();
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    fm.findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

            fm.beginTransaction()
                    .add(R.id.container, new LibraryFragment())
                    .commit();
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
                    mTitle = getString(R.string.all_bookmarks);
                    break;
                case 2:
                    mTitle = getString(R.string.my_library);
                    break;
                case 3:
                    mTitle = getString(R.string.new_zine);
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
                getMenuInflater().inflate(R.menu.main, menu);
                restoreActionBar();
                return true;
            }
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    }


    public static class LibraryFragment extends ListFragment {
        public static final String TAG = "libraryFragment";

        public LibraryFragment() {
        }

        public static List<Bookmark> mLibrary;
        public static ProgressBar progressBar;

        @Override
        public void onStart() {
            super.onStart();
            new BookmarksAsyncTask().execute();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.fragment_library, container, false);

            LibraryAdapter adapter = new LibraryAdapter(getActivity(), Readability.getInstance().getLibrary());
            setListAdapter(adapter);
            return v;
        }

        private class LibraryAdapter extends BaseAdapter {

            LayoutInflater mInflater;

            LibraryAdapter(Context context, List<Bookmark> library) {
                mLibrary = library;
                mInflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return mLibrary.size();
            }

            @Override
            public Bookmark getItem(int i) {
                return mLibrary.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, @NonNull ViewGroup parent) {
                View v;
                ViewHolder holder;

                if (convertView == null) {
                    v = mInflater.inflate(R.layout.list_item_library, parent, false);
                    holder = new ViewHolder();

                    holder.titleTV = (TextView) v.findViewById(R.id.library_item_title);
                    holder.dateTV = (TextView) v.findViewById(R.id.library_item_date);

                    v.setTag(holder);
                } else {
                    v = convertView;
                    holder = (ViewHolder) v.getTag();
                }

                Bookmark bookmark = mLibrary.get(i);

                holder.dateTV.setText(bookmark.getDateAdded().toString());
                holder.titleTV.setText(bookmark.getArticle().getTitle());

                return v;
            }

            private class ViewHolder {
                public TextView titleTV, dateTV;
            }
        }
    }
}
