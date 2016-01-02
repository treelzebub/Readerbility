package net.treelzebub.readerbility.auth

import com.squareup.okhttp.OkHttpClient
import retrofit.RestAdapter
import retrofit.client.OkClient

/**
 * Created by Tre Murillo on 1/1/16
 */
object ServiceGenerator {

    private val builder = RestAdapter.Builder()

    fun <S> createService(serviceClazz: Class<S>, baseUrl: String, accessToken: OAuthToken?): S {
        val client = OkHttpClient()
        builder.setClient(OkClient(client))
        builder.setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL)
        if (accessToken != null) {
            builder.setRequestInterceptor {
                it.addHeader("Accept", "application/json")
                it.addHeader("Authorization", "...")
            }
        }
        val adapter = builder.build()
        return adapter.create(serviceClazz)
    }
}
