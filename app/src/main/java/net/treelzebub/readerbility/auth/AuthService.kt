package net.treelzebub.readerbility.auth

import net.treelzebub.readerbility.Constants
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.DefaultApi10a
import org.scribe.model.Token
import org.scribe.oauth.OAuthService

/**
 * Created by Tre Murillo on 1/1/16
 */
object AuthService {

    val service: OAuthService get() {
        return ServiceBuilder()
                .apiKey(Constants.CONSUMER_KEY)
                .apiSecret(Constants.CONSUMER_SECRET)
                .callback(Constants.CALLBACK_URL)
                .provider(ReadabilityApi::class.java)
                .build()
    }

    private class ReadabilityApi : DefaultApi10a() {
        override fun getAccessTokenEndpoint(): String {
            return Constants.ACCESS_TOKEN_URL
        }

        override fun getRequestTokenEndpoint(): String {
            return Constants.REQUEST_TOKEN_URL
        }

        override fun getAuthorizationUrl(requestToken: Token): String {
            return Constants.AUTHORIZE_URL
        }
    }
}
