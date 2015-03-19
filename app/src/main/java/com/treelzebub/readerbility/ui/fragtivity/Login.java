package com.treelzebub.readerbility.ui.fragtivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import android.widget.Toast;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.auth.AuthenticateWithOAuthTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Login {

    public static class LoginActivity extends ActionBarActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_dialog);

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
        public static final String TAG = "loginFragment";

        //Intent
        static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
        static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

        private static final String PROPERTIES_FILE_NAME = "local.properties";
        private final String[] MANDATORY_PROPERTIES = {"client_id", "client_secret"};

        //TODO move
        private String username, password;

        private String mEmail;

        public LoginFragment() {
        }

        private Context mActivity;

        @InjectView(R.id.progress_bar)
        ProgressBar mProgressBar;
        @InjectView(R.id.username_edit)
        EditText mUsernameEdit;
        @InjectView(R.id.pwd_edit)
        EditText mPasswordEdit;
        @InjectView(R.id.submit_button)
        Button mSubmitButton;

        //Lifecycle
        @Override
        public void onStart() {
            super.onStart();
            mActivity = getActivity();
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

            mSubmitButton.setTag("submit");
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
//                task.setProgressBar(mProgressBar);
                task.execute();

            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.load_properties:
                    loadPropertiesFile(PROPERTIES_FILE_NAME);
                    return false;
                case R.id.action_settings:
//                    Intent i = new Intent(this, Settings.SettingsActivity.class);
                    return true;
                default:
                    break;
            }

            return false; //because fragment.
        }

        //
        private Properties loadPropertiesFile(String filename) {
            Properties properties = null;

            try {
                properties = new Properties();
                properties.load(new FileInputStream(filename));
            } catch (IOException e) {
                String message = (e instanceof FileNotFoundException) ? "File Not Found." : "Disk Read Error.";

                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                Log.e(message, e.getMessage());
            }

            boolean success = true;

            for (String key : MANDATORY_PROPERTIES) {
                String value = properties.getProperty(key);

                if (value == null || value.trim().isEmpty()) {
                    success = false;
                    Toast.makeText(getActivity(), "Property missing: " + key, Toast.LENGTH_LONG).show();
                }
            }

            return success ? properties : null;
        }

    }
}