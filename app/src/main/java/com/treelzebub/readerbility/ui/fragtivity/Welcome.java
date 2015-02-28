package com.treelzebub.readerbility.ui.fragtivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.treelzebub.readerbility.R;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Welcome {
    public class WelcomeActivity extends FragmentActivity {

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

    public static class WelcomeFragment extends Fragment {
        public static final String TAG = "welcome_fragment";

        private static WelcomeFragment welcomeFrag = null;

        public WelcomeFragment() {
        }

        public static Fragment getInstance() {
            if (welcomeFrag == null) welcomeFrag = new WelcomeFragment();
            return welcomeFrag;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            View v = inflater.inflate(R.layout.fragment_welcome, container, false);

            Button browseButton = (Button) v.findViewById(R.id.browse_button);
            Button createNewButton = (Button) v.findViewById(R.id.create_new_button);

            browseButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   getFragmentManager()
                           .beginTransaction()
                           .replace(R.id.container, Library.LibraryFragment.getInstance())
                           .commit();
                }
            });

            createNewButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

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
}
