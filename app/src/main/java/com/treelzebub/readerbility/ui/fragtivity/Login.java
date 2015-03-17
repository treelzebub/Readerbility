package com.treelzebub.readerbility.ui.fragtivity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.api.Scopes;
import com.treelzebub.readerbility.auth.AuthUtils;
import com.treelzebub.readerbility.auth.AuthUtils.AccountManagerAuth;
import com.treelzebub.readerbility.auth.Constants;
import com.treelzebub.readerbility.auth.GoogleAccountsService;
import com.treelzebub.readerbility.util.ServiceGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Login {

    public static boolean purgeAllTokens = false; //TODO sharedprefs & purge button in settings

    public class LoginActivity extends ActionBarActivity {

        @InjectView(R.id.progress_bar)
        ProgressBar progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_dialog);
            ButterKnife.inject(this);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.login, menu);
            return true;
        }

        @Override
        public void finish() {
            AuthUtils.invalidateToken(purgeAllTokens);
            super.finish();
        }

        @Override
        protected void onDestroy() {
            AccountManagerAuth.getAccountMan().invalidateAuthToken(Constants.ACCOUNT_TYPE, AccountManagerAuth.getToken());
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

        GoogleAccountsService mAccountsService;

        //TODO move
        private String username, password;

        private String mEmail;

        public LoginFragment() {
        }

        private Context mActivity;

        @InjectView(R.id.username_edit)
        EditText usernameEdit;
        @InjectView(R.id.pwd_edit)
        EditText passEdit;
        @InjectView(R.id.submit_button)
        Button submitBtn;


        //Lifecycle
        @Override
        public void onStart() {
            super.onStart();
            mActivity = getActivity();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case REQUEST_CODE_PICK_ACCOUNT:
                    if (resultCode == Activity.RESULT_CANCELED) {
                        Toast.makeText(mActivity, "Google Play Services must be installed.",
                                Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    } else if (resultCode == Activity.RESULT_OK) {
                        mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    }
                    return;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        @Override
        public void onResume() {
            super.onResume();
            if (checkPlayServices()) {
                mAccountsService = ServiceGenerator.createService(GoogleAccountsService.class, GoogleAccountsService.BASE_URL);
            }
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

            submitBtn.setTag("submit");
            submitBtn.setOnClickListener(this);

            return v;
        }

        @Override
        public void onClick(View v) {
            //TODO validated ? start Library : refresh(this)
            String username = usernameEdit.getText().toString();
            String pass = passEdit.getText().toString();

            if (v.getTag().equals("submit")) {
                Intent i = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Scopes.AUTHORIZATION_BASE_URL + "/login" + ".......")
                );
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

        private boolean checkPlayServices() {
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);

            if (status != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                    showErrorDialog(status);
                } else {
                    Toast.makeText(getActivity(), "This device is not supported.", Toast.LENGTH_LONG).show();
                    getActivity().finish();  //TODO do more betterer
                }
                return false;
            }
            return true;
        }

        private void checkUserAccount() {
            String accountName = AuthUtils.getAccountName(mActivity);
            if (accountName == null) showAccountPicker();
        }

        private void showErrorDialog(int code) {
            GooglePlayServicesUtil.getErrorDialog(code, getActivity(), REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
        }

        private void showAccountPicker() {
            String[] accountTypes = new String[]{"com.google"};
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    accountTypes, false, null, null, null, null);
            startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
        }


    }

}
