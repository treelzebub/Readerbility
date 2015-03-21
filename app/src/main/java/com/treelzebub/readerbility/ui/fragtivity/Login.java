package com.treelzebub.readerbility.ui.fragtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.auth.AccessToken;
import com.treelzebub.readerbility.auth.XAuthAsyncTask;
import com.treelzebub.readerbility.util.Settings;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15
 */
public class Login {

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
            String username = mUsernameEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();

            if (v.getId() == R.id.submit_button) {
                if (!username.trim().equals("") || !password.trim().equals("")) {
                    AccessToken.getInstance().setUsername(username);
                    AccessToken.getInstance().setPassword(password);
                    new XAuthAsyncTask().execute();
                } else {
                    Toast.makeText(this.getActivity(), "Must enter username & password.", Toast.LENGTH_SHORT).show();
                }
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