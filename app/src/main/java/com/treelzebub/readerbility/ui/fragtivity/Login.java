package com.treelzebub.readerbility.ui.fragtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.auth.AuthenticateWithOAuthTask;
import com.treelzebub.readerbility.util.Settings;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Login {

    public static class LoginActivity extends ActionBarActivity {
        public static final String TAG = "LoginActivity";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.login, menu);
            return true;
        }

        @Override
        public void finish() {
            super.finish();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }
    }

    public static class LoginFragment extends Fragment implements OnClickListener {
        public static final String TAG = "LoginFragment";

        public LoginFragment() {
        }

        @InjectView(R.id.username_edit)
        EditText mUsernameEdit;
        @InjectView(R.id.pwd_edit)
        EditText mPasswordEdit;
        @InjectView(R.id.submit_button)
        Button mSubmitButton;
        @InjectView(R.id.progress_bar)
        ProgressBar mProgressBar;

        //Lifecycle
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.inject(this, v);

            mSubmitButton.setOnClickListener(this);

            return v;
        }

        @Override
        public void onClick(View v) {
            //TODO delete password immediately after auth
            CharSequence username = mUsernameEdit.getText().toString();
            CharSequence password = mPasswordEdit.getText().toString();

            if (v.getTag().equals("submit")) {
                AuthenticateWithOAuthTask task = new AuthenticateWithOAuthTask(getActivity(), username, password);
                task.setProgressBar(mProgressBar);
                task.execute();
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    Intent i = new Intent(getActivity(), Settings.SettingsActivity.class);
                    return true;
                default:
                    break;
            }
            return false; //because fragment.
        }
    }

}