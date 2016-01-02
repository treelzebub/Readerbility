package net.treelzebub.readerbility.auth

import com.google.api.client.auth.oauth.OAuthParameters
import com.google.api.client.http.GenericUrl
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Response
import java.io.IOException
import java.security.GeneralSecurityException

/**
 * Created by Tre Murillo on 1/1/16
 */
class OAuthInterceptor : Interceptor {
    private final val oAuthParams: OAuthParameters

    constructor(oAuthParams: OAuthParameters) {
        this.oAuthParams = oAuthParams;
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestUrl = GenericUrl(originalRequest.urlString())
        oAuthParams.computeNonce()
        oAuthParams.computeTimestamp()
        try {
            oAuthParams.computeSignature("GET", requestUrl)
            val compressedRequest = originalRequest
                    .newBuilder()
                    .header("Authorization", oAuthParams.authorizationHeader)
                    .header("Accept", "application/xml")
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
            return chain.proceed(compressedRequest)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }
        return chain.proceed(originalRequest)
    }
}
