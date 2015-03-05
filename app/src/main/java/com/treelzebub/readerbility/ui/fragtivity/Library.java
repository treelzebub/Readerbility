package com.treelzebub.readerbility.ui.fragtivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public static class LibraryFragment extends ListFragment {
        public static final String TAG = "library_fragment";

        public LibraryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            List libraryList = new ArrayList<Article>();
            View v = inflater.inflate(R.layout.fragment_library, container, false);
            ListView listView = (ListView) v.findViewById(android.R.id.list);
            LibraryAdapter adapter = new LibraryAdapter(getActivity());
            setListAdapter(adapter);

            return v;
        }

        private class LibraryAdapter extends BaseAdapter {
            List<Article> mLibrary;
            LayoutInflater mInflater;

            LibraryAdapter(Context c) {
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
                ////            }
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

                Article bookmark = mLibrary.get(i);

                holder.dateTV.setText(bookmark.url);
                holder.titleTV.setText(bookmark.title);

                return v;
            }

            private class ViewHolder {
                public TextView titleTV, dateTV;
            }
        }
    }

}
