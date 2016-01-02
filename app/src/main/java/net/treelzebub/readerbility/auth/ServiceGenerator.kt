package net.treelzebub.readerbility.auth

import com.google.api.client.auth.oauth.OAuthParameters
import com.squareup.okhttp.OkHttpClient
import retrofit.RestAdapter
import retrofit.client.OkClient

/**
 * Created by Tre Murillo on 1/1/16
 */
object ServiceGenerator {

    private val builder = RestAdapter.Builder()

    fun <S> createService(serviceClazz: Class<S>, baseUrl: String, oAuthParams: OAuthParameters): S {
        val client = OkHttpClient()
        client.networkInterceptors().add(OAuthInterceptor(oAuthParams))
        builder.setClient(OkClient(client))
        builder.setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL)
        val adapter = builder.build()
        return adapter.create(serviceClazz)
    }
}
