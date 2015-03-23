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
import android.widget.TextView;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.api.BookmarksAsyncTask;
import com.treelzebub.readerbility.api.Readability;
import com.treelzebub.readerbility.api.model.Bookmark;

import java.util.List;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Library {
    public class LibraryActivity {
        //TODO move navigation drawer shit here from Main
    }

    public static class LibraryFragment extends ListFragment {
        public static final String TAG = "libraryFragment";

        public LibraryFragment() {
        }

        public static List<Bookmark> mLibrary;

        @Override
        public void onStart() {
            super.onStart();

            // get Bookmarks from API
            new BookmarksAsyncTask().execute();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.fragment_library, container, false);

            LibraryAdapter adapter = new LibraryAdapter(getActivity(), Readability.library);
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
