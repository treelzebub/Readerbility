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

import butterknife.ButterKnife;
import butterknife.InjectView;

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

        public WelcomeFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @InjectView(R.id.browse_button)
        Button browseButton;
        @InjectView(R.id.login_button)
        Button loginButton;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.fragment_welcome, container, false);
            ButterKnife.inject(this, v);

            browseButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new Library.LibraryFragment())
                            .commit();
                }
            });

            loginButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });

            return v;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }
    }
}
