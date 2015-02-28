package com.treelzebub.readerbility.ui.fragtivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.thing.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Library {
    
    public class LibraryActivity extends FragmentActivity {

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }


        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }
        @Override
        protected void onStop() {
            super.onStop();
        }

    }

    public static class LibraryFragment extends ListFragment {
        public static final String TAG = "library_fragment";

        private static LibraryFragment libraryFrag = null;

        public LibraryFragment() {
        }

        public static Fragment getInstance() {
            if (libraryFrag == null) {
                libraryFrag = new LibraryFragment();
                libraryFrag.setListAdapter(new LibraryAdapter());
            }

            return libraryFrag;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            List libraryList = new ArrayList<Article>();

            View v = inflater.inflate(R.layout.fragment_library, container, false);
            ListView listView = (ListView) v.findViewById(android.R.id.list);
            
            setListAdapter(LibraryAdapter.getInstance(getActivity()));

            return v;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }
    }

    private static class LibraryAdapter extends BaseAdapter {
        static LibraryAdapter mAdapter;
        static List<Article> mLibrary;
        static LayoutInflater mInflater;

        static LibraryAdapter getInstance(Context c) {
            if (mAdapter == null) {
                mAdapter = new LibraryAdapter();
                mLibrary = new ArrayList<Article>();
                mInflater = LayoutInflater.from(c);

                //TODO temp
                mLibrary.add(new Article("Jazz", "http://www.asdf.com"));
                mLibrary.add(new Article("Programming", "http://www.asdf.com"));
                mLibrary.add(new Article("Film", "http://www.asdf.com"));
                mLibrary.add(new Article("Laptop Lapburn", "http://www.asdf.com"));
                mLibrary.add(new Article("Probable Testicularly Cancerous Future", "http://www.asdf.com"));
                mLibrary.add(new Article("Payments", "http://www.asdf.com"));
                mLibrary.add(new Article("RIP Leonard Nemoy", "http://www.asdf.com"));
                mLibrary.add(new Article("Article", "http://www.asdf.com"));
                mLibrary.add(new Article("Another Article", "http://www.asdf.com"));
                ////
            }
            return mAdapter;
        }

        @Override
        public int getCount() {
            return mLibrary.size();
        }

        @Override
        public Article getItem(int i) {
            return mLibrary.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder holder;

            if (convertView == null) {
                view = mInflater.inflate(R.layout.list_item_library, parent, false);
                holder = new ViewHolder();

                holder.titleTV = (TextView) view.findViewById(R.id.library_item_title);
                holder.dateTV = (TextView) view.findViewById(R.id.library_item_date);

                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            Article bookmark = mLibrary.get(i);

            holder.dateTV.setText(bookmark.url);
            holder.titleTV.setText(bookmark.title);

            return view;
        }

        private class ViewHolder {
            public TextView titleTV, dateTV;
        }
    }

}
