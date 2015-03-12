package com.treelzebub.readerbility.auth;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by Tre Murillo on 3/5/15.
 */
public class GetUsernameTask extends AsyncTask<Void, Void, Void> {
    Activity mActivity;
    String mScope, mEmail;

    GetUsernameTask(Activity activity, String name, String scope) {
        mActivity = activity;
        mScope = scope;
        mEmail = name;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String token = fetchToken();
            if (token != null) {
                //
            }
        } catch (IOException e) {

        }
        return null;
    }

    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (UserRecoverableAuthException e) {
            // GooglePlayServices.apk is either old, disable, or not present
            // AuthUtils.recover();
        } catch (GoogleAuthException e) {
            Log.e("Error fetching token", e.getMessage());
        }
        return null;
    }


}
