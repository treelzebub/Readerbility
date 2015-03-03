package com.treelzebub.readerbility.ui.fragtivity;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.treelzebub.readerbility.R;

/**
 * Created by Tre Murillo on 2/27/15.
 */
public class Login {

    public static class LoginFragment extends Fragment {
        public static final String TAG = "login_fragment";

        private static LoginFragment loginFragment;

        public LoginFragment() {
        }

        public static Fragment getInstance(Context c) {
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
            }
            return loginFragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            View v;
            v = inflater.inflate(R.layout.fragment_login, container, false);

            EditText usernameEdit = (EditText) v.findViewById(R.id.username_edit);
            EditText pwdEdit = (EditText) v.findViewById(R.id.pwd_edit);

            return v;
        }
    }

    public class LoginActivity extends AccountAuthenticatorActivity {

    }
}
