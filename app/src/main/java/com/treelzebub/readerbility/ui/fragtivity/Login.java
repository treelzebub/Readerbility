package com.treelzebub.readerbility.ui.fragtivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.util.Constants;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Login {

    public class LoginActivity extends FragmentActivity {
        private final Handler mHandler = new Handler();
        private AccountManager mAccountManager;

        @InjectView(R.id.progress_bar)
        ProgressBar progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.activity_authenticator);

            mAccountManager = AccountManager.get(this);
            final Account[] accounts = mAccountManager.getAccountsByType(Constants.AUTH_TOKEN_TYPE);
            final Account account = accounts[2];
            final AccountManagerFuture<Bundle> amf =
                    mAccountManager.getAuthToken(accounts[0], Constants.AUTH_TOKEN_TYPE, null, this,
                            //TODO good candidate for kotlin lambda; this is hideous
                            new AccountManagerCallback<Bundle>() {

                                @Override
                                public void run(AccountManagerFuture<Bundle> accountManagerCallback) {
                                    try {
                                        Bundle result;
                                        Intent i;
                                        String token;

                                        result = accountManagerCallback.getResult();
                                        if (result.containsKey(AccountManager.KEY_INTENT)) {
                                            i = (Intent) result.get(AccountManager.KEY_INTENT);
                                            if (i.toString().contains("GrantCredentialsPermissionActivity")) {
                                                //TODO wait for the user to accept
                                                startActivity(i);
                                            } else {
                                                startActivity(i);
                                            }

                                        } else {
                                            token = (String) result.get(AccountManager.KEY_AUTHTOKEN);
                                            //TODO work with token
                                        }
                                    } catch (OperationCanceledException e) {
                                        Log.e("OperationCanceled", e.getMessage());
                                    } catch (IOException e) {
                                        Log.e("IOException", e.getMessage());
                                    } catch (AuthenticatorException e) {
                                        Log.e("AuthenticatorException", e.getMessage());
                                    }
                                }
                            }, mHandler);

            //TODO etc, etc, magic!
        }

        @Override
        public void finish() {
            super.finish();
            mAccountManager.invalidateAuthToken("", null); //null purges all old keys
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mAccountManager.invalidateAuthToken("", null);
        }
    }

    public static class LoginFragment extends Fragment implements OnClickListener {
        public static final String TAG = "login_fragment";

        private static LoginFragment mLoginFragment;

        public LoginFragment() {
        }

        public static Fragment getInstance(Context c) {
            return mLoginFragment == null ? mLoginFragment = new LoginFragment() : mLoginFragment;
        }

        @InjectView(R.id.username_edit)
        EditText usernameEdit;
        @InjectView(R.id.pwd_edit)
        EditText pwdEdit;
        @InjectView(R.id.submit_button)
        Button submitBtn;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.inject(this, v);

            //usernameEdit ...
            //pwdEdit      ...
            submitBtn.setOnClickListener(this);

            return v;
        }

        @Override
        public void onClick(View v) {
            //TODO validated ? doLibraryStuff : refresh(this)
        }
    }

}
