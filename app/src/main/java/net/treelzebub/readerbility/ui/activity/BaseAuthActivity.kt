package net.treelzebub.readerbility.ui.activity

import android.support.v7.app.AppCompatActivity
import com.google.api.client.auth.oauth.OAuthHmacSigner
import com.google.api.client.auth.oauth.OAuthParameters
import net.treelzebub.readerbility.Constants
import net.treelzebub.readerbility.auth.LocalCredStore

/**
 * Created by Tre Murillo on 1/1/16
 */
open class BaseAuthActivity : AppCompatActivity() {

    //Setup the AuthParameters for API call. This assumes we have successfully authed.
    protected fun getAuthParams(): OAuthParameters {
        val signer = OAuthHmacSigner()
        signer.clientSharedSecret = Constants.CONSUMER_SECRET
        signer.tokenSharedSecret = LocalCredStore.getToken()!!.secret
        val authorizer = OAuthParameters()
        authorizer.consumerKey = Constants.CONSUMER_KEY
        authorizer.signer = signer
        authorizer.token = LocalCredStore.getToken()!!.token
        return authorizer
    }
}
