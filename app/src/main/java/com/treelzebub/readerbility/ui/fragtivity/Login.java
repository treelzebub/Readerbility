package com.treelzebub.readerbility.ui.fragtivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.common.AccountPicker;
import com.treelzebub.readerbility.R;
import com.treelzebub.readerbility.util.AuthUtils.AccountManagerAuth;
import com.treelzebub.readerbility.util.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Login {

    public class LoginActivity extends FragmentActivity {

        @InjectView(R.id.progress_bar)
        ProgressBar progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.activity_authenticator);


        }

        @Override
        public void finish() {
            super.finish();
            AccountManagerAuth.getAccountMan().invalidateAuthToken(Constants.ACCOUNT_TYPE, AccountManagerAuth.getToken()); //null token purges all old keys
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            AccountManagerAuth.getAccountMan().invalidateAuthToken(Constants.ACCOUNT_TYPE, AccountManagerAuth.getToken());
        }
    }

    public static class LoginFragment extends Fragment implements OnClickListener {
        public static final String TAG = "loginFragment";

        //Intent
        private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

        public LoginFragment() {
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

            submitBtn.setOnClickListener(this);

            return v;
        }

        @Override
        public void onClick(View v) {
            //TODO validated ? start Library : refresh(this)
            new ProgressDialog(getActivity());
        }

        private void pickUserAccount() {
            String[] accountTypes = new String[]{"com.google"};
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    accountTypes, false, null, null, null, null);
            startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
        }

    }

}
